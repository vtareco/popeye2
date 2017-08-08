package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraIssue;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.MyColors;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.MyTableModel;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dminanos on 22/05/2017.
 */
public class JiraTableModel extends MyTableModel<JiraIssue, JiraTableModel.Columns> {
    public enum Columns implements TableColumnEnumType{

        KEY(false), SUMMARY(false), ASSIGNEE(false);

        private boolean editable;

        Columns(boolean editable) {
            this.editable = editable;
        }

        public boolean isEditable() {
            return editable;
        }

        public static Columns lookup(int iPosition){
            return Arrays.stream(Columns.values()).filter(c -> c.ordinal() == iPosition).findFirst().get();
        }
    }

    public JiraTableModel(List<JiraIssue> issues){
        super(Columns.class, issues);

    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Columns.lookup(columnIndex)){
            case KEY:
                return rows.get(rowIndex).getKey();

            case SUMMARY:

                return rows.get(rowIndex).getFields().getSummary();
            case ASSIGNEE:
                return rows.get(rowIndex).getFields().getAssignee().getName();
            default:
                return "no defined";
        }

    }

    public Color getRowColour(int row, List<FenixAcc> accs) {
        if (existsAcc(accs, rows.get(row).getKey())){
            return MyColors.JIRA_CREATED;
        }else{
            return super.getRowColour(row);
        }

    }

    private boolean existsAcc(List<FenixAcc> accs, String codJira){
        if (accs.stream().filter(a -> codJira != null && a.getCodigoPeticionCliente().contains(codJira)).count() > 0){
            return true;
        }else {
            return false;
        }

    }
}
