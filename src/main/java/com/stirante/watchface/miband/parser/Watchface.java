package com.stirante.watchface.miband.parser;

import com.stirante.watchface.miband.utils.ExtendedDataInputStream;
import com.stirante.watchface.miband.utils.ExtendedDataOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static com.stirante.watchface.miband.utils.StreamUtils.toByteBuffer;
import static com.stirante.watchface.miband.utils.StreamUtils.toInputStream;

public class Watchface {

    private WatchfaceHeader header;
    private WatchfaceParameter mainParam;
    private HashMap<WatchfaceParameter, List<WatchfaceParameter>> parameters;
    private ArrayList<WatchfaceResource> resources;

    public Watchface() {
        header = new WatchfaceHeader();
        mainParam = new WatchfaceParameter();
        parameters = new HashMap<>();
        resources = new ArrayList<>();
    }

    public void read(InputStream in) throws IOException {
        ExtendedDataInputStream headerStream = toInputStream(toByteBuffer(in, WatchfaceHeader.LENGTH));
        header.read(headerStream);
        headerStream.close();
        ExtendedDataInputStream parameterStream = toInputStream(toByteBuffer(in, (int) header.getParametersSize()));
        mainParam.read(parameterStream);
        ArrayList<WatchfaceParameter> parameterDescriptors = WatchfaceParameter.readParameterList(parameterStream);
        parameterStream.close();
        long paramTableLength = mainParam.getChildById(1).getValue();
        ByteBuffer parameterTableBb = toByteBuffer(in, (int) paramTableLength);
        for (WatchfaceParameter parameterDescriptor : parameterDescriptors) {
            ExtendedDataInputStream input = toInputStream(parameterTableBb, (int) parameterDescriptor
                    .getChildById(1).getValue(), (int) parameterDescriptor.getChildById(2).getValue());
            parameters.put(parameterDescriptor, WatchfaceParameter.readParameterList(input));
            input.close();
        }
        long resourceCount = mainParam.getChildById(2).getValue();
        ExtendedDataInputStream resourceOffsetsStream = toInputStream(toByteBuffer(in, (int) (resourceCount * 4)));
        ArrayList<Integer> offsets = new ArrayList<>((int) resourceCount);
        for (int i = 0; i < resourceCount; i++) {
            offsets.add(resourceOffsetsStream.readLittleEndianInt());
        }
        resourceOffsetsStream.close();
        for (int i = 0; i < offsets.size(); i++) {
            Integer offset = offsets.get(i);
            ExtendedDataInputStream resourceStream;
            if (i + 1 == offsets.size()) {
                resourceStream = toInputStream(toByteBuffer(in));
            }
            else {
                int length = offsets.get(i + 1) - offset;
                resourceStream = toInputStream(toByteBuffer(in, length));
            }
            WatchfaceResource resource = new WatchfaceResource(i);
            resource.read(resourceStream);
            resources.add(resource);
        }
    }

    public void write(OutputStream out) throws IOException {
        ExtendedDataOutputStream eout = new ExtendedDataOutputStream(out);
        header.write(eout);
        mainParam.write(eout);
        ArrayList<WatchfaceParameter> descriptors = new ArrayList<>(parameters.keySet());
        descriptors.sort(Comparator.comparingInt(WatchfaceParameter::getId));
        int offset = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ExtendedDataOutputStream edos = new ExtendedDataOutputStream(baos);
        for (WatchfaceParameter parameterDescriptor : descriptors) {
            List<WatchfaceParameter> watchfaceParameters = parameters.get(parameterDescriptor);
            watchfaceParameters.sort(Comparator.comparingInt(WatchfaceParameter::getId));
            for (WatchfaceParameter parameter : watchfaceParameters) {
                parameter.write(edos);
            }
            parameterDescriptor.getChildById(1).setValue(offset);
            parameterDescriptor.getChildById(2).setValue(baos.size() - offset);
            offset = baos.size();
        }
        for (WatchfaceParameter parameterDescriptor : descriptors) {
            parameterDescriptor.write(eout);
        }
        eout.write(baos.toByteArray());
        baos.close();
        baos = new ByteArrayOutputStream();
        edos = new ExtendedDataOutputStream(baos);
        for (WatchfaceResource resource : resources) {
            eout.writeLittleEndianInt(baos.size());
            resource.write(edos);
        }
        eout.write(baos.toByteArray());
        baos.close();
    }

    public WatchfaceHeader getHeader() {
        return header;
    }

    public WatchfaceParameter getMainParam() {
        return mainParam;
    }

    public HashMap<WatchfaceParameter, List<WatchfaceParameter>> getParameters() {
        return parameters;
    }

    public ArrayList<WatchfaceResource> getResources() {
        return resources;
    }

    public boolean isValid() {
        return header.isValid();
    }

    @Override
    public String toString() {
        return "Watchface{" +
                "header=" + header +
                ", mainParam=" + mainParam +
                ", parameters=" + parameters +
                '}';
    }
}
