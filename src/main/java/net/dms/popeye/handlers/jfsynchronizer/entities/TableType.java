package net.dms.popeye.handlers.jfsynchronizer.entities;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.popeye.handlers.jfsynchronizer.swing.models.AccTableModel;

public enum TableType {
    FENIX_ACC(AccTableModel.Columns.values());

    private TableColumnEnumType[] columns;

    TableType(TableColumnEnumType[] columns) {
        this.columns = columns;
    }

    public TableColumnEnumType[] getColumns() {
        return columns;
    }
}
