package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;

public class AmPmElement extends PositionElement {

    @WatchfaceId(3)
    @WatchfaceRequired
    private WatchfaceResource amImage;
    @WatchfaceId(4)
    @WatchfaceRequired
    private WatchfaceResource pmImage;

    public AmPmElement(Watchface watchface) {
        super(watchface);
    }

    public WatchfaceResource getAmImage() {
        return amImage;
    }

    public void setAmImage(WatchfaceResource amImage) {
        this.amImage = amImage;
    }

    public WatchfaceResource getPmImage() {
        return pmImage;
    }

    public void setPmImage(WatchfaceResource pmImage) {
        this.pmImage = pmImage;
    }
}
