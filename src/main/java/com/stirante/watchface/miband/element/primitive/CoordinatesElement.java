package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;

public class CoordinatesElement extends PositionElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private Integer x1;
    @WatchfaceId(2)
    @WatchfaceRequired
    private Integer y1;
    @WatchfaceId(3)
    @WatchfaceRequired
    private Integer x2;
    @WatchfaceId(4)
    @WatchfaceRequired
    private Integer y2;
    @WatchfaceId(5)
    @WatchfaceRequired
    private Integer x3;

    public CoordinatesElement(Watchface watchface) {
        super(watchface);
    }

    public Integer getX1() {
        return x1;
    }

    public void setX1(Integer x1) {
        this.x1 = x1;
    }

    public Integer getY1() {
        return y1;
    }

    public void setY1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getX2() {
        return x2;
    }

    public void setX2(Integer x2) {
        this.x2 = x2;
    }

    public Integer getY2() {
        return y2;
    }

    public void setY2(Integer y2) {
        this.y2 = y2;
    }

    public Integer getX3() {
        return x3;
    }

    public void setX3(Integer x3) {
        this.x3 = x3;
    }
}
