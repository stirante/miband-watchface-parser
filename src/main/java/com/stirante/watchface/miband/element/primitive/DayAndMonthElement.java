package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.parser.Watchface;

public class DayAndMonthElement extends BaseElement {

    @WatchfaceId(1)
    private NumberElement month;
    @WatchfaceId(3)
    private NumberElement day;

    public DayAndMonthElement(Watchface watchface) {
        super(watchface);
    }

    public NumberElement getMonth() {
        return month;
    }

    public void setMonth(NumberElement month) {
        this.month = month;
    }

    public NumberElement getDay() {
        return day;
    }

    public void setDay(NumberElement day) {
        this.day = day;
    }
}
