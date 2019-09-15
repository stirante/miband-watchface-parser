package com.stirante.watchface.miband.element.complex;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.element.primitive.BaseElement;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceParameter;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.GraphicsContext;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class ComplexElement {

    protected final transient Watchface watchface;

    public ComplexElement(Watchface watchface) {
        this.watchface = watchface;
    }

    public void fromWatchface(List<WatchfaceParameter> parameters) {
        Class clz = getClass();
        while (clz != ComplexElement.class) {
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(WatchfaceId.class)) {
                    boolean required = field.isAnnotationPresent(WatchfaceRequired.class);
                    int id = field.getAnnotation(WatchfaceId.class).value();
                    WatchfaceParameter param =
                            parameters.stream().filter(parameter -> parameter.getId() == id).findFirst().orElse(null);
                    if (required && param == null) {
                        throw new IllegalArgumentException("Missing parameter!");
                    }
                    else if (param != null) {
                        Object value = null;
                        if (BaseElement.class.isAssignableFrom(field.getType())) {
                            try {
                                Constructor<?> constructor = field.getType().getConstructor(Watchface.class);
                                value = constructor.newInstance(watchface);
                                ((BaseElement) value).fromWatchface(param);
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (field.getType() == Integer.class) {
                            value = param.getIntValue();
                        }
                        else if (field.getType() == Long.class) {
                            value = param.getValue();
                        }
                        try {
                            field.set(this, value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            clz = clz.getSuperclass();
        }
    }

    public List<WatchfaceParameter> toWatchface() {
        List<WatchfaceParameter> result = new ArrayList<>();
        Class clz = getClass();
        while (clz != ComplexElement.class) {
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(WatchfaceId.class)) {
                    boolean required = field.isAnnotationPresent(WatchfaceRequired.class);
                    int id = field.getAnnotation(WatchfaceId.class).value();
                    try {
                        BaseElement element = (BaseElement) field.get(this);
                        if (required && element == null) {
                            throw new IllegalArgumentException("Missing required parameter!");
                        }
                        else if (element != null) {
                            result.add(element.toWatchface(id));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clz = clz.getSuperclass();
        }
        return result;
    }

    public abstract void render(GraphicsContext g);

    protected void renderImage(GraphicsContext g, WatchfaceResource image, int x, int y) {
        g.drawImage(SwingFXUtils.toFXImage((BufferedImage) image.getImage(), null), x, y);
    }

}
