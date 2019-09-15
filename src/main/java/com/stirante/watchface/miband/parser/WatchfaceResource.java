package com.stirante.watchface.miband.parser;

import com.stirante.watchface.miband.utils.BitReader;
import com.stirante.watchface.miband.utils.BitWriter;
import com.stirante.watchface.miband.utils.ExtendedDataInputStream;
import com.stirante.watchface.miband.utils.ExtendedDataOutputStream;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.util.stream.IntStream;

public class WatchfaceResource extends WatchfaceBaseBlock {

    public enum ImageType {
        BIT_8,
        BIT_16,
        BIT_24,
        BIT_32,
        PALETTE
    }

    private int id;
    private int bitsPerPixel;
    private int height;
    private Color[] palette;
    private int paletteColors;
    private int rowLengthInBytes;
    private boolean transparency;
    private int width;
    private transient Image image;
    private ImageType type;

    public WatchfaceResource(int id) {
        this.id = id;
    }

    public void read(ExtendedDataInputStream in) throws IOException {
        char c = (char) in.read();
        char c1 = (char) in.read();
        if (c != 'B' && c1 != 'M') {
            throw new IllegalArgumentException("Image signature doesn't match.");
        }
        in.skip(2);
        readHeader(in);
        if (paletteColors > 256) {
            throw new IllegalArgumentException("Too many palette colors.");
        }
        if (paletteColors > 0) {
            this.readPalette(in);
        }
        else {
            if (IntStream.of(8, 16, 24, 32).noneMatch(i -> this.bitsPerPixel == i)) {
                throw new IllegalArgumentException("The image format is not supported.");
            }
        }
        image = readImage(in);
    }

    private ImageType guessBestType() {
        width = image.getWidth(null);
        height = image.getHeight(null);
        transparency = ((BufferedImage) image).getColorModel().hasAlpha();
        boolean bit8 = true;
        boolean bit16 = true;
        boolean bit24 = true;
//        HashSet<Integer> palette = new HashSet<>();
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int color = ((BufferedImage) image).getRGB(x, y);
                Color c = new Color(color);
                if (c.getBlue() != c.getGreen() || c.getGreen() != c.getRed()) {
                    bit8 = false;
                }
                if (c.getBlue() != (c.getBlue() & 0b11111000) || c.getRed() != (c.getRed() & 0b11111000) ||
                        c.getGreen() != (c.getGreen() & 0b11111100)) {
                    bit16 = false;
                    bit24 = false;
                }
//                palette.add(color);
            }
        }
        if (bit8 && !transparency) {
            bitsPerPixel = 8;
            return ImageType.BIT_8;
        }
        if (bit16 && !transparency) {
            bitsPerPixel = 16;
            return ImageType.BIT_16;
        }
        if (bit24) {
            bitsPerPixel = 24;
            return ImageType.BIT_24;
        }
//        int paletteSize = palette.size() + (transparency ? 1 : 0);
//        if (paletteSize <= 256 && (width * height * 4) > (width * height) + (paletteSize * 4)) {
//            System.out.println("Original size: " + (width * height * 4) + ", palette size: " + ((width * height) + (paletteSize * 4)) + ", color count: " + paletteSize);
//            return ImageType.PALETTE;
//        }
        bitsPerPixel = 32;
        rowLengthInBytes = width * (bitsPerPixel / 8);
        paletteColors = 0;
        return ImageType.BIT_32;
    }

    @Override
    public void write(ExtendedDataOutputStream out) throws IOException {
        out.writeByte('B');
        out.writeByte('M');
        if (transparency) {
            out.writeByte(16);
        }
        else {
            out.writeByte(8);
        }
        out.writeByte(0);
        writeHeader(out);
        if (type == null) {
            type = guessBestType();
        }
        switch (type) {
            case BIT_8:
                write8BitImage(out);
                break;
            case BIT_16:
                write16BitImage(out);
                break;
            case BIT_24:
                write24BitImage(out);
                break;
            case BIT_32:
                write32BitImage(out);
                break;
        }
    }

    @Override
    public boolean isValid() {
        return image != null;
    }

    private void readHeader(ExtendedDataInputStream in) throws IOException {
        this.width = in.readLittleEndianUShort();
        this.height = in.readLittleEndianUShort();
        this.rowLengthInBytes = in.readLittleEndianUShort();
        this.bitsPerPixel = in.readLittleEndianUShort();
        this.paletteColors = in.readLittleEndianUShort();
        this.transparency = (in.readLittleEndianUShort() > 0);
        if (paletteColors > 0) {
            type = ImageType.PALETTE;
        } else {
            switch (bitsPerPixel) {
                case 8:
                    type = ImageType.BIT_8;
                    break;
                case 16:
                    type = ImageType.BIT_16;
                    break;
                case 24:
                    type = ImageType.BIT_24;
                    break;
                case 32:
                    type = ImageType.BIT_32;
                    break;
            }
        }
    }

    private void writeHeader(ExtendedDataOutputStream out) throws IOException {
        out.writeLittleEndianUShort(width);
        out.writeLittleEndianUShort(height);
        out.writeLittleEndianUShort(rowLengthInBytes);
        out.writeLittleEndianUShort(bitsPerPixel);
        out.writeLittleEndianUShort(paletteColors);
        out.writeLittleEndianUShort(transparency ? 1 : 0);
    }

    private void readPalette(ExtendedDataInputStream in) throws IOException {
        this.palette = new Color[this.paletteColors];
        for (int i = 0; i < this.paletteColors; i++) {
            int r = in.read();
            int g = in.read();
            int b = in.read();
            //padding
            in.read();
            int alpha = (transparency && i == 0) ? 0 : 255;
            this.palette[i] = new Color(r, g, b, alpha);
        }
    }

    private void writePalette(ExtendedDataOutputStream out) throws IOException {
        for (int i = 0; i < this.paletteColors; i++) {
            Color color = palette[i];
            out.writeByte(color.getRed());
            out.writeByte(color.getGreen());
            out.writeByte(color.getBlue());
            out.writeByte(1);
        }
    }

    private Image readImage(ExtendedDataInputStream in) throws IOException {
        Image result;
        switch (type) {
            case BIT_8:
                result = read8BitImage(in);
                break;
            case BIT_16:
                result = read16BitImage(in);
                break;
            case BIT_24:
                result = read24BitImage(in);
                break;
            case BIT_32:
                result = read32BitImage(in);
                break;
            case PALETTE:
                result = readPaletteImage(in);
                break;
            default:
                throw new IllegalArgumentException("Unsupported bits per pixel value: " + bitsPerPixel);
        }
        return result;
    }

    private void writePaletteImage(ExtendedDataOutputStream out) throws IOException {
        for (int y = 0; y < this.height; y++) {
            BitWriter bitWriter = new BitWriter(out);
            for (int x = 0; x < this.width; x++) {
                long pixelColorIndex = 0;
                int color = ((BufferedImage) image).getRGB(x, y);
                for (int i = 0; i < palette.length; i++) {
                    if (palette[i].getRGB() == color) {
                        pixelColorIndex = i;
                        break;
                    }
                }
                bitWriter.writeBits(bitsPerPixel, pixelColorIndex);
            }
        }
    }

    private Image readPaletteImage(ExtendedDataInputStream in) throws IOException {
        type = ImageType.PALETTE;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < this.height; y++) {
            byte[] rowBytes = new byte[rowLengthInBytes];
            if (in.read(rowBytes) != rowLengthInBytes) {
                throw new EOFException();
            }
            BitReader bitReader = new BitReader(rowBytes);
            for (int x = 0; x < this.width; x++) {
                long pixelColorIndex = bitReader.readBits(this.bitsPerPixel);
                Color color = this.palette[(int) pixelColorIndex];
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private void write8BitImage(ExtendedDataOutputStream out) throws IOException {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int color = ((BufferedImage) image).getRGB(x, y);
                out.writeByte(color & 0xff);
            }
        }
    }

    private Image read8BitImage(ExtendedDataInputStream in) throws IOException {
        type = ImageType.BIT_8;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < this.height; y++) {
            byte[] rowBytes = new byte[rowLengthInBytes];
            if (in.read(rowBytes) != rowLengthInBytes) {
                throw new EOFException();
            }
            for (int x = 0; x < this.width; x++) {
                byte data = rowBytes[x];
                Color color = new Color(data, data, data, 255);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private void write16BitImage(ExtendedDataOutputStream out) throws IOException {
        for (int y = 0; y < this.height; y++) {
            BitWriter bitWriter = new BitWriter(out);
            for (int x = 0; x < this.width; x++) {
                int color = ((BufferedImage) image).getRGB(x, y);
                int r = (color >> 19) & 0b11111;
                int g = (color >> 10) & 0b111111;
                int b = (color >> 3) & 0b11111;
                bitWriter.writeBits(3, g & 0b111);
                bitWriter.writeBits(5, r);
                bitWriter.writeBits(5, b);
                bitWriter.writeBits(3, (g >> 3) & 0b111);
            }
        }
    }

    private Image read16BitImage(ExtendedDataInputStream in) throws IOException {
        type = ImageType.BIT_16;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < this.height; y++) {
            byte[] rowBytes = new byte[rowLengthInBytes];
            if (in.read(rowBytes) != rowLengthInBytes) {
                throw new EOFException();
            }
            BitReader bitReader = new BitReader(rowBytes);
            for (int x = 0; x < this.width; x++) {

                int firstByte = bitReader.readByte();
                int secondByte = bitReader.readByte();
                // This is how color channel bits are packed
                // 1111111111111111
                // GGGRRRRRBBBBBGGG
                int r = (int) ((byte) (firstByte & 0b11111)) << 3;
                int g = (int) ((byte) ((firstByte >> 5 & 0b111) | (secondByte & 0b111) << 3)) << 2;
                int b = (int) ((byte) (secondByte >> 3 & 31)) << 3;
                Color color = new Color(r, g, b, 255);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private void write24BitImage(ExtendedDataOutputStream out) throws IOException {
        for (int y = 0; y < this.height; y++) {
            BitWriter bitWriter = new BitWriter(out);
            for (int x = 0; x < this.width; x++) {
                int color = ((BufferedImage) image).getRGB(x, y);
                bitWriter.writeBits(8, 255 - ((color >> 24) & 0xff));
                bitWriter.writeBits(5, (color >> 3) & 0b11111);
                bitWriter.writeBits(6, (color >> 10) & 0b111111);
                bitWriter.writeBits(5, (color >> 19) & 0b11111);
            }
        }
    }

    private Image read24BitImage(ExtendedDataInputStream in) throws IOException {
        type = ImageType.BIT_24;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < this.height; y++) {
            byte[] rowBytes = new byte[rowLengthInBytes];
            if (in.read(rowBytes) != rowLengthInBytes) {
                throw new EOFException();
            }
            BitReader bitReader = new BitReader(rowBytes);
            for (int x = 0; x < this.width; x++) {
                int alpha = bitReader.readByte();
                int b = (int) bitReader.readBits(5) << 3;
                int g = (int) bitReader.readBits(6) << 2;
                int r = (int) bitReader.readBits(5) << 3;
                Color color = new Color(r, g, b, 255 - alpha);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    private void write32BitImage(ExtendedDataOutputStream out) throws IOException {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int color = ((BufferedImage) image).getRGB(x, y);
                out.writeByte((color >> 16) & 0xff);
                out.writeByte((color >> 8) & 0xff);
                out.writeByte(color & 0xff);
                out.writeByte(255 - ((color >> 24) & 0xff));
            }
        }
    }

    private Image read32BitImage(ExtendedDataInputStream in) throws IOException {
        type = ImageType.BIT_32;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < this.height; y++) {
            byte[] rowBytes = new byte[rowLengthInBytes];
            if (in.read(rowBytes) != rowLengthInBytes) {
                throw new EOFException();
            }
            for (int x = 0; x < this.width; x++) {
                int r = rowBytes[x * 4] & 0xFF;
                int g = rowBytes[x * 4 + 1] & 0xFF;
                int b = rowBytes[x * 4 + 2] & 0xFF;
                int alpha = rowBytes[x * 4 + 3] & 0xFF;
                Color color = new Color(r, g, b, 255 - alpha);
                image.setRGB(x, y, color.getRGB());
            }
        }
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public ImageType getType() {
        return type;
    }
}