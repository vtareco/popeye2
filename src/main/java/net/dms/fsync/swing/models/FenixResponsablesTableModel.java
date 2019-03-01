package net.dms.fsync.swing.models;

import java.util.Arrays;
import java.util.List;

import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.synchronizer.fenix.entities.FenixResponsable;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by dminanos on 03/02/2018.
 */
public class FenixResponsablesTableModel extends JenixTableModel<FenixResponsable, FenixResponsablesTableModel.Columns> {

	public FenixResponsablesTableModel(List<FenixResponsable> responsables) {
		super(Columns.class, responsables);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Columns.lookup(columnIndex)) {
			case NUMERO_EMPLEADO:
				return rows.get(rowIndex).getNumero();
			case NOMBRE_EMPLEADO:
				return rows.get(rowIndex).getNombre();
			case ESFUERZO:
				return rows.get(rowIndex).getEsfuerzo();
			case SUBTIPO_TAREA:
				return rows.get(rowIndex).getSubtipoTarea();
			default:
				return "no defined";
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		FenixResponsable responsable = rows.get(row);
		switch(findColumnTypeByOrdinal(col)) {
			case ESFUERZO:
				if(!StringUtils.isBlank((String) value)) {
					responsable.setEsfuerzo(Double.parseDouble((String) value));
				} else {
					responsable.setEsfuerzo(null);
				}
				break;
			default:
				super.setValueAt(value, row, col);
		}
		this.fireTableDataChanged();
	}

	public enum Columns implements TableColumnEnumType {
		NUMERO_EMPLEADO(false),
		NOMBRE_EMPLEADO(false),
		ESFUERZO(true),
		SUBTIPO_TAREA(true);

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
