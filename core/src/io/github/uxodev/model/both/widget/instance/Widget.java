package io.github.uxodev.model.both.widget.instance;

import com.badlogic.gdx.graphics.Color;
import io.github.uxodev.model.both._objcomp.lock.ILockable;
import io.github.uxodev.model.both._objcomp.lock.Lockable;
import io.github.uxodev.model.both.widget._data.ShapeData;
import io.github.uxodev.model.both.widget._data.token.ContainToken;
import io.github.uxodev.model.both.widget._data.token.PropertyToken;
import io.github.uxodev.model.both.widget._data.token.UseToken;
import io.github.uxodev.model.both.widget.instance._objcomp.container.IContainable;
import io.github.uxodev.model.both.widget.instance._objcomp.token.IHasPropertyTokens;
import io.github.uxodev.model.both.widget.instance._objcomp.token.IHasUseTokens;
import io.github.uxodev.model.both.widget.instance.part.Decoration;
import io.github.uxodev.model.both.widget.instance.part.Part;
import io.github.uxodev.model.both.widget.token.material.MaterialToken;
import io.github.uxodev.model.both.widget.token.shape.WidgetShapeToken;
import io.github.uxodev.model.both.widget.token.source.Source;
import io.github.uxodev.model.city.map.voxel.Voxel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public abstract class Widget implements ILockable, IHasUseTokens, IHasPropertyTokens {
    public Voxel loc;

    public final Source source;
    public final MaterialToken materialToken;
    private final WidgetShapeToken widgetShapeToken;
    public final String name;
    public final Color color;

    private final Lockable lockable;
    private final HashSet<PropertyToken> propertyTokens;

    protected Widget(Source source, MaterialToken materialToken, WidgetShapeToken widgetShapeToken) {
        this.source = source;
        this.materialToken = materialToken;
        this.widgetShapeToken = widgetShapeToken;
        if (source.sourceToken2.name.equals(materialToken.name)) name = source.name + " " + widgetShapeToken.name;
        else name = source.name + " " + materialToken.name + " " + widgetShapeToken.name;
        color = source.color;

        lockable = new Lockable();
        propertyTokens = new HashSet<>();
        propertyTokens.addAll(source.propertyTokens);
        propertyTokens.addAll(materialToken.propertyTokens);
    }

    public void damage(int distance) {
        System.out.println(this + " was damaged distance " + distance);
    }

    public void destroy() {
        System.out.println(this + " was destroyed");
    }

    // ILockable
    @Override
    public void addLock(Lockable.LockReason lockReason) {
        lockable.addLock(lockReason);
    }

    @Override
    public void removeLock(Lockable.LockReason lockReason) {
        lockable.removeLock(lockReason);
    }

    @Override
    public boolean isLocked() {
        return lockable.isLocked();
    }

    // IHasUseTokens
    @Override
    public HashSet<UseToken> getUseTokens() {
        return widgetShapeToken.useTokens;
    }

    // IHasPropertyTokens
    @Override
    public HashSet<PropertyToken> getPropertyTokens() {
        return propertyTokens;
    }

    // defaults
    public WidgetShapeToken getShapeToken() {
        return widgetShapeToken;
    }

    public int getVolume() {
        return getShapeToken().volume;
    }

    public int getWeight() {
        return source.density * getShapeToken().volume;
    }

    public int getValue() {
        return (source.value * materialToken.multiplier * getShapeToken().multiplier) / 10000;
    }

    public ContainToken getContainToken() {
        return ShapeData.CONTAIN_TOKENS.get("");
    }

    public HashSet<ContainToken> getContainTokens() {
        return new HashSet<>();
    }

    public int getQuantity() {
        return 1;
    }

    public ArrayList<Part> getParts() {
        return new ArrayList<>();
    }

    public ArrayList<Decoration> getDecorations() {
        return new ArrayList<>();
    }

    public ArrayList<IContainable> getContained() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Widget widget = (Widget) o;
//        return Objects.equals(source, widget.source) &&
//                Objects.equals(materialToken, widget.materialToken) &&
//                Objects.equals(widgetShapeToken, widget.widgetShapeToken);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(source, materialToken, widgetShapeToken);
    }
}
