package net.dms.fsync.synchronizer.fenix.control;

import net.dms.fsync.synchronizer.excel.control.ExcelMapper;
import net.dms.fsync.synchronizer.fenix.entities.FenixDuda;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.DudaRowType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by dminanos on 23/05/2017.
 */
public class FenixDudaMapper {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    ExcelMapper<DudaRowType, FenixDuda> excelMapper = new ExcelMapper<>(DudaRowType.class);

    public FenixDuda map(Row row) {
        FenixDuda duda = new FenixDuda();

        excelMapper.map(row, duda,
                new HashSet<>(Arrays.asList(DudaRowType.FECHA_ALTA,
                        DudaRowType.FECHA_PREVISTA_RESPUESTA, DudaRowType.FECHA_ULT_ACT)));


        duda.setFechaAlta(row.getCell(DudaRowType.FECHA_ALTA.getColPosition()) != null && row.getCell(DudaRowType.FECHA_ALTA.getColPosition()).getCellType() != 1 ? row.getCell(DudaRowType.FECHA_ALTA.getColPosition()).getDateCellValue() : null);
        duda.setFechaPrevistaRespuesta(row.getCell(DudaRowType.FECHA_PREVISTA_RESPUESTA.getColPosition()) != null ? row.getCell(DudaRowType.FECHA_PREVISTA_RESPUESTA.getColPosition()).getDateCellValue() : null);
        if (row.getCell(DudaRowType.FECHA_ULT_ACT.getColPosition()) != null) {
            Cell cell = row.getCell(DudaRowType.FECHA_ULT_ACT.getColPosition());

            Date dateCellValue = null;
            try {
                dateCellValue = dateFormat.parse(cell.getStringCellValue());
            } catch (ParseException e) {
            }
            duda.setFechaUltAct(dateCellValue);
        }
        else
            duda.setFechaUltAct(null);

        return duda;
    }


    public void mapDudaToRow(Workbook wb, Row row, FenixDuda duda) {
        excelMapper.mapEntity2Row(row, duda, Collections.EMPTY_SET);

    }
}
