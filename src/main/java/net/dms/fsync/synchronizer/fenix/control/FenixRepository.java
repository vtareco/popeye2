package net.dms.fsync.synchronizer.fenix.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.fsync.httphandlers.common.Utils;
import net.dms.fsync.httphandlers.control.ActionExecutor;
import net.dms.fsync.httphandlers.control.actions.DownloadAction;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.EverisVariables;
import net.dms.fsync.swing.EverisManager;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import net.dms.fsync.synchronizer.fenix.control.handlers.GetIncidenciaMetaDataAction;
import net.dms.fsync.synchronizer.fenix.entities.*;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 14/04/2017.
 */
@Component
public class FenixRepository {

    EverisManager em;

    private Logger logger = LoggerFactory.getLogger(FenixRepository.class);

    private FenixAccMapper accMapper = new FenixAccMapper();

    private FenixIncidenciaMapper incidenciaMapper = new FenixIncidenciaMapper();
    /*AQUI*/
    private FenixDudaMapper dudaMapper = new FenixDudaMapper();

    private FenixRequirementSpecificationMapper fenixRequirementSpecificationMapper = new FenixRequirementSpecificationMapper();

    public void downloadACCs(Long idPeticion, String pathFile) {

        Map<String, String> variables = createFenixVariables(idPeticion.toString());

// TODO FIXME critical, extranet pilot
        // ActionExecutor el = new ActionExecutor("/bmw/rsp/executions/extranet_login.xml", variables);
        // el.execute();

        //  ExtranetLoginAction el = new ExtranetLoginAction(variables);
//el.execute();

        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();

        DownloadAction fs = new DownloadAction("/bmw/rsp/executions/fenix_download_accs.xml", pathFile, variables);
        fs.execute();
    }

    public void downloadACCsIncurridos(Long idPeticionOt, String pathFile) {

        //TODO FIXME, avoid login
        Map<String, String> variables = createFenixVariables(idPeticionOt.toString());
        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();

        // TODO FIXME, DISABLED FOr agile
        DownloadAction fs = new DownloadAction("/bmw/rsp/executions/fenix_download_accs_incurridos.xml", pathFile, variables);
        fs.execute();
    }

    public void uploadACCs(Long idPeticionOt) {

        Map<String, String> variables = createFenixVariables(idPeticionOt.toString());
        variables.put(EverisVariables.UPLOAD_FILE.getVariableName(), getACCsFile(idPeticionOt).getAbsolutePath().replaceAll("\\\\", "/"));

        // TODO FIXME
        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();


        ActionExecutor upload = new ActionExecutor("/bmw/rsp/executions/fenix_upload_accs.xml", variables);
        upload.execute();




    }

    public void uploadIncidencias(Long idPeticionOt) {
        Map<String, String> variables = createFenixVariables(idPeticionOt.toString());
        variables.put(EverisVariables.UPLOAD_FILE.getVariableName(), getIncidenciasFile(idPeticionOt).getAbsolutePath().replaceAll("\\\\", "/"));

        // TODO FIXME
        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();


        ActionExecutor upload = new ActionExecutor("/bmw/rsp/executions/fenix_upload_incidencias.xml", variables);
        upload.execute();


    }

    public void uploadDudas(Long idPeticionOt) {
        Map<String, String> variables = createFenixVariables(idPeticionOt.toString());
        variables.put(EverisVariables.UPLOAD_FILE.getVariableName(), getDudasFile(idPeticionOt).getAbsolutePath().replaceAll("\\\\", "/"));

        // TODO FIXME
        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();


        ActionExecutor upload = new ActionExecutor("/bmw/rsp/executions/fenix_upload_dudas.xml", variables);
        upload.execute();
    }

    public List<FenixAcc> searchACCsIncurridos(Long idOt, boolean forceDownload) {
        List<FenixAcc> fenixAccs = new ArrayList<FenixAcc>();
        File accFile = getACCsIncurridosFile(idOt);
        if (forceDownload) {
            removeFile(accFile);
        }
        InputStream fis;
        try {
            if (!accFile.exists()) {
                downloadACCsIncurridos(idOt, accFile.getAbsolutePath());
            }
            fis = new FileInputStream(accFile);

            Workbook wb = WorkbookFactory.create(fis);
            fis.close();
            Sheet sheet = wb.getSheet("Actuaciones");

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {

                if (sheet.getRow(i).getCell(AccIncurridoRowType.ID_ACC.getColPosition()) == null) {
                    break;
                }
                FenixAcc fenixAcc = accMapper.mapIncurridos(sheet.getRow(i));
                fenixAccs.add(fenixAcc);
            }

        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return fenixAccs;
    }

    public List<FenixAcc> searchACCs(Long idOt, boolean forceDownload) {
        List<FenixAcc> fenixAccs = new ArrayList<FenixAcc>();
        LocalVariables lv = new LocalVariables();
        File accFile = getACCsFile(idOt);
        if (forceDownload) {
            removeFile(accFile);
        }
        InputStream fis;
        try {
            if (!accFile.exists()) {
                downloadACCs(idOt, accFile.getAbsolutePath());
            }
            fis = new FileInputStream(accFile);

            Workbook wb = new XSSFWorkbook(fis);
            IOUtils.closeQuietly(fis);
            Sheet sheet = wb.getSheet("Repositorio ACC");

            for (int i = 1; i < sheet.getLastRowNum(); i++) {

                if (isAValidRow(sheet, i)) {
                    FenixAcc fenixAcc = accMapper.map(sheet.getRow(i));
                    fenixAcc.setIdPeticion(  lv.readOtInfoFile(WorkingJira.getIdOt()).getId_peticion());
                    fenixAccs.add(fenixAcc);

                }
            }


            List<FenixAcc> accsIncurridos = searchACCsIncurridos(idOt, forceDownload);
            int iAcc;

            fenixAccs = fenixAccs.stream().filter(acc -> !AccStatus.DESESTIMADA.getDescription().equals(acc.getEstado())).collect(Collectors.toList());
            for (FenixAcc accInc : fenixAccs) {
                iAcc = accsIncurridos.indexOf(accInc);
                if (iAcc >= 0) {
                    accInc.setIncurrido(accsIncurridos.get(iAcc).getIncurrido());
                    accInc.setEtc(accsIncurridos.get(iAcc).getEtc());
                }
            }

        } catch (IOException ex) {
            throw new AppException(ex);
        }
/*
        // TODO FIXME, 1.load asynchronously, 2.avoiding load the object each time
        FenixPeticion fenixPeticion = loadPeticion(idOt);
        if (fenixPeticion != null) {
            mergeLocalData(fenixPeticion, fenixAccs);
        }
        */

        return fenixAccs;
    }

    private boolean isAValidRow(Sheet sheet, int i) {
        if (sheet.getRow(i) == null || sheet.getRow(i).getCell(AccRowType.ID_PETICIONOT_ASOCIADA.getColPosition()) == null) {
            return false;
        }
        Row row = sheet.getRow(i);
        Cell otCell = row.getCell(AccRowType.ID_PETICIONOT_ASOCIADA.getColPosition());
        String stringOTid = otCell.toString();
        return !StringUtils.isBlank(stringOTid);
    }

    public void removeFile(File file) {
        if (file.exists()) {
            File newFile = new File(file.getParent() + "/" + file.getName() + "." + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));

            try {
                Files.move(file.toPath(), newFile.toPath());
            } catch (IOException e) {
                throw new AppException(e);
            }
        }
    }

    public File getPeticionDir(Long idPeticion) {
        LocalVariables lv = new LocalVariables();
        File path = null;

        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();
        File dirProject = new File(projectPath);

        for (File child : dirProject.listFiles()) {
            if (child.exists() && child.isDirectory() && child.getName().startsWith(idPeticion + "-")) {
                path = child;
                break;
            }
        }

        return path;
    }

    public File getACCsTemplate() {
        return getFile(Utils.getProgramRoot()+"\\ExcelTemplates","\\PlantillaCargaMasivaActuacionesCortas.xlsx", "/fenix/templates/PlantillaCargaMasivaActuacionesCortas.xlsx");

    }

    public File getIncidenciasTemplate() {
        return getFile(Utils.getProgramRoot()+"\\ExcelTemplates","\\PlantillaCargaMasivaIncidencias.xls", "/fenix/templates/PlantillaCargaMasivaIncidencias.xls");

    }

    /* AQUI */
    public File getDudasTemplate() {
        return getFile(Utils.getProgramRoot()+"\\ExcelTemplates", "\\Plantilla_de_dudas.xlsx", "/fenix/templates/Plantilla_de_dudas.xlsx");
    }

    public File getFile(String diskFolder, String fileName, String filePathInJar) {
        String filePath = diskFolder + fileName;
        Path path = Paths.get(filePath);

        InputStream resource = getClass().getResourceAsStream(filePathInJar); //informação pronta a deitar fora


        File file = null;
        try {


            new File(diskFolder).mkdirs();

            file = Utils.convertInputStreamToFileCommonWay(resource, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }

    public File getACCsFile(Long idPeticion) {
        File path;
        File peticionFile = getPeticionDir(idPeticion);
        if (peticionFile == null) {
            throw new AppException("Peticion folder doesn´t exist");
        }
        String fileName = EverisConfig.getInstance().getProperty(EverisPropertiesType.ACC_FILE_NAME);
        fileName = Utils.replaceVariable("idPeticionOt", idPeticion.toString(), fileName);
        path = new File(peticionFile.getAbsolutePath() + "/" + fileName);
        return path;
    }

    public File getIncidenciasFile(Long idPeticionOt) {
        File path;
        File incidenciaFile = getPeticionDir(idPeticionOt);
        if (incidenciaFile == null) {
            throw new AppException("Peticion folder doesn´t exist");
        }
        String fileName = EverisConfig.getInstance().getProperty(EverisPropertiesType.INCIDENCIAS_FILE_NAME);
        fileName = Utils.replaceVariable("idPeticionOt", idPeticionOt.toString(), fileName);
        path = new File(incidenciaFile.getAbsolutePath() + "/" + fileName);
        return path;
    }

    public File getACCsIncurridosFile(Long idPeticionOt) {
        File path;
        File peticionFile = getPeticionDir(idPeticionOt);
        if (peticionFile == null) {
            throw new AppException("Peticion folder doesn´t exist");
        }
        String fileName = EverisConfig.getInstance().getProperty(EverisPropertiesType.ACC_INCURRIDOS_FILE_NAME);
        fileName = Utils.replaceVariable("idPeticionOt", idPeticionOt.toString(), fileName);
        path = new File(peticionFile.getAbsolutePath() + "/" + fileName);
        return path;
    }

    /* AQUI*/
    public File getDudasFile(Long idPeticionOt) {
        File path;
        File dudasFile = getPeticionDir(idPeticionOt);
        System.out.println("AHH " + idPeticionOt);
        if (dudasFile == null) {
            throw new AppException("Peticion folder doesn´t exist");
        }
        String fileName = EverisConfig.getInstance().getProperty(EverisPropertiesType.DUDAS_FILE_NAME);
        fileName = Utils.replaceVariable("idPeticionOt", idPeticionOt.toString(), fileName);
        path = new File(dudasFile.getAbsolutePath() + "/" + fileName);
        return path;
    }


    public void addACCs(List<FenixAcc> accs) {
        File accFile = getACCsFile(accs.get(0).getIdPeticionOtAsociada());
        InputStream fis;
        int lastRow = 0;
        try {

            fis = new FileInputStream(accFile);

            Workbook wb = new XSSFWorkbook(fis);
            fis.close();
            Sheet sheet = wb.getSheet("Repositorio ACC");


            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                System.out.println("cell: " + sheet.getRow(i).getCell(0));
                if (sheet.getRow(i).getCell(AccRowType.ID_PETICIONOT_ASOCIADA.getColPosition()) == null) {
                    lastRow = i;
                    break;
                }

            }

            for (FenixAcc acc : accs) {
                Row row = sheet.createRow(lastRow);
                accMapper.mapAccToRow(wb, row, acc);
                lastRow++;
            }

            FileOutputStream fos = new FileOutputStream(accFile);
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            throw new AppException(ex);
        }

    }

    public void saveCCs(List<FenixAcc> accs) {
        File accFile = getACCsFile(accs.get(0).getIdPeticionOtAsociada());
        File template = getACCsTemplate();
        InputStream fis;
        int lastRow = 1;
        try {

            fis = new FileInputStream(template);

            Workbook wb = new XSSFWorkbook(fis);
            fis.close();
            Sheet sheet = wb.getSheet("Repositorio ACC");

            System.out.println("FenixRepository.saveCCs5");

            for (FenixAcc acc : accs) {
                Row row = sheet.createRow(lastRow);
                accMapper.mapAccToRow(wb, row, acc);
                lastRow++;
            }

            /*
            // remove the others rows
            for (int  i = lastRow; i < sheet.getLastRowNum();  i++){
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }else{
                    break;
                }
            }
            */

            FileOutputStream fos = new FileOutputStream(accFile);
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            throw new AppException(ex);
        }

            Toast.display(Internationalization.getStringTranslated("toastSuccess"), Toast.ToastType.INFO);
            //toast.setVisible(true);


    }

    public List<FenixIncidencia> searchIncidenciasByOtId(Long idOt, boolean forceDownload) {
        List<FenixIncidencia> incidencias = new ArrayList<>();

        File incidenciaFile = getIncidenciasFile(idOt);
        if (forceDownload) {
            removeFile(incidenciaFile);
        }
        InputStream fis;
        try {
            if (!incidenciaFile.exists()) {
                downloadIncidencias(idOt, incidenciaFile.getAbsolutePath());
            }
            fis = new FileInputStream(incidenciaFile);

            Workbook wb = WorkbookFactory.create(fis);
            IOUtils.closeQuietly(fis);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 4; i < sheet.getLastRowNum(); i++) {

                if (sheet.getRow(i) == null || sheet.getRow(i).getCell(IncidenciaRowType.ID_PETICION_OT.getColPosition()) == null
                        || StringUtils.isEmpty(sheet.getRow(i).getCell(IncidenciaRowType.ID_PETICION_OT.getColPosition()).getStringCellValue())) {
                    break;
                }

                logger.debug("Processing row {}", i);
                FenixIncidencia incidencia = incidenciaMapper.map(sheet.getRow(i));
                incidencias.add(incidencia);
                Toast.display(Internationalization.getStringTranslated("toastSuccessUpload"), Toast.ToastType.INFO);
            }


        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
        return incidencias;
    }

    private void downloadIncidencias(Long idOt, String pathFile) {
        Map<String, String> variables = createFenixVariables(idOt.toString());

        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();

        DownloadAction fs = new DownloadAction("/bmw/rsp/executions/fenix_download_incidencias.xml", pathFile, variables);
        fs.execute();
    }

    /*DOWNLOAD AQUI*/
    private void downloadDudas(Long idOt, String pathFile) {
        Map<String, String> variables = createFenixVariables(idOt.toString());

        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        ae.execute();

        DownloadAction fs = new DownloadAction("/bmw/rsp/executions/fenix_download_dudas.xml", pathFile, variables);
        fs.execute();
    }

    public void saveIncidencias(List<FenixIncidencia> incidencias) {
        System.out.println("incidencias com valores");
        File accFile = getIncidenciasFile(Long.valueOf(incidencias.get(0).getIdPeticionOt()));
        File template = getIncidenciasTemplate();
        InputStream fis;
        int lastRow = 4;
        try {

            fis = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(fis);
            fis.close();
            Sheet sheet = wb.getSheetAt(0);


            for (FenixIncidencia incidencia : incidencias) {
                Row row = sheet.createRow(lastRow);
                incidenciaMapper.mapIncidenciaToRow(wb, row, incidencia);
                lastRow++;
            }


            FileOutputStream fos = new FileOutputStream(accFile);
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    public Map<IncidenciasMetaDataType, Map> getIncidenciasMetaData(Long idOt) {

        Map<String, String> variables = createFenixVariables(idOt.toString());
        ActionExecutor ae = new ActionExecutor("/bmw/rsp/executions/fenix_login.xml", variables);
        System.out.println("2 passo");
        ae.execute();

        GetIncidenciaMetaDataAction getIncidencias = new GetIncidenciaMetaDataAction(variables);
        getIncidencias.execute();

        return getIncidencias.getMetadataMaps();
    }

    private Map<String, String> createFenixVariables(String ot) {
        Map<String, String> variables = new HashMap<String, String>();
        variables.put(EverisVariables.FENIX_TIMESTAMP.getVariableName(), new Long(System.currentTimeMillis()).toString());
        variables.put(EverisVariables.FENIX_ID_PETICION_OT.getVariableName(), ot.toString());
        variables.put(EverisVariables.FENIX_URL_BASE.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_URL_BASE));
        variables.put(EverisVariables.FENIX_USER.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_USER));
        variables.put(EverisVariables.FENIX_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_PASSWORD));
        variables.put(EverisVariables.FENIX_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_PASSWORD));
        return variables;
    }

    public void saveSpecificationRequirements(String fileName, List<FenixRequirementSpecification> requirementSpecifications) {
        File requirementsFile = new File(fileName);
        File template = getSpecificationRequirementsTemplate();
        InputStream fis;
        int lastRow = 1;
        try {

            fis = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(fis);
            fis.close();
            Sheet sheet = wb.getSheetAt(0);


            for (FenixRequirementSpecification requirementSpecification : requirementSpecifications) {
                Row row = sheet.createRow(lastRow);
                fenixRequirementSpecificationMapper.mapToRow(wb, row, requirementSpecification);
                lastRow++;
            }


            FileOutputStream fos = new FileOutputStream(requirementsFile);
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }

    public File getSpecificationRequirementsTemplate() {
        return getFile(Utils.getProgramRoot()+"\\ExcelTemplates", "\\PlantillaEspecificacionRequerimientos.xlsx", "/fenix/templates/PlantillaEspecificacionRequerimientos.xlsx");
    }

    public void save(FenixPeticion peticion) {
        ObjectMapper mapper = new ObjectMapper();
        File file = getPeticionLocalDataFile(peticion.getId());
        try {
            mapper.writeValue(file, peticion);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public void mergeLocalData(FenixPeticion peticion, List<FenixAcc> accs) {
        for (FenixAcc acc : accs) {
            FenixAcc pAcc = peticion.searchAcc(acc.getIdAcc());
            if (pAcc != null) {
                acc.setBitacora(pAcc.getBitacora());
            }
        }
    }

    public File getPeticionLocalDataFile(Long idPeticion) {
        File folder = getPeticionDir(idPeticion);
        File file = new File(String.format("%s/%s_peticion_fenix.json", folder.getAbsolutePath(), idPeticion));
        return file;
    }

    public FenixPeticion loadPeticion(Long idPeticion) {
        ObjectMapper mapper = new ObjectMapper();
        File file = getPeticionLocalDataFile(idPeticion);
        try {
            if (file.exists()) {
                return mapper.readValue(file, FenixPeticion.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    /* AQUI */

    public List<FenixDuda> searchDudasByOtId(Long idOt, boolean forceDownload) {

        List<FenixDuda> dudas = new ArrayList<>();

        File dudaFile = getDudasFile(idOt);
        if (forceDownload) {
            removeFile(dudaFile);
        }
        InputStream fis;
        try {
            if (!dudaFile.exists()) {
                downloadDudas(idOt, dudaFile.getAbsolutePath());
            }
            fis = new FileInputStream(dudaFile);

            Workbook wb = WorkbookFactory.create(fis);
            IOUtils.closeQuietly(fis);
            Sheet sheet = wb.getSheetAt(0);
            /*IMPORTANTE*/
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i) == null || sheet.getRow(i).getCell(DudaRowType.ID_REQUERIMIENTO.getColPosition()) == null
                        || StringUtils.isBlank(sheet.getRow(i).getCell(DudaRowType.ID_REQUERIMIENTO.getColPosition()).toString())) {
                    //  || StringUtils.isEmpty(sheet.getRow(i).getCell(DudaRowType.ACC.getColPosition()).getStringCellValue())){ gg
                    //isCreatable(String)
                    continue;
                }
                logger.debug("Processing row {}", i);
                FenixDuda duda = dudaMapper.map(sheet.getRow(i));
                dudas.add(duda);
            }


        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
        return dudas;
    }

    public void saveDudas(List<FenixDuda> dudas) {

        //System.out.println("ULTIMA "+dudas.get(dudas.size()-1).getDescripcion());

        for(FenixDuda d: dudas){
            if(StringUtils.isBlank(d.getDescripcion())){
                throw new AppException("Descripcion es Requerida");
            }
        }

        File dudaFile = getDudasFile(Long.valueOf(dudas.get(0).getIdOt()));
        System.out.println("boy " + (Long.valueOf(dudas.get(0).getIdOt())));
        File template = getDudasTemplate();
        InputStream fis;
        int lastRow = 4;
        try {

            fis = new FileInputStream(template);

            Workbook wb = WorkbookFactory.create(fis);
            fis.close();
            Sheet sheet = wb.getSheetAt(0);


            for (FenixDuda duda : dudas) {
                Row row = sheet.createRow(lastRow);
                dudaMapper.mapDudaToRow(wb, row, duda);
                lastRow++;
            }


            Toast.display("Success !", Toast.ToastType.INFO);
            FileOutputStream fos = new FileOutputStream(dudaFile);
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            throw new AppException(ex);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }
}
