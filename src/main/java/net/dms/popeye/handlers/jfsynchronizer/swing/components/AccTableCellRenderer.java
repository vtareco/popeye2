package net.dms.popeye.handlers.jfsynchronizer.swing.components;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.popeye.handlers.jfsynchronizer.swing.models.AccTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AccTableCellRenderer extends JenixTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        AccTableModel model = (AccTableModel) table.getModel();
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        FenixAcc acc = ((AccTableModel) table.getModel()).getList().get(row);
        AccStatus status = AccStatus.lookup(acc.getEstado());

        switch (AccTableModel.Columns.lookup(column)){
            case ETC:
                if (status != null
                        && !status.isFinal()
                        && acc != null
                        &&  (acc.getDesvioEtc() != null && acc.getDesvioEtc() > 0)) {
                    c.setBackground(Color.RED);
                    break;
                }

            case PORCENTAJE_COMPLETADO:

                Double porcentaje = acc.getPorcentajeCompletado();

                if (status != null
                        && !status.isFinal()
                        && porcentaje!= null
                        && porcentaje >= 75) {
                    c.setBackground(Color.ORANGE);
                    break;
                }
            case ESTADO:
                if (acc != null && acc.getJiraStatus() != null){
                    if (status == AccStatus.ENTREGADA) {
                        java.util.List<String> validStatus = Arrays.asList("Review", "Solved", "Implemented", "Done");
                        if (!validStatus.stream().anyMatch(s -> s.equals(acc.getJiraStatus()))) {
                            c.setBackground(Color.RED);
                            break;
                        }
                    }else if(status == AccStatus.EN_EJECUCION){
                        List<String> validStatusEj = Arrays.asList("In Progress", "Reopened", "To Do", "In Implementation");
                        if (!validStatusEj.stream().anyMatch(s -> s.equals(acc.getJiraStatus()))){
                            c.setBackground(Color.RED);
                            break;
                        }

                    }

                }

            default:
                if (isSelected) {

                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }else{
                    c.setBackground(model.getRowColour(row));
                    c.setForeground(Color.BLACK);
                }
        }



        return c;
    }
}