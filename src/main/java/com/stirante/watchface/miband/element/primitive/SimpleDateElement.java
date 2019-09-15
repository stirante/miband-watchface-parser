package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.parser.Watchface;

public class SimpleDateElement extends BaseElement {

    @WatchfaceId(1)
    private DayAndMonthElement separateDayAndMonth;
    @WatchfaceId(2)
    private NumberWithDelimiterElement oneLineDayAndMonth;
    @WatchfaceId(3)
    public boolean twoDigitsMonth;
    @WatchfaceId(4)
    public boolean twoDigitsDay;

    public SimpleDateElement(Watchface watchface) {
        super(watchface);
    }

    public DayAndMonthElement getSeparateDayAndMonth() {
        return separateDayAndMonth;
    }

    public void setSeparateDayAndMonth(DayAndMonthElement separateDayAndMonth) {
        this.separateDayAndMonth = separateDayAndMonth;
    }

    public NumberWithDelimiterElement getOneLineDayAndMonth() {
        return oneLineDayAndMonth;
    }

    public void setOneLineDayAndMonth(NumberWithDelimiterElement oneLineDayAndMonth) {
        this.oneLineDayAndMonth = oneLineDayAndMonth;
    }

    public boolean isTwoDigitsMonth() {
        return twoDigitsMonth;
    }

    public void setTwoDigitsMonth(boolean twoDigitsMonth) {
        this.twoDigitsMonth = twoDigitsMonth;
    }

    public boolean isTwoDigitsDay() {
        return twoDigitsDay;
    }

    public void setTwoDigitsDay(boolean twoDigitsDay) {
        this.twoDigitsDay = twoDigitsDay;
    }
}
