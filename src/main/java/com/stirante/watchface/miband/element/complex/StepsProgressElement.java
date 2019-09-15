package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.CircleElement;
import com.stirante.watchface.miband.element.primitive.ImageElement;
import com.stirante.watchface.miband.element.primitive.LinearElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

public class StepsProgressElement extends ComplexElement {

    @WatchfaceId(1)
    private ImageElement goalImage;
    @WatchfaceId(2)
    private LinearElement linear;
    @WatchfaceId(3)
    private CircleElement circle;

    public StepsProgressElement(Watchface watchface) {
        super(watchface);
    }

    public ImageElement getGoalImage() {
        return goalImage;
    }

    public void setGoalImage(ImageElement goalImage) {
        this.goalImage = goalImage;
    }

    public LinearElement getLinear() {
        return linear;
    }

    public void setLinear(LinearElement linear) {
        this.linear = linear;
    }

    public CircleElement getCircle() {
        return circle;
    }

    public void setCircle(CircleElement circle) {
        this.circle = circle;
    }

    @Override
    public void render(GraphicsContext g) {
        if (goalImage != null) {
            renderImage(g, goalImage.getImage(), goalImage.getX(), goalImage.getY());
        }
        if (linear != null) {
            linear.draw(g, 50);
        }
        if (circle != null) {
            circle.draw(g, 50);
        }
    }

}