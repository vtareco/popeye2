package net.dms.fsync.synchronizer.fenix.business;

import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import net.dms.fsync.synchronizer.fenix.control.FenixRepository;
import net.dms.fsync.synchronizer.fenix.entities.*;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccStatus;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccType;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.IncidenciasMetaDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 17/04/2017.
 */
@Service
public class FenixService {

    @Autowired
    private FenixRepository fenixRepository;

    private EverisConfig config = EverisConfig.getInstance();

    public static void main(String[] args) {

        Pattern pattern = Pattern.compile("\\d.*-.*", Pattern.CASE_INSENSITIVE);

        System.out.println(pattern.matcher("890584-STOR-55 - BMW Welt delivery").matches());

    }

    public List<FenixAcc> searchAccByPeticionId(Long idPeticion, boolean forceDownload) {
        System.out.println("IDZINHO " + idPeticion);
        return fenixRepository.searchACCs(idPeticion, forceDownload);
    }

    public List<FenixIncidencia> searchIncidenciasByOtId(Long idOt, boolean forceDownload) {
        return fenixRepository.searchIncidenciasByOtId(idOt, forceDownload);
    }

    public List<FenixDuda> searchDudasByOtId(Long idOt, boolean forceDownload) {
        return fenixRepository.searchDudasByOtId(idOt, forceDownload);
    }

    public void addACCs(List<FenixAcc> accs) {
        fenixRepository.addACCs(accs);
    }

    public void uploadACCs(Long idPeticion) {
        fenixRepository.uploadACCs(idPeticion);
    }

    public void saveACCs(List<FenixAcc> accs) {
        fenixRepository.saveCCs(accs);
    }

    public List<String> getPeticionesActuales() {
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        File parent = new File(ap.getWorkingDirectory());

        List<String> childs = new ArrayList<>();


        Pattern pattern = Pattern.compile(config.getProperty(EverisPropertiesType.PETICIONES_FOLDER_PATTERN), Pattern.CASE_INSENSITIVE);

        for (File file : parent.listFiles()) {
            if (pattern.matcher(file.getName()).matches()) {
                childs.add(file.getName());
            }
        }
        Collections.reverse(childs);

        return childs;
    }

    public void saveIncidencia(List<FenixIncidencia> incidencias) {
        fenixRepository.saveIncidencias(incidencias);
    }

    public void saveDuda(List<FenixDuda> dudas) {
        fenixRepository.saveDudas(dudas);
    }

    public void uploadIncidencias(Long idPeticionOt) {
        fenixRepository.uploadIncidencias(idPeticionOt);
    }

    /*AQUI*/
    public void uploadDudas(Long idPeticionOt) {
        fenixRepository.uploadDudas(idPeticionOt);
    }

    public Map<IncidenciasMetaDataType, Map> getIncidenciasMetaData(Long idOt) {
        System.out.println("1 passo");
        return fenixRepository.getIncidenciasMetaData(idOt);
    }

    public void createRequirementSpecification(List<FenixAcc> accs) {
        if (!accs.isEmpty()) {
            List<FenixRequirementSpecification> requirementSpecifications = new ArrayList<>();
            for (FenixAcc acc : accs.stream()
                    .filter(a -> !a.getEstado().equals(AccStatus.DESESTIMADA.getDescription()))
                    .filter(a -> a.getTipo().equals(AccType.USER_STORY.getDescription()))
                    .collect(Collectors.toList())) {
                FenixRequirementSpecification requirementSpecification = new FenixRequirementSpecification();
                requirementSpecification.setCodigo(acc.getHistoriaUsuario());
                requirementSpecification.setDescription(acc.getNombre());
                requirementSpecification.setHours(Double.toString(acc.getTotalEsfuerzo()));
                requirementSpecification.setStoryPoints(acc.getPuntosHistoria());
                requirementSpecifications.add(requirementSpecification);
            }
            File peticionDir = fenixRepository.getPeticionDir(accs.get(0).getIdPeticionOtAsociada());
            String fileName = String.format("%s/%s_Especificacion_Requerimientos_RSPSales.xlsx", peticionDir.getAbsolutePath(), accs.get(0).getIdPeticionOtAsociada());
            fenixRepository.saveSpecificationRequirements(fileName, requirementSpecifications);
        }
    }

    public void save(FenixPeticion peticion) {
        // fenixRepository.save(peticion);
    }
}
