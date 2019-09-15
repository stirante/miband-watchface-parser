package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

public class SwitchElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private CoordinatesElement coordinates;
    @WatchfaceId(2)
    private WatchfaceResource onImage;
    @WatchfaceId(3)
    private WatchfaceResource offImage;

    public SwitchElement(Watchface watchface) {
        super(watchface);
    }

    public CoordinatesElement getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesElement coordinates) {
        this.coordinates = coordinates;
    }

    public WatchfaceResource getOnImage() {
        return onImage;
    }

    public void setOnImage(WatchfaceResource onImage) {
        this.onImage = onImage;
    }

    public WatchfaceResource getOffImage() {
        return offImage;
    }

    public void setOffImage(WatchfaceResource offImage) {
        this.offImage = offImage;
    }

    public void draw(GraphicsContext g, boolean on) {
        Image img = null;
        if (on && onImage != null) {
            img = SwingFXUtils.toFXImage((BufferedImage) onImage.getImage(), null);
        }
        else if (!on && offImage != null) {
            img = SwingFXUtils.toFXImage((BufferedImage) offImage.getImage(), null);
        }
        if (img != null) {
            g.drawImage(img, coordinates.getX(), coordinates.getY());
        }
    }

}
