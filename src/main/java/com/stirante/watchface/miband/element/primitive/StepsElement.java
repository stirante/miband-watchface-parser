package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;

public class StepsElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private NumberElement steps;

    public StepsElement(Watchface watchface) {
        super(watchface);
    }

    public NumberElement getSteps() {
        return steps;
    }

    public void setSteps(NumberElement steps) {
        this.steps = steps;
    }
}
