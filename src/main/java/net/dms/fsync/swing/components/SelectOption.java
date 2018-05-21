package net.dms.fsync.swing.components;

import java.util.Objects;

/**
 * Created by dminanos on 30/05/2017.
 */
public class SelectOption<C,D> {
    private C code;
    private D description;

    public SelectOption(C code, D description) {
        this.code = code;
        this.description = description;
    }

    public C getCode() {
        return code;
    }

    public void setCode(C code) {
        this.code = code;
    }

    public D getDescription() {
        return description;
    }

    public void setDescription(D description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description == null ? null : this.description.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelectOption<?, ?> that = (SelectOption<?, ?>) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
