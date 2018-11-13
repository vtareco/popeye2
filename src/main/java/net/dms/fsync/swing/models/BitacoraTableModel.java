package net.dms.fsync.swing.models;

import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.synchronizer.fenix.entities.Bitacora;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

import java.util.Arrays;
import java.util.List;

public class BitacoraTableModel extends JenixTableModel<Bitacora, BitacoraTableModel.Columns> {
public enum Columns implements TableColumnEnumType {

    USER(false), CREATION_DATE(true), COMMENT(true);

    private boolean editable;

    Columns(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public int getWidth() {
        return 50;
    }

    public static BitacoraTableModel.Columns lookup(int iPosition) {
        return Arrays.stream(BitacoraTableModel.Columns.values()).filter(c -> c.ordinal() == iPosition).findFirst().get();
    }
}

    public BitacoraTableModel(List<Bitacora> bitacora) {
        super(BitacoraTableModel.Columns.class, bitacora);

    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (BitacoraTableModel.Columns.lookup(columnIndex)) {
            case USER:
                return rows.get(rowIndex).getUser();
            case CREATION_DATE:
                return rows.get(rowIndex).getCreationDate();
            case COMMENT:
                return rows.get(rowIndex).getComment();
            default:
                return "no defined";
        }

    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Bitacora bitacora = rows.get(row);
        switch (findColumnTypeByOrdinal(col)){
            case COMMENT:
                bitacora.setComment((String)value);
                break;
            default:
                super.setValueAt(value, row, col);
        }
        this.fireTableDataChanged();
    }


}