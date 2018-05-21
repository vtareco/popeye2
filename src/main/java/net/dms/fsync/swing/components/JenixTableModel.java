package net.dms.fsync.swing.components;


import com.google.common.base.CaseFormat;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dminanos on 19/05/2017.
 */
public class JenixTableModel<R,C extends Enum> extends AbstractTableModel {
    protected String[] columnNames;
    protected List<R> rows;
    protected  Class<C> columns;


    public JenixTableModel(Class<C> columns, List<R> rows){
        this.rows = rows;
       // enumConstants = enumeration.getEnumConstants();
        this.columns = columns;
        setColumnNames(columns.getEnumConstants());
    }

    private void setColumnNames(C[] enumConstants){
        columnNames = new String[enumConstants.length];
        int i = 0;
        for (C constant : enumConstants){
            columnNames[i++] = constant.name();
        }
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }


    public String getColumnName(int index) {
        return columnNames[index];
    }

    public R getPayload(int iRow){
        return rows.get(iRow);
    }


    public void load(List<R> rows) {
        this.rows.clear();
        this.rows.addAll(rows);
        this.fireTableDataChanged();

    }

    public void clear(){
        this.rows.clear();
        this.fireTableDataChanged();
    }

    public List<R> getList(){
        return rows;
    }

    public List<R> getElements(int[] indexes){
        List<R> accs = new ArrayList<>();
        for(int i = 0; i < indexes.length; i++){
            accs.add(rows.get(indexes[i]));
        }
        return accs;
    }

    public Color getRowColour(int row) {
        if (row % 2 == 0){
            return MyColors.TABLE_ROW_EVEN;
        }else{
            return MyColors.TABLE_ROW_ODD;
        }
    }


    public C findColumnTypeByOrdinal(int iPosition){
        return Arrays.stream(columns.getEnumConstants()).filter(c -> c.ordinal() == iPosition).findFirst().get();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        C c = findColumnTypeByOrdinal(columnIndex);
        R r = getList().get(rowIndex);
        return getValue(r,c);
    }

    private Object getValue(R r, C c){
        Object value = null;
        try {
            Class<R> clazz = (Class<R>) r.getClass();
            String methodName = "get" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,c.name());
            Method method = clazz.getMethod(methodName);
            value =  method.invoke(r);

        }catch(Exception ex){
           throw new AppException("GEt value is failing: " + ex.getMessage());
        }

        return value;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        C c = findColumnTypeByOrdinal(col);
        R r = getList().get(row);
        setValue(value, r, c);
        fireTableCellUpdated(row, col);
    }

    private void setValue(Object value, R r, C c){
        try {

            Class<R> clazz = (Class<R>) r.getClass();

            String attributeName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,c.name());
            Field field = clazz.getDeclaredField(attributeName);

            String methodName = "set" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,c.name());
            Method method = clazz.getMethod(methodName, field.getType());

            if (field.getType().equals(Double.class)) {
                method.invoke(r, value == null? null : Double.valueOf(value.toString()));
            }else{
                method.invoke(r, value);
            }

        }catch(Exception ex){
            ex.printStackTrace();
            throw new AppException(String.format("Set value is failing, column: %s, value: %s, error: %s", c, value, ex.getMessage()));
        }

    }

    public boolean isCellEditable(int row, int col)
    {

        return ((TableColumnEnumType)findColumnTypeByOrdinal(col)).isEditable();

    }

    private static void makeAccessible(final Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
                && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public C[] getColumnIndentifiers(){
        return columns.getEnumConstants();
    }

}