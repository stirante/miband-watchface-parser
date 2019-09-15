package com.stirante.watchface.miband.utils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ExtendedDataInputStream extends DataInputStream {

    private ByteBuffer littleEndianBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    /**
     * Creates an ExtendedDataInputStream that uses the specified
     * underlying InputStream.
     *
     * @param in the specified input stream
     */
    public ExtendedDataInputStream(InputStream in) {
        super(in);
    }

    public String read0DelimitedString() throws IOException {
        return read0DelimitedString(Charset.defaultCharset());
    }

    public String read0DelimitedString(Charset charset) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int i;
        while ((i = read()) > 0) {
            stream.write(i);
        }
        return new String(stream.toByteArray(), charset);
    }

    private ByteBuffer readLittleEndianBuffer(int len) throws IOException {
        littleEndianBuffer.clear();
        byte[] bytes = new byte[len];
        int i = read(bytes);
        if (i < len) {
            throw new EOFException();
        }
        littleEndianBuffer.put(bytes);
        littleEndianBuffer.rewind();
        return littleEndianBuffer;
    }

    public int readLittleEndianInt() throws IOException {
        return readLittleEndianBuffer(4).getInt();
    }

    public long readLittleEndianUInt() throws IOException {
        return readLittleEndianBuffer(4).getInt();
    }

    public short readLittleEndianShort() throws IOException {
        return readLittleEndianBuffer(2).getShort();
    }

    public int readLittleEndianUShort() throws IOException {
        return readLittleEndianBuffer(2).getShort();
    }

    public long readUInt() throws IOException {
        return readInt();
    }

    public int readUShort() throws IOException {
        return readShort();
    }

}
