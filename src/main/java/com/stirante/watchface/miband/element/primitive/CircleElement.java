package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceParameter;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.scene.canvas.GraphicsContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class CircleElement extends BaseElement {

    @WatchfaceId(1)
    private Long centerX;
    @WatchfaceId(2)
    private Long centerY;
    @WatchfaceId(3)
    private Long radiusX;
    @WatchfaceId(4)
    private Long radiusY;
    @WatchfaceId(5)
    private Long startAngle;
    @WatchfaceId(6)
    private Long endAngle;
    @WatchfaceId(7)
    private Long width;
    @WatchfaceId(8)
    private Long color;

    public CircleElement(Watchface watchface) {
        super(watchface);
    }

    public Long getCenterX() {
        return centerX;
    }

    public void setCenterX(Long centerX) {
        this.centerX = centerX;
    }

    public Long getCenterY() {
        return centerY;
    }

    public void setCenterY(Long centerY) {
        this.centerY = centerY;
    }

    public Long getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(Long radiusX) {
        this.radiusX = radiusX;
    }

    public Long getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(Long radiusY) {
        this.radiusY = radiusY;
    }

    public Long getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(Long startAngle) {
        this.startAngle = startAngle;
    }

    public Long getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(Long endAngle) {
        this.endAngle = endAngle;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getColor() {
        return color;
    }

    public void setColor(Long color) {
        this.color = color;
    }

    public void draw(GraphicsContext g, int progress) {
        throw new NotImplementedException();
    }

}