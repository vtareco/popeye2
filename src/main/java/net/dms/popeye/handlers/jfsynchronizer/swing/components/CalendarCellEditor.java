package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import net.dms.popeye.handlers.jfsynchronizer.swing.IncidenciaTableModel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.util.Date;

/**
 * Created by dminanos on 24/05/2017.
 */
public class CalendarCellEditor extends AbstractCellEditor
        implements TableCellEditor, TreeCellEditor{

    UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model);
    JDatePicker datePicker = new JDatePickerImpl(datePanel);


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        datePicker.getModel().setValue(null);
        return (Component) datePicker;
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        return null;
    }

    @Override
    public Date getCellEditorValue() {
        return (Date)datePicker.getModel().getValue();
    }
}
