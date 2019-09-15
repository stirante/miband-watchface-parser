package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.primitive.AnimationElement;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.scene.canvas.GraphicsContext;

public class OtherElement extends ComplexElement {

    @WatchfaceId(1)
    private AnimationElement animation;


    public OtherElement(Watchface watchface) {
        super(watchface);
    }

    @Override
    public void render(GraphicsContext g) {

    }

    public AnimationElement getAnimation() {
        return animation;
    }

    public void setAnimation(AnimationElement animation) {
        this.animation = animation;
    }
}