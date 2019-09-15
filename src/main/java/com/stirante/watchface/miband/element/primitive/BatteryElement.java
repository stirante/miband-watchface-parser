package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.parser.Watchface;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;

public class BatteryElement extends BaseElement {

    @WatchfaceId(1)
    private NumberElement text1;
    @WatchfaceId(2)
    private NumberElement text2;
    @WatchfaceId(3)
    private ImageSetElement icons;
    @WatchfaceId(5)
    private Long unknown5;
    @WatchfaceId(6)
    private Long unknown6;

    public BatteryElement(Watchface watchface) {
        super(watchface);
    }

    public NumberElement getText1() {
        return text1;
    }

    public void setText1(NumberElement text1) {
        this.text1 = text1;
    }

    public NumberElement getText2() {
        return text2;
    }

    public void setText2(NumberElement text2) {
        this.text2 = text2;
    }

    public ImageSetElement getIcons() {
        return icons;
    }

    public void setIcons(ImageSetElement icons) {
        this.icons = icons;
    }

    public Long getUnknown5() {
        return unknown5;
    }

    public void setUnknown5(Long unknown5) {
        this.unknown5 = unknown5;
    }

    public Long getUnknown6() {
        return unknown6;
    }

    public void setUnknown6(Long unknown6) {
        this.unknown6 = unknown6;
    }

    public void draw(GraphicsContext g, int battery) {
        int icon = (int) Math.ceil(battery / (100.0 / icons.getImages().size()));
        g.drawImage(SwingFXUtils.toFXImage((BufferedImage) icons.getImages()
                .get(icon)
                .getImage(), null), icons.getX(), icons.getY());
        text1.draw(g, String.valueOf(battery));
        text2.draw(g, String.valueOf(battery));
    }

}
