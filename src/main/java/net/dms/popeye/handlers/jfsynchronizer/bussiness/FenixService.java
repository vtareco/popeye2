package net.dms.popeye.handlers.jfsynchronizer.bussiness;

import net.dms.popeye.handlers.jfsynchronizer.control.EverisConfig;
import net.dms.popeye.handlers.jfsynchronizer.control.EverisPropertiesType;
import net.dms.popeye.handlers.jfsynchronizer.control.FenixRepository;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.IncidenciasMetaDataType;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dminanos on 17/04/2017.
 */
public class FenixService {
    private FenixRepository fenixRepository = new FenixRepository();
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

    public static void main(String[] args){

        Pattern pattern = Pattern.compile("\\d.*-.*", Pattern.CASE_INSENSITIVE);

        System.out.println(pattern.matcher("890584-STOR-55 - BMW Welt delivery").matches());

    }

    public Map<IncidenciasMetaDataType,Map> getIncidenciasMetaData(Long idOt) {
        return fenixRepository.getIncidenciasMetaData(idOt);
    }
}
