package net.dms.fsync.swing.dialogs;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Collections;

import net.dms.fsync.settings.entities.ColumnSetting;
import net.dms.fsync.settings.entities.TableSetting;
import net.dms.fsync.swing.components.JenixDialog;
import net.dms.fsync.swing.components.JenixTable;
import net.dms.fsync.swing.models.ColumnsSettingTableModel;
import net.dms.fsync.swing.preferences.EnumOrderComparator;

public class ColumnsSettingDialog extends JenixDialog<TableSetting> {

	private JenixTable<ColumnsSettingTableModel, ColumnSetting> jtbColumns;
	private JScrollPane columnsSettingTablePanel;

	public ColumnsSettingDialog(Component parent, TableSetting initialPayload) {
		super(parent, initialPayload);
		// TODO FIXME, move to abstract class
		this.setSize(550, 650);
		setLocationRelativeTo(parent);
		setTitle("Configuración");
		setModal(true);
		setResizable(false);
		setVisible(true);
	}

	@Override
	protected void loadData() {
	}

	@Override
	public void edit() {
		java.util.List<ColumnSetting> columnSettings = jtbColumns.getModel().getList();
		columnSettings.addAll(getPayload().getAllColumns());
		Collections.sort(columnSettings, new EnumOrderComparator());
	}

	@Override
	public JComponent createCentralPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setLayout(new GridBagLayout());
		JLabel lblSummary = new JLabel("Nombre");
		ColumnsSettingTableModel responsablesTableModel = new ColumnsSettingTableModel(new ArrayList<ColumnSetting>());
		jtbColumns = new JenixTable(responsablesTableModel);
		columnsSettingTablePanel = new JScrollPane(jtbColumns, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		columnsSettingTablePanel.setMinimumSize(new Dimension(200, 200));
		jtbColumns.setFillsViewportHeight(true);
		jtbColumns.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final JCheckBox checkBox = new JCheckBox();
		jtbColumns.getColumn(ColumnsSettingTableModel.Columns.VISIBLE.name()).setCellRenderer(new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				checkBox.setSelected(((Boolean) value).booleanValue());
				return checkBox;
			}
		});
		jtbColumns.getColumnModel().getColumn(ColumnsSettingTableModel.Columns.VISIBLE.ordinal()).setCellEditor(new DefaultCellEditor(checkBox));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		int fila = 0;
		double anchoEntiquetas = 0.1;
		constraints.gridx = 0;
		constraints.gridy = ++fila;
		constraints.gridwidth = 2;
		constraints.gridheight = 5;
		constraints.fill = GridBagConstraints.BOTH;
		panel.add(columnsSettingTablePanel, constraints);
		return panel;
	}

	@Override
	public void fillPayLoad() {
	}

	@Override
	public void initialize() {
	}
}
