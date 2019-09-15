package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.AmPmElement;
import com.stirante.watchface.miband.element.primitive.TwoDigitsElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

import java.util.Calendar;

public class ClockElement extends ComplexElement {

    @WatchfaceId(1)
    private TwoDigitsElement hours;
    @WatchfaceId(2)
    private TwoDigitsElement minutes;
    @WatchfaceId(3)
    private TwoDigitsElement seconds;
    @WatchfaceId(4)
    private AmPmElement amPm;
    /**
     * This is hardcoded as 1234. Not used by Mi Band 4
     */
    @WatchfaceId(5)
    private long drawingOrder = 0x1234;
//    @WatchfaceId(9)
//    private long unknown;


    public ClockElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {
        Calendar calendar = Calendar.getInstance();
        boolean is24 = amPm == null;
        String hourStr =
                is24 ? String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) : String.valueOf(calendar.get(Calendar.HOUR));
        String minuteStr = String.valueOf(calendar.get(Calendar.MINUTE));
        String secondStr = String.valueOf(calendar.get(Calendar.SECOND));

        if (hours != null) {
            hours.draw(g, hourStr);
        }
        if (minutes != null) {
            minutes.draw(g, minuteStr);
        }
        if (seconds != null) {
            seconds.draw(g, secondStr);
        }
        if (!is24) {
            if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
                renderImage(g, amPm.getAmImage(), amPm.getX(), amPm.getY());
            }
            else {
                renderImage(g, amPm.getPmImage(), amPm.getX(), amPm.getY());
            }
        }
    }

    public TwoDigitsElement getHours() {
        return hours;
    }

    public void setHours(TwoDigitsElement hours) {
        this.hours = hours;
    }

    public TwoDigitsElement getMinutes() {
        return minutes;
    }

    public void setMinutes(TwoDigitsElement minutes) {
        this.minutes = minutes;
    }

    public TwoDigitsElement getSeconds() {
        return seconds;
    }

    public void setSeconds(TwoDigitsElement seconds) {
        this.seconds = seconds;
    }

    public AmPmElement getAmPm() {
        return amPm;
    }

    public void setAmPm(AmPmElement amPm) {
        this.amPm = amPm;
    }

    public String getDrawingOrder() {
        return Long.toHexString(drawingOrder);
    }

    public void setDrawingOrder(String drawingOrder) {
        this.drawingOrder = Long.parseLong(drawingOrder, 16);
    }

//    public long getUnknown() {
//        return unknown;
//    }
//
//    public void setUnknown(long unknown) {
//        this.unknown = unknown;
//    }
}