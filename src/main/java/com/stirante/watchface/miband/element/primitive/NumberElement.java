package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceImageSet;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.List;

public class NumberElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private Integer topLeftX;
    @WatchfaceId(2)
    @WatchfaceRequired
    private Integer topLeftY;
    @WatchfaceId(3)
    private Integer bottomRightX;
    @WatchfaceId(4)
    private Integer bottomRightY;
    @WatchfaceId(5)
    private Integer alignment;
    @WatchfaceId(6)
    private Integer spacing;
    @WatchfaceId(7)
    @WatchfaceImageSet(8)
    private List<WatchfaceResource> images;

    public NumberElement(Watchface watchface) {
        super(watchface);
    }

    public Integer getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(Integer topLeftX) {
        this.topLeftX = topLeftX;
    }

    public Integer getTopLeftY() {
        return topLeftY;
    }

    public void setTopLeftY(Integer topLeftY) {
        this.topLeftY = topLeftY;
    }

    public Integer getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(Integer bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public Integer getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(Integer bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public Integer getAlignment() {
        return alignment;
    }

    public void setAlignment(Integer alignment) {
        this.alignment = alignment;
    }

    public Integer getSpacing() {
        return spacing;
    }

    public void setSpacing(Integer spacing) {
        this.spacing = spacing;
    }

    public List<WatchfaceResource> getImages() {
        return images;
    }

    public void setImages(List<WatchfaceResource> images) {
        this.images = images;
    }

    public static class Alignment {
        public static final int LEFT = 2;
        public static final int RIGHT = 4;
        public static final int CENTER = 8;
        public static final int TOP = 16;
        public static final int BOTTOM = 32;
        public static final int MIDDLE = 64;
    }

    public void draw(GraphicsContext g, String number) {
        draw(g, number, null);
    }

    public void draw(GraphicsContext g, String number, WatchfaceResource delimiter) {
        //This is so messy, just kill me
        if (images == null || images.isEmpty()) {
            return;
        }
        int length = number.length();
        boolean hasDelimiter = delimiter != null && !number.matches("[0-9]+");
        int pointWidth = 0;
        if (hasDelimiter) {
            pointWidth = spacing + delimiter.getImage().getWidth(null);
            length--;
        }
        int x, y;
        if ((alignment & Alignment.TOP) != 0) {
            y = topLeftY;
        }
        else if ((alignment & Alignment.BOTTOM) != 0) {
            y = bottomRightY - images.get(0).getImage().getHeight(null);
        }
        else {
            y = (topLeftY + ((bottomRightY - topLeftY) / 2)) - (images.get(0).getImage().getHeight(null) / 2);
        }
        if ((alignment & Alignment.LEFT) != 0) {
            x = topLeftX;
        }
        else if ((alignment & Alignment.RIGHT) != 0) {
            x = bottomRightX - (((images.get(0).getImage().getWidth(null) + spacing) * length) + pointWidth);
        }
        else {
            x = topLeftX + ((bottomRightX - topLeftX) / 2) -
                    (((images.get(0).getImage().getWidth(null) + spacing) * length) + pointWidth) / 2;
        }
        if (hasDelimiter) {
            length++;
        }
        boolean hadPoint = false;
        for (int i = 0; i < length; i++) {
            boolean willHavePoint = false;
            WritableImage img = null;
            if (number.substring(i, i + 1).matches("[0-9]")) {
                img = SwingFXUtils.toFXImage((BufferedImage) images
                        .get(Integer.parseInt(number.substring(i, i + 1)))
                        .getImage(), null);
            }
            else if (hasDelimiter) {
                img = SwingFXUtils.toFXImage((BufferedImage) delimiter.getImage(), null);
                willHavePoint = true;
            }
            g.drawImage(img, x + (hadPoint ? i - 1 : i) * (spacing + images.get(0).getImage().getWidth(null)) +
                    (hadPoint ? pointWidth : 0), y);
            if (willHavePoint) {
                hadPoint = true;
            }
        }
//        g.setStroke(Color.RED);
//        g.setLineWidth(2);
//        g.rect(topLeftX, topLeftY, bottomRightX - topLeftX, bottomRightY - topLeftY);
//        g.stroke();
    }

}
