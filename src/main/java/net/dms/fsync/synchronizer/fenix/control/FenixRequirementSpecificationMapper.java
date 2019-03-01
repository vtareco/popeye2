package net.dms.fsync.synchronizer.fenix.control;

import java.util.Collections;

import net.dms.fsync.synchronizer.excel.control.ExcelMapper;
import net.dms.fsync.synchronizer.fenix.entities.FenixRequirementSpecification;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.RequirementSpecificationRowType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by dminanos on 23/05/2017.
 */
public class FenixRequirementSpecificationMapper {

	ExcelMapper<RequirementSpecificationRowType, FenixRequirementSpecification> excelMapper = new ExcelMapper<>(RequirementSpecificationRowType.class);

	public void mapToRow(Workbook wb, Row row, FenixRequirementSpecification requirementSpecification) {
		excelMapper.mapEntity2Row(row, requirementSpecification, Collections.EMPTY_SET);
	}
}
