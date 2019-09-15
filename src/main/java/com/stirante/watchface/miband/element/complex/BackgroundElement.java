package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.element.primitive.ImageElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundElement extends ComplexElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private ImageElement image;

    public BackgroundElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {
        renderImage(g, image.getImage(), image.getX(), image.getY());
    }

    public ImageElement getImage() {
        return image;
    }

    public void setImage(ImageElement image) {
        this.image = image;
    }
}
