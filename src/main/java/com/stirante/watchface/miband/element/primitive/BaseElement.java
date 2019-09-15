package com.stirante.watchface.miband.element.primitive;

import com.stirante.watchface.miband.element.annotation.WatchfaceId;
import com.stirante.watchface.miband.element.annotation.WatchfaceImageSet;
import com.stirante.watchface.miband.element.annotation.WatchfaceRequired;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceParameter;
import com.stirante.watchface.miband.parser.WatchfaceResource;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseElement {

    protected final transient Watchface watchface;

    public BaseElement(Watchface watchface) {
        this.watchface = watchface;
    }

    public void fromWatchface(WatchfaceParameter parameter) {
        Class clz = getClass();
        while (clz != BaseElement.class) {
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(WatchfaceId.class)) {
                    boolean required = field.isAnnotationPresent(WatchfaceRequired.class);
                    int id = field.getAnnotation(WatchfaceId.class).value();
                    Object value = null;
                    if (field.getType() == Integer.class) {
                        value = fromIntValue(parameter, id, required);
                    }
                    else if (field.getType() == Long.class) {
                        value = fromLongValue(parameter, id, required);
                    }
                    else if (field.getType() == boolean.class) {
                        value = fromLongValue(parameter, id, required) != 0;
                    }
                    else if (field.getType() == WatchfaceResource.class) {
                        value = fromResourceIndex(parameter, id, required);
                    }
                    else if (field.getType().isAssignableFrom(List.class) &&
                            field.isAnnotationPresent(WatchfaceImageSet.class)) {
                        int countId = field.getAnnotation(WatchfaceImageSet.class).value();
                        value = fromResourceIndexAndCount(parameter, id, countId, required);
                    }
                    else if (field.getType().isAssignableFrom(List.class) &&
                            BaseElement.class.isAssignableFrom((Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0])) {
                        List<BaseElement> result = new ArrayList<>();
                        for (WatchfaceParameter child : parameter.getChildren()) {
                            if (child.getId() != 2) {
                                continue;
                            }
                            result.add(fromBaseElement(child, PositionElement.class, true));
                        }
                        value = result;
                    }
                    else if (BaseElement.class.isAssignableFrom(field.getType())) {
                        //noinspection unchecked
                        value =
                                fromBaseElement(parameter, id, (Class<? extends BaseElement>) field.getType(), required);
                    }
                    try {
                        field.set(this, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            clz = clz.getSuperclass();
        }
    }

    public WatchfaceParameter toWatchface(int id) {
        WatchfaceParameter root = new WatchfaceParameter(id);
        Class clz = getClass();
        while (clz != BaseElement.class) {
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(WatchfaceId.class)) {
                    boolean required = field.isAnnotationPresent(WatchfaceRequired.class);
                    int paramId = field.getAnnotation(WatchfaceId.class).value();
                    Object value = null;
                    try {
                        value = field.get(this);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (required && value == null) {
                        throw new IllegalStateException("Incomplete element!");
                    }
                    else if (value != null) {
                        if (field.getType() == Integer.class) {
                            root.getChildren().add(new WatchfaceParameter(paramId, (int) value));
                        }
                        else if (field.getType() == Long.class) {
                            root.getChildren().add(new WatchfaceParameter(paramId, (long) value));
                        }
                        else if (field.getType() == WatchfaceResource.class) {
                            root.getChildren().add(toResourceIndex(paramId, (WatchfaceResource) value));
                        }
                        else if (field.getType().isAssignableFrom(List.class) &&
                                field.isAnnotationPresent(WatchfaceImageSet.class)) {
                            int countId = field.getAnnotation(WatchfaceImageSet.class).value();
                            //noinspection unchecked
                            root.getChildren()
                                    .addAll(toResourceIndexAndCount(paramId, countId, (List<WatchfaceResource>) value));
                        }
                        else if (field.getType().isAssignableFrom(List.class) &&
                                BaseElement.class.isAssignableFrom((Class<?>) ((ParameterizedType) field.getGenericType())
                                        .getActualTypeArguments()[0])) {
                            List<?> result = (List<?>) value;
                            for (Object o : result) {
                                BaseElement child = (BaseElement) o;
                                root.getChildren().add(child.toWatchface(2));
                            }
                        }
                        else if (BaseElement.class.isAssignableFrom(field.getType())) {
                            root.getChildren().add(((BaseElement) value).toWatchface(paramId));
                        }
                    }
                }
            }
            clz = clz.getSuperclass();
        }
        return root;
    }

    protected int fromIntValue(WatchfaceParameter root, int id, boolean required) {
        WatchfaceParameter child = root.getChildById(id);
        if (required && child == null) {
            throw new IllegalArgumentException("Incomplete parameter " + getClass().getSimpleName() + "!");
        }
        return child != null ? child.getIntValue() : 0;
    }

    protected long fromLongValue(WatchfaceParameter root, int id, boolean required) {
        WatchfaceParameter child = root.getChildById(id);
        if (required && child == null) {
            throw new IllegalArgumentException("Incomplete parameter " + getClass().getSimpleName() + "!");
        }
        return child != null ? child.getValue() : 0;
    }

    protected WatchfaceResource fromResourceIndex(WatchfaceParameter root, int id, boolean required) {
        WatchfaceParameter child = root.getChildById(id);
        if (required && child == null) {
            throw new IllegalArgumentException("Incomplete parameter " + getClass().getSimpleName() + "!");
        }
        else if (child != null) {
            int index = child.getIntValue();
            if (index >= watchface.getResources().size() || index < 0) {
                throw new IllegalArgumentException("Invalid resource index!");
            }
            return watchface.getResources().get(index);
        }
        return null;
    }

    protected WatchfaceParameter toResourceIndex(int id, WatchfaceResource resource) {
        if (!watchface.getResources().contains(resource)) {
            throw new IllegalArgumentException("Resource not found!");
        }
        return new WatchfaceParameter(id, watchface.getResources().indexOf(resource));
    }

    protected <T extends BaseElement> T fromBaseElement(WatchfaceParameter child, Class<T> clz, boolean required) {
        if (required && child == null) {
            throw new IllegalArgumentException("Incomplete parameter " + getClass().getSimpleName() + "!");
        }
        else if (child != null) {
            try {
                Constructor<T> constructor = clz.getConstructor(Watchface.class);
                T t = constructor.newInstance(watchface);
                t.fromWatchface(child);
                return t;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new RuntimeException("Error occurred while parsing base element!", e);
            }
        }
        return null;
    }

    protected <T extends BaseElement> T fromBaseElement(WatchfaceParameter root, int id, Class<T> clz, boolean required) {
        return fromBaseElement(root.getChildById(id), clz, required);
    }

    protected List<WatchfaceResource> fromResourceIndexAndCount(WatchfaceParameter root, int indexId, int countId, boolean required) {
        WatchfaceParameter indexParam = root.getChildById(indexId);
        WatchfaceParameter countParam = root.getChildById(countId);
        if (required && (indexParam == null || countParam == null)) {
            throw new IllegalArgumentException("Incomplete parameter " + getClass().getSimpleName() + "!");
        }
        else if (indexParam != null && countParam != null) {
            int index = indexParam.getIntValue();
            int count = countParam.getIntValue();
            if (index >= watchface.getResources().size() || index < 0) {
                throw new IllegalArgumentException("Invalid resource index!");
            }
            if (index + count > watchface.getResources().size()) {
                throw new IllegalArgumentException("Invalid resource count!");
            }
            ArrayList<WatchfaceResource> images = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                images.add(watchface.getResources().get(i + index));
            }
            return images;
        }
        return null;
    }

    protected ArrayList<WatchfaceParameter> toResourceIndexAndCount(int indexId, int countId, List<WatchfaceResource> images) {
        int index = watchface.getResources().indexOf(images.get(0));
        if (index == -1) {
            throw new IllegalArgumentException("Resource not found!");
        }
        for (int i = 0; i < images.size(); i++) {
            if (watchface.getResources().indexOf(images.get(i)) != index + i) {
                throw new IllegalArgumentException("Images are not in the right order!");
            }
        }
        ArrayList<WatchfaceParameter> result = new ArrayList<>();
        result.add(new WatchfaceParameter(indexId, index));
        result.add(new WatchfaceParameter(countId, images.size()));
        return result;
    }

}
