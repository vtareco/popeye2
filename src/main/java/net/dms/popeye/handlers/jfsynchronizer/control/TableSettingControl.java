package net.dms.popeye.handlers.jfsynchronizer.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import net.dms.popeye.handlers.entities.exceptions.AppException;
import net.dms.popeye.handlers.jfsynchronizer.entities.TableType;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.TableColumnEnumType;
import net.dms.popeye.settings.entities.Settings;
import net.dms.popeye.settings.entities.TableSetting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class TableSettingControl {
    private static EverisConfig config = EverisConfig.getInstance();

    public TableSetting load(TableType tableType) {
        TableSetting tableSetting;
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(TableColumnEnumType.class, new TableColumnEnumTypeDeserializer(TableType.FENIX_ACC.getColumns()));
        mapper.registerModule(module);

        try {
            File settingFile = new File(getTableSettingFile(tableType));
            if (!settingFile.exists()) {
                tableSetting = new TableSetting(tableType.getColumns());
            } else {
                tableSetting = mapper.readValue(settingFile, TableSetting.class);
            }

            return tableSetting;
        }catch(IOException e){
            e.printStackTrace();
            return new TableSetting(tableType.getColumns());
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public void save(TableType tableType, TableSetting tableSetting) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new FileOutputStream(new File(getTableSettingFile(tableType))), tableSetting);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    private String getTableSettingFile(TableType tableType){
       return config.getProperty(EverisPropertiesType.PROJECT_PATH) + "/_preferences/" + tableType + ".json";
    }
}
