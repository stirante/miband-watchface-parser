package com.stirante.watchface.miband.utils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

public class BitReader {

    private final ExtendedDataInputStream stream;
    /**
     * Current bit from the left side
     */
    private int currentBit = 8;
    /**
     * Current byte we're operating on
     */
    private int currentByte;

    public BitReader(byte[] bytes) {
        this(new ExtendedDataInputStream(new ByteArrayInputStream(bytes)));
    }

    public BitReader(ExtendedDataInputStream stream) {
        this.stream = stream;
    }

    public byte readByte() throws IOException {
        return (byte) readBits(8);
    }

    public long readBits(int length) throws IOException {
        long result = 0;
        for (int i = 0; i < length; i++) {
            result |= readBit() << (length - i - 1);
        }
        return result;
    }

    private long readBit() throws IOException {
        checkByte();
        long result = currentByte >> (7 - currentBit);
        currentBit++;
        return result & 1;
    }

    private void checkByte() throws IOException {
        if (currentBit > 7) {
            currentBit = 0;
            currentByte = stream.read();
            if (currentByte == -1) {
                throw new EOFException();
            }
        }
    }

}
