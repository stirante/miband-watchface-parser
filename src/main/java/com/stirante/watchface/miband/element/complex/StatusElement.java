package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.BatteryElement;
import com.stirante.watchface.miband.element.primitive.SwitchElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

public class StatusElement extends ComplexElement {

    @WatchfaceId(1)
    private SwitchElement alarm;
    @WatchfaceId(2)
    private SwitchElement lock;
    @WatchfaceId(3)
    private SwitchElement bluetooth;
    @WatchfaceId(4)
    private BatteryElement battery;


    public StatusElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {
        if (alarm != null) {
            alarm.draw(g, true);
        }
        if (lock != null) {
            lock.draw(g, true);
        }
        if (bluetooth != null) {
            bluetooth.draw(g, true);
        }
        if (battery != null) {
            battery.draw(g, 60);
        }
    }

}