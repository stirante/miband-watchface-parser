package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceParameter;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LinearElement extends BaseElement {

    @WatchfaceId(1)
    private List<WatchfaceResource> images;
    @WatchfaceId(2)
    private List<PositionElement> segments;

    public LinearElement(Watchface watchface) {
        super(watchface);
    }

    public List<WatchfaceResource> getImages() {
        return images;
    }

    public void setImages(List<WatchfaceResource> images) {
        this.images = images;
    }

    public List<PositionElement> getSegments() {
        return segments;
    }

    public void setSegments(List<PositionElement> segments) {
        this.segments = segments;
    }

    @Override
    public void fromWatchface(WatchfaceParameter parameter) {
        super.fromWatchface(parameter);
        images = new ArrayList<>();
        int index = fromIntValue(parameter, 1, true);
        for (int i = 0; i < segments.size(); i++) {
            images.add(watchface.getResources().get(index + i));
        }
    }

    @Override
    public WatchfaceParameter toWatchface(int id) {
        WatchfaceParameter root = super.toWatchface(id);
        root.getChildren().add(new WatchfaceParameter(1, watchface.getResources().indexOf(images.get(0))));
        return root;
    }

    public void draw(GraphicsContext g, int progress) {
        double step = 100.0 / segments.size();
        int icon = (int) (progress / step);
        for (int i = 0; i <= icon; i++) {
            PositionElement positionElement = segments.get(i);
            g.drawImage(SwingFXUtils.toFXImage((BufferedImage) images.get(i)
                    .getImage(), null), positionElement.getX(), positionElement.getY());
        }
    }
}
