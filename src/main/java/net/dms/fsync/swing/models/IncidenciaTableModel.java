package net.dms.fsync.swing.models;

import java.util.List;

import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.swing.components.SelectOption;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidencia;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

/**
 * Created by dminanos on 22/05/2017.
 */
public class IncidenciaTableModel extends JenixTableModel<FenixIncidencia, IncidenciaTableModel.Columns> {

	public IncidenciaTableModel(List<FenixIncidencia> incidencias) {
		super(Columns.class, incidencias);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Columns c = findColumnTypeByOrdinal(columnIndex);
		FenixIncidencia r = getList().get(rowIndex);
		switch(findColumnTypeByOrdinal(c.ordinal())) {
			case ACC_CORRECTOR:
				return new SelectOption<>(r.getAccCorrector(), r.getAccCorrector());
			case TAREA_CAUSANTE:
				return new SelectOption<>(r.getTareaCausante(), r.getTareaCausante());
			default:
				return super.getValueAt(rowIndex, columnIndex);
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		FenixIncidencia incidencia = rows.get(row);
		switch(findColumnTypeByOrdinal(col)) {
			case ACC_CORRECTOR:
				if(value != null) {
					SelectOption<String, String> opt = (SelectOption) value;
					incidencia.setAccCorrector(opt == null
												 ? null
												 : opt.getCode());
				}
				break;
			case TAREA_CAUSANTE:
				if(value != null) {
					SelectOption<String, String> opt = (SelectOption) value;
					incidencia.setTareaCausante(opt == null
												  ? null
												  : opt.getCode());
				}
				break;
			default:
				super.setValueAt(value, row, col);
		}
	}

	public enum Columns implements TableColumnEnumType {
		ID_INCIDENCIA(false, 50),
		NOMBRE_INCIDENCIA(true, 50),
		LOCALIZADA_EN(true, 50),
		TIPO_INCIDENCIA(true, 50),
		DESCRIPCION(true, 50),
		FECHA_INICIO(true, 50),
		FECHA_FIN(true, 50),
		ESFUERZO_HH(true, 50),
		URGENCIA(true, 50),
		IMPACTO(true, 50),
		PRIORIDAD(true, 50),
		FECHA_PREVISTA_CENTRO(true, 50),
		TAREA_CAUSANTE(true, 250),
		OT_CORRECTOR(true, 50),
		ACC_CORRECTOR(true, 250),
		ESTADO(true, 80);

		private int width;

		private boolean editable;

		Columns(boolean editable, int width) {
			this.editable = editable;
			this.width = width;
		}

		public boolean isEditable() {
			return editable;
		}

		public int getWidth() {
			return width;
		}
	}
}