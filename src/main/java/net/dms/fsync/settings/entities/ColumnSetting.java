package net.dms.fsync.settings.entities;

import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

public class ColumnSetting {
    private TableColumnEnumType column;
    private boolean visible;
    public ColumnSetting() {
    }

    public ColumnSetting(TableColumnEnumType column, boolean visible) {
        this.column = column;
        this.visible = visible;
    }

    public TableColumnEnumType getColumn() {
        return column;
    }

    public void setColumn(TableColumnEnumType column) {
        this.column = column;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
