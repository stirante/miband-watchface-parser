package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceImageSet;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;

import java.util.List;

public class ImageSetElement extends PositionElement {

    @WatchfaceId(3)
    @WatchfaceImageSet(4)
    @WatchfaceRequired
    private List<WatchfaceResource> images;

    public ImageSetElement(Watchface watchface) {
        super(watchface);
    }

    public List<WatchfaceResource> getImages() {
        return images;
    }

    public void setImages(List<WatchfaceResource> images) {
        this.images = images;
    }
}
