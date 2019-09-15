package com.stirante.watchface.miband.utils;

import java.io.IOException;

public class BitWriter {

    private final ExtendedDataOutputStream stream;
    /**
     * Current bit from the left side
     */
    private int currentBit = 0;
    /**
     * Current byte we're operating on
     */
    private int currentByte = 0;

    public BitWriter(ExtendedDataOutputStream stream) {
        this.stream = stream;
    }

    public void writeBits(int length, long value) throws IOException {
        for (int i = 1; i <= length; i++) {
            long value1 = (value >> (length - i)) & 0b1;
            writeBit(value1);
        }
    }

    private void writeBit(long value) throws IOException {
        currentBit++;
        long result = (value & 0b1) << (8 - currentBit);
        currentByte |= result;
        checkByte();
    }

    private void checkByte() throws IOException {
        if (currentBit > 7) {
            flush();
        }
    }

    public void flush() throws IOException {
        stream.writeByte(currentByte & 0xff);
        currentBit = 0;
        currentByte = 0;
    }

}
