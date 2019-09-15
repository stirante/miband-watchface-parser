package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;

public class FormattedNumber extends BaseElement {

    @WatchfaceId(1)
    @WatchfaceRequired
    private NumberElement number;
    @WatchfaceId(2)
    private WatchfaceResource suffix;
    @WatchfaceId(3)
    private WatchfaceResource decimalPoint;
    @WatchfaceId(4)
    private WatchfaceResource suffixMiles;

    public FormattedNumber(Watchface watchface) {
        super(watchface);
    }

    public NumberElement getNumber() {
        return number;
    }

    public void setNumber(NumberElement number) {
        this.number = number;
    }

    public WatchfaceResource getSuffix() {
        return suffix;
    }

    public void setSuffix(WatchfaceResource suffix) {
        this.suffix = suffix;
    }

    public WatchfaceResource getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(WatchfaceResource decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public WatchfaceResource getSuffixMiles() {
        return suffixMiles;
    }

    public void setSuffixMiles(WatchfaceResource suffixMiles) {
        this.suffixMiles = suffixMiles;
    }

}
