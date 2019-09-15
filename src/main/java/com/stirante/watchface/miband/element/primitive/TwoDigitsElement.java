package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;

public class TwoDigitsElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private ImageSetElement tens;
    @WatchfaceId(2)
    @WatchfaceRequired
    private ImageSetElement ones;

    public TwoDigitsElement(Watchface watchface) {
        super(watchface);
    }

    public ImageSetElement getTens() {
        return tens;
    }

    public void setTens(ImageSetElement tens) {
        this.tens = tens;
    }

    public ImageSetElement getOnes() {
        return ones;
    }

    public void setOnes(ImageSetElement ones) {
        this.ones = ones;
    }

    public void draw(GraphicsContext g, String number) {
        if (number.length() < 2) {
            number = "0" + number;
        }
        g.drawImage(SwingFXUtils.toFXImage((BufferedImage) getTens().getImages()
                .get(Integer.parseInt(number.substring(0, 1)))
                .getImage(), null), getTens().getX(), getTens().getY());
        g.drawImage(SwingFXUtils.toFXImage((BufferedImage) getOnes().getImages()
                .get(Integer.parseInt(number.substring(1, 2)))
                .getImage(), null), getOnes().getX(), getOnes().getY());
    }

}