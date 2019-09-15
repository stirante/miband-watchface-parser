package com.stirante.watchface.miband.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class ExtendedDataOutputStream extends DataOutputStream {

    private ByteBuffer littleEndianBuffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    /**
     * Creates an ExtendedDataOutputStream that uses the specified
     * underlying OutputStream.
     *
     * @param out the specified output stream
     */
    public ExtendedDataOutputStream(OutputStream out) {
        super(out);
    }

    public void write0DelimitedString(String string) throws IOException {
        write0DelimitedString(string, Charset.defaultCharset());
    }

    public void write0DelimitedString(String string, Charset charset) throws IOException {
        byte[] bytes = string.getBytes(charset);
        for (byte b : bytes) {
            write(b);
        }
        write(0);
    }

    private void writeLittleEndian(int len, Runnable putRunnable) throws IOException {
        byte[] bytes = new byte[len];
        littleEndianBuffer.clear();
        putRunnable.run();
        littleEndianBuffer.rewind();
        littleEndianBuffer.get(bytes);
        write(bytes);
    }

    public void writeLittleEndianInt(int i) throws IOException {
        writeLittleEndian(4, () -> littleEndianBuffer.putInt(i));
    }

    public void writeLittleEndianUInt(long i) throws IOException {
        writeLittleEndian(4, () -> littleEndianBuffer.putInt((int) i));
    }

    public void writeLittleEndianShort(short s) throws IOException {
        writeLittleEndian(2, () -> littleEndianBuffer.putShort(s));
    }

    public void writeLittleEndianUShort(int s) throws IOException {
        writeLittleEndian(2, () -> littleEndianBuffer.putShort((short) s));
    }

    public void writeUInt(long i) throws IOException {
        writeInt((int)i);
    }

    public void writeUShort(int s) throws IOException {
        writeInt((short)s);
    }

    public void writeUntil(int i, long targetPosition) throws IOException {
        while (this.size() != targetPosition) {
            write(i);
        }
    }

}
