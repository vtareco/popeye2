package net.dms.fsync.swing.models;

import java.util.Arrays;
import java.util.List;

import net.dms.fsync.settings.entities.ColumnSetting;
import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

/**
 * Created by dminanos on 10/03/2018.
 */
public class ColumnsSettingTableModel extends JenixTableModel<ColumnSetting, ColumnsSettingTableModel.Columns> {

	public ColumnsSettingTableModel(List<ColumnSetting> columnSettings) {
		super(Columns.class, columnSettings);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Columns.lookup(columnIndex)) {
			case VISIBLE:
				return rows.get(rowIndex).isVisible();
			case NOMBRE:
				return rows.get(rowIndex).getColumn().name();
			default:
				return "not defined";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		ColumnSetting data = rows.get(row);
		switch(findColumnTypeByOrdinal(col)) {
			case VISIBLE:
				data.setVisible((Boolean) value);
				break;
			default:
				super.setValueAt(value, row, col);
		}
		this.fireTableDataChanged();
	}

	public enum Columns implements TableColumnEnumType {
		VISIBLE(true),
		NOMBRE(false);

		private boolean editable;

		Columns(boolean editable) {
			this.editable = editable;
		}

		public static Columns lookup(int iPosition) {
			return Arrays.stream(Columns.values()).filter(c -> c.ordinal() == iPosition).findFirst().get();
		}

		public boolean isEditable() {
			return editable;
		}

		public int getWidth() {
			return 50;
		}
	}
}
