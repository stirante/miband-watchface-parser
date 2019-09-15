package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.CoordinatesElement;
import com.stirante.watchface.miband.element.primitive.DayAmPmElement;
import com.stirante.watchface.miband.element.primitive.ImageSetElement;
import com.stirante.watchface.miband.element.primitive.SimpleDateElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

import java.util.Calendar;

public class DateElement extends ComplexElement {

    @WatchfaceId(1)
    private SimpleDateElement date;
    @WatchfaceId(2)
    private ImageSetElement weekdays;
    @WatchfaceId(3)
    private DayAmPmElement amPm;
    @WatchfaceId(4)
    private CoordinatesElement coordinates;

    public DateElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {
        Calendar calendar = Calendar.getInstance();
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (date.isTwoDigitsDay() && day.length() == 1) {
            day = "0" + day;
        }
        //Month is counted from 0 here, so we add 1
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        if (date.isTwoDigitsMonth() && month.length() == 1) {
            month = "0" + month;
        }
        int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        if (date != null) {
            if (date.getSeparateDayAndMonth() != null) {
                if (date.getSeparateDayAndMonth().getMonth() != null) {
                    date.getSeparateDayAndMonth().getMonth().draw(g, month);
                }
                if (date.getSeparateDayAndMonth().getDay() != null) {
                    date.getSeparateDayAndMonth().getDay().draw(g, day);
                }
            }
            else if (date.getOneLineDayAndMonth() != null) {
                date.getOneLineDayAndMonth().draw(g, day + "/" + month);
            }
        }
        if (weekdays != null) {
            renderImage(g, weekdays.getImages().get(weekday), weekdays.getX(), weekdays.getY());
        }
    }

}