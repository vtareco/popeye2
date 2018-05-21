package net.dms.fsync.synchronizer.fenix.business;

import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.synchronizer.fenix.control.FenixRepository;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;
import net.dms.fsync.synchronizer.fenix.entities.FenixIncidencia;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciasMetaDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by dminanos on 17/04/2017.
 */
@Service
public class FenixService {

    @Autowired
    private FenixRepository fenixRepository;


    private EverisConfig config = EverisConfig.getInstance();

    public List<FenixAcc> searchAccByPeticionId(Long idPeticion, boolean forceDownload){
        return fenixRepository.searchACCs(idPeticion, forceDownload);
    }

    public List<FenixIncidencia> searchIncidenciasByOtId(Long idOt, boolean forceDownload){
        return fenixRepository.searchIncidenciasByOtId(idOt, forceDownload);
    }


    public void addACCs(List<FenixAcc> accs){
        fenixRepository.addACCs(accs);
    }

    public void uploadACCs(Long idPeticion) {
        fenixRepository.uploadACCs(idPeticion);
    }

    public void saveACCs(List<FenixAcc> accs) {
        fenixRepository.saveCCs(accs);
    }

    public List<String> getPeticionesActuales(){
       File parent = new File(config.getProperty(EverisPropertiesType.PROJECT_PATH));

       List<String> childs = new ArrayList<>();

       Pattern pattern = Pattern.compile(config.getProperty(EverisPropertiesType.PETICIONES_FOLDER_PATTERN), Pattern.CASE_INSENSITIVE);

       for (File file : parent.listFiles()){
          if (pattern.matcher(file.getName()).matches()){
              childs.add(file.getName());
          }
       }

       return childs;
    }

    public void saveIncidencia(List<FenixIncidencia> incidencias){
        fenixRepository.saveIncidencias(incidencias);
    }

    public void uploadIncidencias(Long idPeticionOt) {
        fenixRepository.uploadIncidencias(idPeticionOt);
    }

    public static void main(String[] args){

        Pattern pattern = Pattern.compile("\\d.*-.*", Pattern.CASE_INSENSITIVE);

        System.out.println(pattern.matcher("890584-STOR-55 - BMW Welt delivery").matches());

    }

    public Map<IncidenciasMetaDataType,Map> getIncidenciasMetaData(Long idOt) {
        return fenixRepository.getIncidenciasMetaData(idOt);
    }
}
