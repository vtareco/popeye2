package net.dms.fsync.swing.preferences;

import java.util.Comparator;

import net.dms.fsync.settings.entities.ColumnSetting;

public class EnumOrderComparator implements Comparator<ColumnSetting> {

	@Override
	public int compare(ColumnSetting o1, ColumnSetting o2) {
		return new Integer(o1.getColumn().ordinal()).compareTo(new Integer(o2.getColumn().ordinal()));
	}
}
