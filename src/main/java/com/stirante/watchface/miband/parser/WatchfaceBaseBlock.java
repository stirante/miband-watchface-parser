package com.stirante.watchface.miband.parser;

import com.stirante.watchface.miband.utils.ExtendedDataInputStream;
import com.stirante.watchface.miband.utils.ExtendedDataOutputStream;

import java.io.IOException;

public abstract class WatchfaceBaseBlock {

    public abstract void read(ExtendedDataInputStream in) throws IOException;

    public abstract void write(ExtendedDataOutputStream out) throws IOException;

    public abstract boolean isValid();

}
