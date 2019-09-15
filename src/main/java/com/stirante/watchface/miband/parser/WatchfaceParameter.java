package com.stirante.watchface.miband.parser;

import com.stirante.watchface.miband.utils.ExtendedDataInputStream;
import com.stirante.watchface.miband.utils.ExtendedDataOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

import static com.stirante.watchface.miband.utils.StreamUtils.toByteBuffer;
import static com.stirante.watchface.miband.utils.StreamUtils.toInputStream;

/**
 * +----------+------+--------------------+-----------------+
 * |  Address | Size |        Type        |   Description   |
 * +----------+------+--------------------+-----------------+
 * |          |      |                    | 5 bits - ID     |
 * | 0x00     | 1    | unsigned byte      +-----------------+
 * |          |      |                    | 3 bits - flag   |
 * +----------+------+--------------------+-----------------+
 * | 0x01     | n    | long               | parameter value |
 * +----------+------+--------------------+-----------------+
 * | 0x01 + n | m    | WatchfaceParameter | children        |
 * +----------+------+--------------------+-----------------+
 */
public class WatchfaceParameter extends WatchfaceBaseBlock {

    private int id;
    private int flags;
    private long value;
    private int size;
    private ArrayList<WatchfaceParameter> children;

    public WatchfaceParameter() {
        this(0);
    }

    public WatchfaceParameter(int id) {
        this(id, 0);
    }

    public WatchfaceParameter(int id, long value) {
        size = 2;
        children = new ArrayList<>();
        this.id = id;
        this.value = value;
    }

    @Override
    public void read(ExtendedDataInputStream in) throws IOException {
        int rawId = in.readByte();
        id = (rawId & 248) >> 3;
        flags = rawId & 7;
        size = 1;
        int i = in.read();
        value = 0L;
        int offset = 0;
        while ((i & 128) > 0) {
            if (size > 9) {
                throw new IllegalArgumentException("Invalid parameter value!");
            }
            value |= (long) (i & 127) << offset;
            offset += 7;
            i = in.read();
            size++;
        }
        value = value | (long) (i & 127) << offset;
        size++;
        if (hasChildren()) {
            children = readParameterList(toInputStream(toByteBuffer(in, (int) value)));
        }
    }

    @Override
    public void write(ExtendedDataOutputStream out) throws IOException {
        out.write((id << 3) | flags);
        if (hasChildren()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ExtendedDataOutputStream eout = new ExtendedDataOutputStream(baos);
            children.sort(Comparator.comparingInt(o -> o.id));
            for (WatchfaceParameter child : children) {
                child.write(eout);
            }
            byte[] bytes = baos.toByteArray();
            value = bytes.length;
            writeValue(out);
            out.write(bytes);
        }
        else {
            writeValue(out);
        }
    }

    private void writeValue(ExtendedDataOutputStream out) throws IOException {
        long val = value;
        byte b;
        while (val >= 128) {
            b = (byte) ((val & 127) | 128);
            out.write(b);
            val >>= 7;
        }
        b = (byte) (val & 127);
        out.write(b);
    }

    private boolean hasChildren() {
        return (flags & WatchfaceParameterFlag.HAS_CHILDREN) != 0;
    }

    public WatchfaceParameter getChildById(int id) {
        return children.stream()
                .filter(watchfaceParameter -> watchfaceParameter.id == id)
                .findFirst()
                .orElse(null);
    }

    static ArrayList<WatchfaceParameter> readParameterList(ExtendedDataInputStream in) throws IOException {
        LinkedList<WatchfaceParameter> params = new LinkedList<>();
        WatchfaceParameter child = new WatchfaceParameter();
        child.read(in);
        params.add(child);
        while (in.available() > 0) {
            child = new WatchfaceParameter();
            child.read(in);
            params.add(child);
        }
        ArrayList<WatchfaceParameter> list = new ArrayList<>(params);
        list.sort(Comparator.comparingInt(o -> o.id));
        return list;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public int getIntValue() {
        return (int) value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public ArrayList<WatchfaceParameter> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<WatchfaceParameter> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "{" +
                "id:" + id +
                ", flags:" + flags +
                ", value:" + value +
                (hasChildren() ? ", children:" + children : "") +
                '}';
    }

    public static class WatchfaceParameterFlag {
        public static final byte UNKNOWN = 1;
        public static final byte HAS_CHILDREN = 2;
        public static final byte UNKNOWN1 = 4;
    }

}
