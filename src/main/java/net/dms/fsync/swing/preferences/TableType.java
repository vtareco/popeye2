package net.dms.fsync.swing.preferences;

import net.dms.fsync.swing.models.AccTableModel;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

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
