package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.scene.canvas.GraphicsContext;

public class NumberWithDelimiterElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private NumberElement number;
    @WatchfaceId(2)
    @WatchfaceRequired
    private WatchfaceResource delimiter;

    public NumberWithDelimiterElement(Watchface watchface) {
        super(watchface);
    }

    public NumberElement getNumber() {
        return number;
    }

    public void setNumber(NumberElement number) {
        this.number = number;
    }

    public WatchfaceResource getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(WatchfaceResource delimiter) {
        this.delimiter = delimiter;
    }

    public void draw(GraphicsContext g, String date) {
        number.draw(g, date, delimiter);
    }

}