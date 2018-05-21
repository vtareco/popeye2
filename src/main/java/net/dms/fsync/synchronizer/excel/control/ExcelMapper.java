package net.dms.fsync.synchronizer.excel.control;

import com.google.common.base.CaseFormat;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 23/05/2017.
 */
public class ExcelMapper<C extends Enum, E> {
    Logger logger = LoggerFactory.getLogger(ExcelMapper.class);

    protected  Class<C> columns;

    public ExcelMapper(Class<C> columns){
        this.columns = columns;
    }

    public void map(Row row, E e, Set<C> excludeColumns){
        int iCol;
        Object value;
        int cellType;
        for (C c : Arrays.stream(columns.getEnumConstants()).filter(c -> !excludeColumns.contains(c)).collect(Collectors.toList())){

             iCol = c.ordinal();

             try {
                 if  (row.getCell(iCol) != null) {
                     cellType = row.getCell(iCol).getCellType();
                     logger.debug("cellType: " + cellType + ", col " + c);
                     switch (cellType) {
                         case 0:
                             value = row.getCell(iCol).getNumericCellValue();
                             break;
                         default:

                             value = row.getCell(iCol).getStringCellValue();

                     }
                     setValue(value, e, c);
                 }
             }catch(Exception ex){
                 logger.error("fail setting column: " + c);
                 throw new AppException("fail setting column: " + c);
             }

        }

    }

    public void mapEntity2Row(Row row, E e, Set<C> excludeColumns){
        for (C c : Arrays.stream(columns.getEnumConstants()).filter(c -> !excludeColumns.contains(c)).collect(Collectors.toList())) {
            Object value = getValue(e, c);
            if (value != null) {
                if (value instanceof Double) {
                    row.createCell(c.ordinal()).setCellValue((Double)value);
                }else if(value instanceof Long){
                    row.createCell(c.ordinal()).setCellValue(new Double((Long)value));
                }else if(value instanceof Integer){
                    row.createCell(c.ordinal()).setCellValue(new Double((Integer)value));
                }else if(value instanceof Date){
                    row.createCell(c.ordinal()).setCellValue((Date)value);
                }else {
                    row.createCell(c.ordinal()).setCellValue((String)value);
                }
            }
        }
    }

/*
    public C findColumnTypeByOrdinal(int iPosition){
        return Arrays.stream(columns.getEnumConstants()).filter(c -> c.ordinal() == iPosition).findFirst().get();
    }*/

   /*
    private Object getValue(int rowIndex, int columnIndex) {
        C c = findColumnTypeByOrdinal(columnIndex);
        R r = getList().get(rowIndex);
        return getValue(r,c);
    }*/

    private Object getValue(E e, C c){
        Object value = null;
        try {
            Class<E> clazz = (Class<E>) e.getClass();
            String methodName = "get" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,c.name());
            Method method = clazz.getMethod(methodName);
            value =  method.invoke(e);

        }catch(Exception ex){
            throw new AppException("GEt value is failing: " + ex.getMessage());
        }

        return value;
    }




    private void setValue(Object value, E e, C c){
        try {
            Class<E> clazz = (Class<E>) e.getClass();
            String fieldName= CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,c.name());
            Field field = clazz.getDeclaredField(fieldName);


            String methodName = "set" + CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,c.name());
            Method method = clazz.getMethod(methodName, field.getType());

            if (value != null) {
                if (field.getType().getName().equals(Long.class.getName())) {
                    value = new Long(((Double) value).longValue());
                }
            }
            method.invoke(e, value);

        }catch(Exception ex){
            ex.printStackTrace();
            throw new AppException("Set value is failing for col " + c + ":" + ex.getMessage());
        }

    }

}
