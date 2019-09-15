package com.stirante.watchface.miband.utils;

import java.io.*;
import java.nio.ByteBuffer;

public class StreamUtils {
    public static ByteBuffer toByteBuffer(InputStream in, int length) throws IOException {
        byte[] b = new byte[length];
        int read = in.read(b);
        if (read != length) {
            throw new EOFException();
        }
        return (ByteBuffer) ByteBuffer.allocate(length).put(b).rewind();
    }

    public static ByteBuffer toByteBuffer(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = in.read()) != -1) {
            baos.write(i);
        }
        byte[] b = baos.toByteArray();
        return (ByteBuffer) ByteBuffer.allocate(b.length).put(b).rewind();
    }

    public static ExtendedDataInputStream toInputStream(ByteBuffer bb) {
        byte[] b = new byte[bb.limit() - bb.position()];
        bb.get(b);
        return new ExtendedDataInputStream(new ByteArrayInputStream(b));
    }

    public static ExtendedDataInputStream toInputStream(ByteBuffer bb, int offset, int length) {
        int position = bb.position();
        bb.position(offset);
        byte[] b = new byte[length];
        bb.get(b);
        bb.position(position);
        return new ExtendedDataInputStream(new ByteArrayInputStream(b));
    }

}
