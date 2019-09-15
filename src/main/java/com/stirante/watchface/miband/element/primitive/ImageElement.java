package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;

public class ImageElement extends PositionElement {

    @WatchfaceId(3)
    @WatchfaceRequired
    private WatchfaceResource image;

    public ImageElement(Watchface watchface) {
        super(watchface);
    }

    public WatchfaceResource getImage() {
        return image;
    }

    public void setImage(WatchfaceResource image) {
        this.image = image;
    }
}
