package net.dms.fsync.swing.components;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberCellEditor extends DefaultCellEditor {

	public NumberCellEditor() {
		super(new JFormattedTextField());
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
		if(value != null) {
			Locale myLocale = Locale.getDefault(); // better still
			NumberFormat numberFormat = NumberFormat.getInstance(myLocale);
			numberFormat.setMaximumFractionDigits(2);
			numberFormat.setMinimumFractionDigits(2);
			numberFormat.setMinimumIntegerDigits(1);
			editor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
			  new NumberFormatter(numberFormat)));
			Number num = (Number) value;
			String text = numberFormat.format(num);
			editor.setHorizontalAlignment(SwingConstants.RIGHT);
			editor.setText(text);
		} else {
			editor.setText(null);
		}
		return editor;
	}
}