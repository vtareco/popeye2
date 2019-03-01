package net.dms.fsync.swing.preferences;

import javax.swing.table.TableColumn;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.TableSetting;
import net.dms.fsync.swing.components.JenixTable;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.TableColumnEnumType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TableSettingControl {

	private static EverisConfig config = EverisConfig.getInstance();
	private Logger logger = LoggerFactory.getLogger(getClass());

	public TableSetting load(TableType tableType) {
		TableSetting tableSetting;
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(TableColumnEnumType.class, new TableColumnEnumTypeDeserializer(TableType.FENIX_ACC.getColumns()));
		mapper.registerModule(module);
		try {
			File settingFile = new File(getTableSettingFile(tableType));
			if(!settingFile.exists()) {
				tableSetting = new TableSetting(tableType.getColumns());
			} else {
				tableSetting = mapper.readValue(settingFile, TableSetting.class);
			}
			return tableSetting;
		} catch(IOException e) {
			e.printStackTrace();
			return new TableSetting(tableType.getColumns());
		} catch(Exception e) {
			throw new AppException(e);
		}
	}

	public void save(TableType tableType, TableSetting tableSetting) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new FileOutputStream(new File(getTableSettingFile(tableType))), tableSetting);
		} catch(Exception e) {
			throw new AppException(e);
		}
	}

	private String getTableSettingFile(TableType tableType) {
		return config.getProperty(EverisPropertiesType.PROJECT_PATH) + "/_preferences/" + tableType + ".json";
	}

	public void apply(JenixTable table, TableSetting tableSetting) {
		if(tableSetting != null) {
			tableSetting.getAllColumns().stream().forEach(c -> {
															  TableColumn col = table.getColumn(c.getColumn().name());
															  if(c.isVisible()) {
																  int width = col.getWidth() == 0
																				? c.getColumn().getWidth()
																				: col.getWidth();
																  col.setMinWidth(20);
																  col.setMaxWidth(10000);
																  col.setWidth(width);
																  col.setPreferredWidth(width);
															  } else {
																  col.setMinWidth(0);
																  col.setMaxWidth(0);
																  col.setWidth(0);
															  }
														  }
														 );
			table.updateUI();
		}
	}
}
