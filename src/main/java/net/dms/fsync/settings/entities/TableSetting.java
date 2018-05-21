package net.dms.fsync.settings.entities;

import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableSetting {
   private Set<ColumnSetting> allColumns = new HashSet<>();

    public TableSetting() {
    }

    public TableSetting(Set<ColumnSetting> allColumns) {
        this.allColumns = allColumns;
    }

    public TableSetting(TableColumnEnumType[] allColumns) {
        Arrays.stream(allColumns).forEach(c ->
                this.allColumns.add(new ColumnSetting(c, true))
        );
    }


    public Set<ColumnSetting> getAllColumns() {
        return allColumns;
    }

    public void setAllColumns(Set<ColumnSetting> allColumns) {
        this.allColumns = allColumns;
    }
}
