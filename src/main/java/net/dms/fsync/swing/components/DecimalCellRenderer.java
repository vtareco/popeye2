package net.dms.fsync.swing.components;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;

public class DecimalCellRenderer extends DefaultTableCellRenderer {

	private DecimalFormat formatter;

	public DecimalCellRenderer() {
		super();
		String formato = "##,##"; //AppManager.getInstance().getPropiedad(Constantes.PROP_FORMATO_MONEDA);
		formatter = new DecimalFormat(formato);
		this.setHorizontalAlignment(RIGHT);
	}

	public DecimalCellRenderer(String formato) {
		super();
//		String formato = AppManager.getInstance().getPropiedad(Constantes.PROP_FORMATO_MONEDA);
		formatter = new DecimalFormat(formato);
		this.setHorizontalAlignment(RIGHT);
	}

	public void setValue(Object value) {
		setText((value == null)
				  ? ""
				  : formatter.format(value));
	}
}
