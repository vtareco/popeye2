package net.dms.fsync.swing.models;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import net.dms.fsync.swing.components.JenixTableModel;
import net.dms.fsync.swing.components.MyColors;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.JiraIssue;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

/**
 * Created by dminanos on 22/05/2017.
 */
public class JiraTableModel extends JenixTableModel<JiraIssue, JiraTableModel.Columns> {

	public JiraTableModel(List<JiraIssue> issues) {
		super(Columns.class, issues);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Columns.lookup(columnIndex)) {
			case KEY:
				return rows.get(rowIndex).getKey();
			case SUMMARY:
				return rows.get(rowIndex).getFields().getSummary();
			case ASSIGNEE:
				return rows.get(rowIndex).getFields().getAssignee() != null
						 ? rows.get(rowIndex).getFields().getAssignee().getName()
						 : null;
			case STATUS:
				return rows.get(rowIndex).getFields().getStatus() != null
						 ? rows.get(rowIndex).getFields().getStatus().getName()
						 : null;
			case STORYPOINT:
				return rows.get(rowIndex).getFields().getStorypoints();
			default:
				return "no defined";
		}
	}

	public Color getRowColour(int row, List<FenixAcc> accs) {
		if(existsAcc(accs, rows.get(row).getKey())) {
			return MyColors.JIRA_CREATED;
		} else {
			return super.getRowColour(row);
		}
	}

	private boolean existsAcc(List<FenixAcc> accs, String codJira) {
		if(accs.stream().filter(a -> codJira != null && a.getCodigoPeticionCliente().contains(codJira)).count() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public enum Columns implements TableColumnEnumType {
		KEY(false),
		SUMMARY(false),
		ASSIGNEE(false),
		STATUS(false),
		STORYPOINT(false);

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
