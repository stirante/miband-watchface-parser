package com.stirante.watchface.miband.parser;

import com.stirante.watchface.miband.utils.ExtendedDataInputStream;
import com.stirante.watchface.miband.utils.ExtendedDataOutputStream;

import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;

/**
 * +---------+------+----------------------+-------------------------------------------------+
 * | Address | Size |         Type         |                   Description                   |
 * +---------+------+----------------------+-------------------------------------------------+
 * | 0x00    | 16   | 0 delimited string   | signature                                       |
 * +---------+------+----------------------+-------------------------------------------------+
 * | 0x10    | 16   | 16 constant bytes    | 24 00 D0 03 00 00 3D 78 FF FF FF FF FF FF FF FF |
 * +---------+------+----------------------+-------------------------------------------------+
 * | 0x20    | 4    | unsigned int         | unknown                                         |
 * +---------+------+----------------------+-------------------------------------------------+
 * | 0x24    | 4    | unsigned int         | parameters size                                 |
 * +---------+------+----------------------+-------------------------------------------------+
 */
public class WatchfaceHeader extends WatchfaceBaseBlock {

    public static final int LENGTH = 40;
    private static final String SIGNATURE = "HMDIAL";
    private static final byte[] CONSTANT = {
            (byte) 0x24, (byte) 0x00, (byte) 0xD0, (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x3D, (byte) 0x78,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF
    };

    private String signature;
    private byte[] constant;
    private long unknown;
    private long parametersSize;

    public WatchfaceHeader() {
        signature = SIGNATURE;
        constant = CONSTANT;
        unknown = 0xffffffffffffffffL;
    }

    @Override
    public void read(ExtendedDataInputStream in) throws IOException {
        signature = in.read0DelimitedString();
        int skip = 16 - (signature.length() + 1);
        in.skipBytes(skip);
        constant = new byte[16];
        int i = in.read(constant);
        if (i < 16) {
            throw new EOFException();
        }
        unknown = in.readLittleEndianUInt();
        parametersSize = in.readLittleEndianUInt();
    }

    @Override
    public void write(ExtendedDataOutputStream out) throws IOException {
        out.write0DelimitedString(signature);
        out.writeUntil(0xFF, 16);
        out.write(constant);
        out.writeLittleEndianUInt(unknown);
        out.writeLittleEndianUInt(parametersSize);
    }

    @Override
    public boolean isValid() {
        return signature.equals(SIGNATURE) && Arrays.equals(constant, CONSTANT);
    }

    public byte[] getConstant() {
        return constant;
    }

    public void setConstant(byte[] constant) {
        this.constant = constant;
    }

    public long getUnknown() {
        return unknown;
    }

    public void setUnknown(long unknown) {
        this.unknown = unknown;
    }

    public long getParametersSize() {
        return parametersSize;
    }

    public void setParametersSize(long parametersSize) {
        this.parametersSize = parametersSize;
    }

    @Override
    public String toString() {
        return "WatchfaceHeader{" +
                "signature='" + signature + '\'' +
                ", unknown=" + unknown +
                ", parametersSize=" + parametersSize +
                '}';
    }
}
