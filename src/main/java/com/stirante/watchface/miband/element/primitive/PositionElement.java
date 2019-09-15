package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;

public class PositionElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private Integer x;
    @WatchfaceId(2)
    @WatchfaceRequired
    private Integer y;

    public PositionElement(Watchface watchface) {
        super(watchface);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}
