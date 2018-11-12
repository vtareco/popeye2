package net.dms.fsync.synchronizer.fenix.control;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.synchronizer.excel.control.ExcelMapper;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidencia;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciaRowType;
import org.apache.poi.ss.usermodel.*;

import java.util.*;

/**
 * Created by dminanos on 23/05/2017.
 */
public class FenixIncidenciaMapper {
    ExcelMapper<IncidenciaRowType, FenixIncidencia> excelMapper = new ExcelMapper<>(IncidenciaRowType.class);

    public FenixIncidencia map(Row row) {
        FenixIncidencia incidencia = new FenixIncidencia();

        excelMapper.map(row, incidencia,
                new HashSet<>(Arrays.asList(IncidenciaRowType.FECHA_FIN,
                        IncidenciaRowType.FECHA_INICIO, IncidenciaRowType.FECHA_PREVISTA_CENTRO)));


        incidencia.setFechaPrevistaCentro(row.getCell(IncidenciaRowType.FECHA_PREVISTA_CENTRO.getColPosition()) != null && row.getCell(IncidenciaRowType.FECHA_PREVISTA_CENTRO.getColPosition()).getCellType() != 1 ? row.getCell(IncidenciaRowType.FECHA_PREVISTA_CENTRO.getColPosition()).getDateCellValue() : null);
        incidencia.setFechaInicio(row.getCell(IncidenciaRowType.FECHA_INICIO.getColPosition()) != null ? row.getCell(IncidenciaRowType.FECHA_INICIO.getColPosition()).getDateCellValue() : null);
        incidencia.setFechaFin(row.getCell(IncidenciaRowType.FECHA_FIN.getColPosition()) != null ? row.getCell(IncidenciaRowType.FECHA_FIN.getColPosition()).getDateCellValue() : null);

        return incidencia;
    }


    public void mapIncidenciaToRow(Workbook wb, Row row, FenixIncidencia incidencia) {
        excelMapper.mapEntity2Row(row, incidencia, Collections.EMPTY_SET);

    }
}
