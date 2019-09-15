package com.stirante.watchface.miband;

import com.stirante.watchface.miband.element.complex.*;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceParameter;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class MiBand4 {

    private final transient Watchface watchface;
    private BackgroundElement background;
    private ClockElement clock;
    private ActivityElement activity;
    private DateElement date;
    private StatusElement status;
    private StepsProgressElement stepsProgress;
    private OtherElement other;

    public MiBand4(Watchface watchface) {
        this.watchface = watchface;
    }

    public void fromWatchface() {
        for (WatchfaceParameter parameter : watchface.getParameters().keySet()) {
            List<WatchfaceParameter> parameters = watchface.getParameters().get(parameter);
            switch (parameter.getId()) {
                case 2:
                    background = new BackgroundElement(watchface);
                    background.fromWatchface(parameters);
                    break;
                case 3:
                    clock = new ClockElement(watchface);
                    clock.fromWatchface(parameters);
                    break;
                case 4:
                    activity = new ActivityElement(watchface);
                    activity.fromWatchface(parameters);
                    break;
                case 5:
                    date = new DateElement(watchface);
                    date.fromWatchface(parameters);
                    break;
                case 7:
                    stepsProgress = new StepsProgressElement(watchface);
                    stepsProgress.fromWatchface(parameters);
                    break;
                case 8:
                    status = new StatusElement(watchface);
                    status.fromWatchface(parameters);
                    break;
                case 11:
                    other = new OtherElement(watchface);
                    other.fromWatchface(parameters);
                    break;
            }
        }
    }

    public void toWatchface() {
        if (background != null) {
            watchface.getParameters().put(new WatchfaceParameter(2), background.toWatchface());
        }
        if (clock != null) {
            watchface.getParameters().put(new WatchfaceParameter(3), clock.toWatchface());
        }
        if (activity != null) {
            watchface.getParameters().put(new WatchfaceParameter(4), activity.toWatchface());
        }
        if (date != null) {
            watchface.getParameters().put(new WatchfaceParameter(5), date.toWatchface());
        }
        if (stepsProgress != null) {
            watchface.getParameters().put(new WatchfaceParameter(7), stepsProgress.toWatchface());
        }
        if (status != null) {
            watchface.getParameters().put(new WatchfaceParameter(8), status.toWatchface());
        }
        if (other != null) {
            watchface.getParameters().put(new WatchfaceParameter(11), other.toWatchface());
        }
    }

    public void render(GraphicsContext g) {
        if (background != null) {
            background.render(g);
        }
        if (clock != null) {
            clock.render(g);
        }
        if (activity != null) {
            activity.render(g);
        }
        if (date != null) {
            date.render(g);
        }
        if (stepsProgress != null) {
            stepsProgress.render(g);
        }
        if (status != null) {
            status.render(g);
        }
        if (other != null) {
            other.render(g);
        }
    }

    public BackgroundElement getBackground() {
        return background;
    }

    public void setBackground(BackgroundElement background) {
        this.background = background;
    }

    public ClockElement getClock() {
        return clock;
    }

    public void setClock(ClockElement clock) {
        this.clock = clock;
    }

    public OtherElement getOther() {
        return other;
    }

    public void setOther(OtherElement other) {
        this.other = other;
    }
}
