package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;

public class AnimationElement extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private AnimationImageSetElement animationImageSet;
    @WatchfaceId(2)
    @WatchfaceRequired
    private Long x;
    @WatchfaceId(3)
    @WatchfaceRequired
    private Long loopCount;
    @WatchfaceId(4)
    @WatchfaceRequired
    private Long interval;

    public AnimationElement(Watchface watchface) {
        super(watchface);
    }

    public AnimationImageSetElement getAnimationImageSet() {
        return animationImageSet;
    }

    public void setAnimationImageSet(AnimationImageSetElement animationImageSet) {
        this.animationImageSet = animationImageSet;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(Long loopCount) {
        this.loopCount = loopCount;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }
}