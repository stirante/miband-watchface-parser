package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.FormattedNumber;
import com.stirante.watchface.miband.element.primitive.NumberElement;
import com.stirante.watchface.miband.element.primitive.NumberWithDelimiterElement;
import com.stirante.watchface.miband.element.primitive.StepsElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

public class ActivityElement extends ComplexElement {

    @WatchfaceId(1)
    private StepsElement steps;
    @WatchfaceId(2)
    private NumberElement stepGoal;
    @WatchfaceId(3)
    private NumberWithDelimiterElement calories;
    @WatchfaceId(4)
    private NumberWithDelimiterElement pulse;
    @WatchfaceId(5)
    private FormattedNumber distance;

    public ActivityElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {
        if (steps != null) {
            steps.getSteps().draw(g, "5000");
        }
        if (stepGoal != null) {
            stepGoal.draw(g, "8000");
        }
        if (calories != null) {
            calories.getNumber().draw(g, "200");
        }
        if (pulse != null) {
            pulse.getNumber().draw(g, "120");
        }
        if (distance != null) {
            distance.getNumber().draw(g, "1.23", distance.getDecimalPoint());
        }
    }

    public StepsElement getSteps() {
        return steps;
    }

    public void setSteps(StepsElement steps) {
        this.steps = steps;
    }

    public NumberElement getStepGoal() {
        return stepGoal;
    }

    public void setStepGoal(NumberElement stepGoal) {
        this.stepGoal = stepGoal;
    }

    public NumberWithDelimiterElement getCalories() {
        return calories;
    }

    public void setCalories(NumberWithDelimiterElement calories) {
        this.calories = calories;
    }

    public NumberWithDelimiterElement getPulse() {
        return pulse;
    }

    public void setPulse(NumberWithDelimiterElement pulse) {
        this.pulse = pulse;
    }

    public FormattedNumber getDistance() {
        return distance;
    }

    public void setDistance(FormattedNumber distance) {
        this.distance = distance;
    }
}
