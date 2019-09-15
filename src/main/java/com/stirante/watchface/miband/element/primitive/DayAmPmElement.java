package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;

public class DayAmPmElement extends AmPmElement {

    @WatchfaceId(5)
    @WatchfaceRequired
    private WatchfaceResource amImageEnglish;
    @WatchfaceId(6)
    @WatchfaceRequired
    private WatchfaceResource pmImageEnglish;

    public DayAmPmElement(Watchface watchface) {
        super(watchface);
    }

    public WatchfaceResource getAmImageEnglish() {
        return amImageEnglish;
    }

    public void setAmImageEnglish(WatchfaceResource amImageEnglish) {
        this.amImageEnglish = amImageEnglish;
    }

    public WatchfaceResource getPmImageEnglish() {
        return pmImageEnglish;
    }

    public void setPmImageEnglish(WatchfaceResource pmImageEnglish) {
        this.pmImageEnglish = pmImageEnglish;
    }
}
