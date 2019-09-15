package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;

public class AnimationImageSetElement extends ImageSetElement {

    @WatchfaceId(5)
    @WatchfaceRequired
    private Long x3;

    public AnimationImageSetElement(Watchface watchface) {
        super(watchface);
    }

    public Long getX3() {
        return x3;
    }

    public void setX3(Long x3) {
        this.x3 = x3;
    }
}
