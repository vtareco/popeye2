package net.dms.fsync.swing;

import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.business.SettingsService;
import net.dms.fsync.settings.entities.*;
import net.dms.fsync.swing.components.*;
import net.dms.fsync.swing.dialogs.*;
import net.dms.fsync.swing.models.AccTableModel;
import net.dms.fsync.swing.models.DudaTableModel;
import net.dms.fsync.swing.models.IncidenciaTableModel;
import net.dms.fsync.swing.models.JiraTableModel;
import net.dms.fsync.swing.preferences.TableSettingControl;
import net.dms.fsync.swing.preferences.TableType;
import net.dms.fsync.synchronizer.LocalVariables.business.VariableService;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.*;
import net.dms.fsync.synchronizer.fenix.business.FenixService;
import net.dms.fsync.synchronizer.fenix.control.FenixAccMapper;
import net.dms.fsync.synchronizer.fenix.entities.*;
import net.dms.fsync.synchronizer.fenix.entities.enumerations.*;
import net.dms.fsync.synchronizer.jira.business.JiraService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 17/04/2017.
 */
public class EverisManager {
    private JTabbedPane tabbedPanel;
    private JPanel panelParent;
    private JenixTable<JiraTableModel, JiraIssue> jiraTable;
    private JenixTable<AccTableModel, FenixAcc> accTable;
    private JButton refreshJiraBtn;
    private JScrollPane jiraScrollPane;
    private JButton searchACCs;
    private JButton jiraToAccBtn;
    private JButton uploadBtn;
    private JButton saveAccExcelBtn;
    private JComboBox peticionesDisponiblesCmb;
    private JComboBox jiraFiltersCmb;
    private JCheckBox forceDownloadCheckBox;
    private JButton addAccBtn;
    private JButton removeFenixAccBtn;
    private JTextField totalEstimatedText;
    private JTextField totalIncurridoText;
    private JTextField estimadoVersusIncurridoText;
    private JButton addIncidenciaBtn;

    private JenixTable<IncidenciaTableModel, FenixIncidencia> incidenciasTable;
    private JButton saveIncidenciasBtn;
    private JTextField txtJiraTask;
    private JScrollPane accScrollPane;
    private JButton checkJiraStatusBtn;
    private JButton uploadIncidenciasBtn;
    private JPanel otPanel;
    private JButton removeIncidenciaBtn;
    private JButton refreshIncidenciasBtn;
    private JScrollPane incidenciasScrollPane;
    private JButton configFenixTable;
    private JButton generateSpecificationRequirementsBtn;
    private JPopupMenu refreshMenu;
    private JButton settingsJbtn;
    private File jenixFoulder = new File(WorkingJira.getJenixFoulder());
    private File jsonUserCreate = new File(WorkingJira.getJsonUserCreate());
    private File jsonApplicationProperties = new File(WorkingJira.getJsonApplicationProperties());
    private File jsonFilters = new File(WorkingJira.getJsonFilters());

    /* AQUI */
    private JenixTable<DudaTableModel, FenixDuda> dudasTable;
    private JButton addDudasBtn;
    private JButton saveDudasBtn;
    private JButton uploadDudasBtn;
    private JButton refreshDudasBtn;
    private JButton removeDudasBtn;
    private JScrollPane dudasScrollPane;


    JiraService jiraService;
    FenixService fenixService;
    TableSettingControl tableSettingControl;
    FenixAccMapper accMapper = new FenixAccMapper();
    EverisConfig config = EverisConfig.getInstance();
    Settings settings = SettingsService.getInstance().getSettings();


    private Map<String, String> jiraFilters = config.getJiraFilters();


    public static void main(String[] args) {
        JFrame frame = new JFrame("Jira / Fenix");
        frame.setContentPane(new EverisManager().panelParent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }


    public EverisManager() {

        $$$setupUI$$$();
        SwingUtil.registerListener(refreshJiraBtn, this::refreshJira, this::handleException);
        SwingUtil.registerListener(searchACCs, this::loadAccs, this::handleException);
        SwingUtil.registerListener(jiraToAccBtn, this::jiraToAcc, this::handleException);
        SwingUtil.registerListener(uploadBtn, this::uploadAccs, this::handleException);
        SwingUtil.registerListener(saveAccExcelBtn, this::saveAccs, this::handleException);
        SwingUtil.registerListener(peticionesDisponiblesCmb, this::loadAccs, this::handleException);
        SwingUtil.registerListener(uploadIncidenciasBtn, this::uploadIncidencias, this::handleException);
        SwingUtil.registerListener(refreshIncidenciasBtn, this::refreshIncidencias, this::handleException);
        SwingUtil.registerListener(generateSpecificationRequirementsBtn, this::generateSpecificationReuirements, this::handleException);


        init();

        SwingUtil.registerListener(jiraFiltersCmb, this::filterSelectorHandler, this::handleException);
        SwingUtil.registerListener(addAccBtn, this::addAcc, this::handleException);
        SwingUtil.registerListener(removeFenixAccBtn, this::removeAcc, this::handleException);

        SwingUtil.registerListener(saveIncidenciasBtn, this::saveIncidencias, this::handleException);
        SwingUtil.registerListener(removeIncidenciaBtn, this::removeIncidencia, this::handleException);
        SwingUtil.registerListener(checkJiraStatusBtn, this::checkJiraStatus, this::handleException);
        SwingUtil.registerListener(configFenixTable, this::configFenixTable, this::handleException);


        /* AQUI */
        SwingUtil.registerListener(saveDudasBtn, this::saveDudas, this::handleException);
       // SwingUtil.registerListener(uploadDudasBtn, this::uploadDudas, this::handleException);
        SwingUtil.registerListener(removeDudasBtn, this::removeDuda, this::handleException);
        SwingUtil.registerListener(refreshDudasBtn, this::refreshDudas, this::handleException);


        SwingUtil.registerListener(settingsJbtn, this::confingJenixSettings, this::handleException);

        tabbedPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {

                    System.out.println("Tab: " + tabbedPanel.getSelectedIndex());

                    if(tabbedPanel.getSelectedIndex()==1){
                        initTabIncidencias();
                    }else{
                        initTabDudas();
                    }

             /*   if (!ComponentStateService.getInstance().isInitialized(EverisComponentType.TAB_INCIDENCIA)){
                    System.out.println("incidencias");
                    initTabIncidencias();
                  }*/
                    if (!ComponentStateService.getInstance().isInitialized(EverisComponentType.TAB_DUDA)) {
                        System.out.println("dudas");
                        initTabDudas();
                    }

                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });

        onInit();
    }


    private void onInit() {
        if (!jenixFoulder.exists()) {
            jenixFoulder.mkdirs();
            System.out.println("Directory created in " + jenixFoulder.toString());
            try {
                if (jsonUserCreate.createNewFile()) {
                    System.out.println("user config json created");
                    VariableService vs = new VariableService();
                    UserChange uc = vs.getUserVariables();
                    vs.createUserJson(uc, jsonUserCreate.toString());
                }
                if (jsonApplicationProperties.createNewFile()) {
                    System.out.println("properties config json created");
                    VariableService vs = new VariableService();
                    ApplicationProperties ap = vs.getApplicationVariables();
                    vs.createApplicationPropertiesJson(ap, jsonApplicationProperties.toString());


                }
                if (jsonFilters.createNewFile()) {
                    VariableService vs = new VariableService();
                    vs.createFilterJson(jsonFilters.toString());
                    System.out.println("filters config json created");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            System.out.println("Directory Already Exists");
            try {
                if (!jsonUserCreate.exists()) {
                    if (jsonUserCreate.createNewFile()) {
                        VariableService vs = new VariableService();
                        UserChange uc = vs.getUserVariables();
                        vs.createUserJson(uc, jsonUserCreate.toString());
                        System.out.println("user config json created");
                    }
                }
                if (!jsonApplicationProperties.exists()) {
                    if (jsonApplicationProperties.createNewFile()) {
                        VariableService vs = new VariableService();
                        ApplicationProperties ap = vs.getApplicationVariables();
                        vs.createApplicationPropertiesJson(ap, jsonApplicationProperties.toString());
                        System.out.println("properties config json created");
                    }
                }
                if (!jsonFilters.exists()) {
                    if (jsonFilters.createNewFile()) {
                        VariableService vs = new VariableService();
                        vs.createFilterJson(jsonFilters.toString());
                        System.out.println("filters config json created");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void generateSpecificationReuirements() {
        fenixService.createRequirementSpecification(accTable.getList());
    }

    private void configFenixTable() {

        TableSetting tableSetting = tableSettingControl.load(TableType.FENIX_ACC);

        ColumnsSettingDialog dialog = new ColumnsSettingDialog(panelParent, tableSetting);
        dialog.pack();
        if (dialog.getPayload() != null) {
            tableSettingControl.save(TableType.FENIX_ACC, dialog.getPayload());

            tableSettingControl.apply(accTable, dialog.getPayload());
        }
    }

    private void refreshIncidencias() {
        incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), this.forceDownloadCheckBox.isSelected()));

    }

    private void  refreshDudas(){
        dudasTable.getModel().load(fenixService.searchDudasByOtId(getPeticionSelected(peticionesDisponiblesCmb), this.forceDownloadCheckBox.isSelected()));
    }

    private void checkJiraStatus() {
        JsonPaths jsonpaths = new JsonPaths(jsonUserCreate.toString(), jsonApplicationProperties.toString(), jsonFilters.toString());
        List<FenixAcc> accs = accTable.getModel().getList();
        Set<String> jiraCodes = accs.stream().map(FenixAcc::getCodigoPeticionCliente).collect(Collectors.toSet());
        jiraCodes = jiraCodes.stream().filter(c -> isValidJiraCode(c)).collect(Collectors.toSet());

        // TODO FIXME, filter should be parametrized
        //String jql = String.format(settings.getJiraSettings().getFilterByIds(), String.join(",", jiraCodes));
        String jql = String.format("issue in (%s)", String.join(",", jiraCodes));

        JiraSearchResponse jiraSearchResponse = jiraService.search(jql);
        List<JiraIssue> issues = jiraSearchResponse.getIssues();
        for (FenixAcc acc : accs) {
            JiraIssue issue = issues.stream().filter(i -> i.getKey().equals(acc.getCodigoPeticionCliente())).findFirst().orElse(null);
            if (issue != null) {
                acc.setJiraStatus(issue.getFields().getStatus().getName());
            }
        }
        accTable.updateUI();
    }

    private void saveIncidencias() {
        fenixService.saveIncidencia(incidenciasTable.getList());
    }

    /* AQUI */
    private void saveDudas() {
        fenixService.saveDuda(dudasTable.getList());
    }

    private void removeAcc() {
        AccTableModel accTM = accTable.getModel();
        accTM.getList().removeAll(accTM.getElements(accTable.getModelSelectedRows()));
        accTM.fireTableDataChanged();
    }

    private void removeIncidencia() {
        IncidenciaTableModel tableModel = incidenciasTable.getModel();
        tableModel.getList().removeAll(tableModel.getElements(incidenciasTable.getModelSelectedRows()));
        tableModel.fireTableDataChanged();
    }

    /* AQUI */
    private void removeDuda() {
        DudaTableModel dudaTableModel = dudasTable.getModel();
        dudaTableModel.getList().removeAll(dudaTableModel.getElements(dudasTable.getModelSelectedRows()));
        dudaTableModel.fireTableDataChanged();
    }

    private void confingJenixSettings() {
        JsonPaths jsonpaths = new JsonPaths(jsonUserCreate.toString(), jsonApplicationProperties.toString(), jsonFilters.toString());
        SettingsDialog settingsDialog = new SettingsDialog(tabbedPanel, jsonpaths);
        settingsDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("hey");
                refreshJiraFiltersCMB();
            }
        });
        settingsDialog.pack();

    }


    private void addAcc() {
        AccTableModel accTableModel = accTable.getModel();
        FenixAcc acc = new FenixAcc();

        acc.setCriticidad(Criticidad.MEDIA.getDescription());
        acc.setIdPeticionOtAsociada(getPeticionSelected(peticionesDisponiblesCmb));
        acc.setEstado(AccStatus.PENDIENTE_ASIGNACION.getDescription());
        acc.setRechazosEntrega(0);

        AccDialog dialog = new AccDialog(panelParent, acc);
        dialog.pack();

        FenixAcc editedAcc = dialog.getPayload();
        if (editedAcc != null) {
            accTableModel.getList().add(editedAcc);
            accTableModel.fireTableDataChanged();

            accTable.setRowSelectionInterval(accTableModel.getRowCount() - 1, accTableModel.getRowCount() - 1);
        }

    }

    private void uploadIncidencias() {
        fenixService.uploadIncidencias(getPeticionSelected(peticionesDisponiblesCmb));
        incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), true));
    }

    private void uploadDudas(){
        fenixService.uploadDudas(getPeticionSelected(peticionesDisponiblesCmb));
        dudasTable.getModel().load(fenixService.searchDudasByOtId(getPeticionSelected(peticionesDisponiblesCmb),true));
    }

    private void saveAccs() {
        FenixPeticion fenixPeticion = new FenixPeticion();
        fenixPeticion.setId(getPeticionSelected(peticionesDisponiblesCmb));
        fenixPeticion.setAccList(accTable.getModel().getList());
        fenixService.save(fenixPeticion);
        fenixService.saveACCs(accTable.getModel().getList());
        refreshTotales();
    }

    private void uploadAccs() {
        fenixService.uploadACCs(getPeticionSelected(peticionesDisponiblesCmb));
        accTable.getModel().load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), true));
        refreshTotales();
    }

    private void jiraToAcc() {
        List<FenixAcc> accs = new ArrayList<>();

        List<JiraIssue> issues = ((JiraTableModel) jiraTable.getModel()).getElements(jiraTable.getModelSelectedRows());

        for (JiraIssue issue : issues) {
            FenixAcc acc = accMapper.mapJiraIssue2Acc(issue);
            acc.setIdPeticionOtAsociada(getPeticionSelected(peticionesDisponiblesCmb));


            AccDialog dialog = new AccDialog(panelParent, acc);
            dialog.pack();

            FenixAcc editedAcc = dialog.getPayload();
            if (editedAcc != null) {
                accs.add(editedAcc);
            }

        }


        accTable.getModel().getList().addAll(accs);
        accTable.getModel().fireTableDataChanged();
    }

    private static boolean isValidJiraCode(String jiraCode) {
        if (jiraCode == null) {
            return false;
        } else {
            // TODO fixme PARAMetrize validation
            Pattern pattern = Pattern.compile(".+-.+", Pattern.CASE_INSENSITIVE);
            return pattern.matcher(jiraCode).matches() && !jiraCode.startsWith("SCDMS ");
        }
    }

    private void refreshTotales() {
        //TODO FIXME

        List<FenixAcc> accs = accTable.getModel().getList();
        double totalEstimado = 0;
        double totalIncurrido = 0;

        for (FenixAcc acc : accs) {

            if (acc.getIncurrido() != null) {
                totalIncurrido = totalIncurrido + acc.getIncurrido();
            }
            if (acc.getEsfuerzo() != null && acc.getIncurrido() != null && acc.getIncurrido().doubleValue() != 0
                    || !acc.getEstado().equals(AccStatus.DESESTIMADA.getDescription())) {
                totalEstimado = totalEstimado + acc.getTotalEsfuerzo();
            }

        }
        double estimadoVsIncurrido = totalIncurrido * 100 / totalEstimado;
        totalEstimatedText.setText(Double.toString(totalEstimado));
        totalIncurridoText.setText(Double.toString(totalIncurrido));
        estimadoVersusIncurridoText.setText(Double.toString(estimadoVsIncurrido));


    }


    private void filterSelectorHandler() {
        LocalVariables lv = new LocalVariables();
        if(jiraFiltersCmb.getSelectedItem() != null){
            Filter filter = lv.getSelectedFilter(jiraFiltersCmb.getSelectedItem().toString(), jsonFilters.toString());
            if (!filter.getFilterName().equals("null")) {
                if (isSelectedFilterById()) {
                    txtJiraTask.setVisible(true);

                } else {
                    refreshJira();
                }
            }
        }


    }

    private boolean isSelectedFilterById() {
        return EverisPropertiesType.JIRA_FILTRO_BY_ID.getProperty().equals(WorkingJira.getJiraFilterProperty() + jiraFiltersCmb.getSelectedItem());
    }

    private void loadAccs() {
        resetForm();
        if (getPeticionSelected(peticionesDisponiblesCmb) != null) {
            accTable.getModel().load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));
            refreshTotales();
        }
    }

    private void refreshJira() {
        String filter;
        String text = txtJiraTask.getText();
        LocalVariables lv = new LocalVariables();
        if (isSelectedFilterById()) {
            filter = String.format(getJiraFilterSelected(), txtJiraTask.getText());
        } else {
            filter = getJiraFilterSelected();
        }

        if (filter.equals("key=")) {
            throw new AppException("No se ha introducido ninguna ACC");
        } else if (filter != null) {
            searchJiras(((JiraTableModel) jiraTable.getModel())::load, filter);

        }


    }

    private void searchJiras(Consumer<List<JiraIssue>> consumer, String filter) {

        JiraSearchResponse response = jiraService.search(filter);
        consumer.accept(response.getIssues());

   /*for(JiraIssue j: response.getIssues()){
     if(j.getKey().compareToIgnoreCase(filter.substring(4))!=0){
       System.out.println("key "+j.getKey());
       System.out.println("filter "+filter.substring(4));
       errorPopup();
     }
   }*/
    }

    private void handleException(Exception ex) {
        System.out.println("error swing: " + ex.getMessage());
        try {
            ex.printStackTrace();
            JLabel label = new JLabel("<html><p> " + ex.getMessage() + "</p></html>");
            label.setMaximumSize(new Dimension(480, 300));
            label.setPreferredSize(new Dimension(480, 300));
            JOptionPane.showMessageDialog(panelParent, label);
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
    }

    private Long getPeticionSelected(JComboBox combo) {
        String selected = (String) combo.getSelectedItem();
        if (StringUtils.isEmpty(selected)) {
            return null;
        } else {
            return new Long(selected.split("-")[0]);
        }
    }

    private String getJiraFilterSelected() {
        LocalVariables lv = new LocalVariables();
        String selected = (String) jiraFiltersCmb.getSelectedItem();
        if (StringUtils.isEmpty(selected)) {
            return null;
        } else {
            return lv.getSelectedFilter(jiraFiltersCmb.getSelectedItem().toString(), jsonFilters.toString()).getFilterQuery();
        }
    }


    private void init() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        fenixService = context.getBean(FenixService.class);
        jiraService = context.getBean(JiraService.class);

        tableSettingControl = context.getBean(TableSettingControl.class);
        initTabSyncJiraFenix();


    }

    private void refreshJiraFiltersCMB() {
        jiraFiltersCmb.removeAllItems();
        LocalVariables lv = new LocalVariables();
        List<String> peticionesActuales = fenixService.getPeticionesActuales();
        ArrayList<String> filtersName = new ArrayList<>();
        for (Filter f : lv.filterList(jsonFilters.toString())) {
            filtersName.add(f.getFilterName());
        }
        SwingUtil.loadComboBox(filtersName, jiraFiltersCmb, true);

    }


    private void initTabSyncJiraFenix() {

        List<String> peticionesActuales = fenixService.getPeticionesActuales();


        //SwingUtil.loadComboBox(jiraFilters.keySet(), jiraFiltersCmb, true);

        refreshJiraFiltersCMB();
        // txtJiraTask.setVisible(false);


        JComboBox accStatusEditor = new JComboBox();
        SwingUtil.loadComboBox(AccStatus.class, accStatusEditor, false);


        JComboBox accResponsableEditor = new JComboBox();
        Map<String, String> responsableJiraEveris = config.getMapResponsableJiraEveris();


        for (Actor responsableJira : settings.getActores()) {
            accResponsableEditor.addItem(responsableJiraEveris.get(responsableJira.getNumeroEmpleadoEveris()));

        }
        accResponsableEditor.setToolTipText(responsableJiraEveris.toString());

        JComboBox accTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccType.class, accTypeEditor, true);

        JComboBox accSubTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccSubType.class, accSubTypeEditor, false);


        AccTableModel accTableModel = new AccTableModel(new ArrayList<FenixAcc>());
        accTable.setModel(accTableModel);

        accTable.getColumnModel().getColumn(AccTableModel.Columns.ESTADO.ordinal()).setCellEditor(new DefaultCellEditor(accStatusEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.RESPONSABLE.ordinal()).setCellEditor(new DefaultCellEditor(accResponsableEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.TIPO.ordinal()).setCellEditor(new DefaultCellEditor(accTypeEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.SUB_TIPO.ordinal()).setCellEditor(new DefaultCellEditor(accSubTypeEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.FECHA_PREVISTA_PROYECTO.ordinal()).setCellEditor(new CalendarCellEditor());

        TableUtil.configureColumnWidths(accTable, AccTableModel.Columns.class);


        JiraTableModel jiraTableModel = new JiraTableModel(new ArrayList<JiraIssue>());
        jiraTable.setModel(jiraTableModel);
        jiraTable.setDefaultRenderer(Object.class, new JiraTableCellRenderer());
        jiraTable.setFillsViewportHeight(true);
        jiraTable.setAutoCreateRowSorter(true);

        accTable.setDefaultRenderer(Object.class, new AccTableCellRenderer());
        // accTable.setDefaultRenderer(Date.class, new DateCellRenderer());

     /*
        accTable.setFillsViewportHeight(true);
        accTable.setAutoCreateRowSorter(true);
        accTable.setOpaque(true);
        accTable.setRowSelectionAllowed(true);
        accTable.setColumnSelectionAllowed(false);
        accTable.setSelectionBackground(MyColors.ROW_SELECTED);
        accTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
*/
        SwingUtil.agregarMenu(accTable, new JMenuItem[]{menuEditarAcc(accTable), SwingUtil.menuCopiar(accTable), menuIncidenciaInterna(accTable), menuDuda(accTable), menuDuplicar(accTable)});


        tableSettingControl.apply(accTable, tableSettingControl.load(TableType.FENIX_ACC));

    }

    private JMenuItem menuEditarAcc(JenixTable<AccTableModel, FenixAcc> tabla) {
        JMenuItem menuDuplicar = new JMenuItem("Editar");
        menuDuplicar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getModelSelectedRows();

                    if (selected.length > 0) {

                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);

                        AccDialog dialog = new AccDialog(panelParent, acc);
                        dialog.pack();
                        accTable.getModel().fireTableDataChanged();

                        tabla.setRowSelectionInterval(tabla.convertRowIndexToView(selected[0]), tabla.convertRowIndexToView(selected[0]));

                    }
                } catch (Exception ex) {
                    handleException(ex);
                }

            }

        });
        return menuDuplicar;
    }


    private void initTabIncidencias() {

        if (!ComponentStateService.getInstance().isInitialized(EverisComponentType.TAB_INCIDENCIA)) {
            ComponentStateService.getInstance().addInitializedComponent(EverisComponentType.TAB_INCIDENCIA);
            incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));

            Map<IncidenciasMetaDataType, Map> incidenciasMetadata = fenixService.getIncidenciasMetaData(getPeticionSelected(peticionesDisponiblesCmb));

            JComboBox accCorrectoCmb = new JComboBox();
            SwingUtil.loadComboBox(SwingUtil.mapToSelectOptionList((Map<String, String>) incidenciasMetadata.get(IncidenciasMetaDataType.ACC_CORRECTOR)), accCorrectoCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.ACC_CORRECTOR.ordinal()).setCellEditor(new DefaultCellEditor(accCorrectoCmb));

            JComboBox tareaCausanteCmb = new JComboBox();
            SwingUtil.loadComboBox(SwingUtil.mapToSelectOptionList((Map<String, String>) incidenciasMetadata.get(IncidenciasMetaDataType.TAREA_CAUSANTE)), tareaCausanteCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.TAREA_CAUSANTE.ordinal()).setCellEditor(new DefaultCellEditor(tareaCausanteCmb));

            JComboBox estadoCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaEstadoType.class, estadoCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.ESTADO.ordinal()).setCellEditor(new DefaultCellEditor(estadoCmb));

            TableUtil.configureColumnWidths(incidenciasTable, IncidenciaTableModel.Columns.class);


            addIncidenciaBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FenixIncidencia fenixIncidencia = new FenixIncidencia();
                        fenixIncidencia.setEstado(IncidenciaEstadoType.EN_EJECUCION.getDescription());
                        fenixIncidencia.setImpacto(IncidenciaImpactoType.BLOQUEANTE.getDescription());
                        fenixIncidencia.setResueltaPorCliente("NO");
                        fenixIncidencia.setPrioridad(IncidenciaPrioridadType.MEDIA.getDescription());
                        fenixIncidencia.setOtCorrector(getPeticionSelected(peticionesDisponiblesCmb).toString());

                        fenixIncidencia.setIdPeticionOt(getPeticionSelected(peticionesDisponiblesCmb).toString());
                        incidenciasTable.addRow(fenixIncidencia);
                    } catch (Exception ex) {
                        handleException(ex);
                    }
                }
            });


            JComboBox localizadaEnCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaLocalizadaEnType.class, localizadaEnCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.LOCALIZADA_EN.ordinal()).setCellEditor(new DefaultCellEditor(localizadaEnCmb));

            JComboBox tipoIncidenciaCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaTipoType.class, tipoIncidenciaCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.TIPO_INCIDENCIA.ordinal()).setCellEditor(new DefaultCellEditor(tipoIncidenciaCmb));

            JComboBox urgenciaCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaUrgenciaType.class, urgenciaCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.URGENCIA.ordinal()).setCellEditor(new DefaultCellEditor(urgenciaCmb));

            JComboBox impactoCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaImpactoType.class, impactoCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.IMPACTO.ordinal()).setCellEditor(new DefaultCellEditor(impactoCmb));

            JComboBox prioridadCmb = new JComboBox();
            SwingUtil.loadComboBox(IncidenciaImpactoType.class, prioridadCmb, false);
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.PRIORIDAD.ordinal()).setCellEditor(new DefaultCellEditor(prioridadCmb));


            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_INICIO.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_INICIO.ordinal()).setCellRenderer(new DateCellRenderer());

            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_FIN.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_FIN.ordinal()).setCellRenderer(new DateCellRenderer());

            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_PREVISTA_CENTRO.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.FECHA_PREVISTA_CENTRO.ordinal()).setCellRenderer(new DateCellRenderer());
        }
    }

    private void createUIComponents() {
        JiraTableModel jiraTableModel = new JiraTableModel(new ArrayList<>());
        jiraTable = new JenixTable(jiraTableModel);
        jiraScrollPane = new JScrollPane(jiraTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jiraTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        AccTableModel accTableModel = new AccTableModel(new ArrayList<>());
        accTable = new JenixTable(accTableModel);
        accScrollPane = new JScrollPane(accTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        accTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        IncidenciaTableModel incidenciaTableModel = new IncidenciaTableModel(new ArrayList<>());
        incidenciasTable = new JenixTable(incidenciaTableModel);
        incidenciasScrollPane = new JScrollPane(incidenciasTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        incidenciasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        /* AQUI */
        DudaTableModel dudaTableModel = new DudaTableModel(new ArrayList<>());
        dudasTable = new JenixTable(dudaTableModel);
        dudasScrollPane = new JScrollPane(dudasTable);
        dudasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panelParent = new JPanel();
        panelParent.setLayout(new GridBagLayout());
        tabbedPanel = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelParent.add(tabbedPanel, gbc);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPanel.addTab("Sync Jira / Fenix", panel1);
        final JSplitPane splitPane1 = new JSplitPane();
        panel1.add(splitPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setRightComponent(panel2);
        final JToolBar toolBar1 = new JToolBar();
        panel2.add(toolBar1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        saveAccExcelBtn = new JButton();
        saveAccExcelBtn.setText("Guardar excel");
        toolBar1.add(saveAccExcelBtn);
        uploadBtn = new JButton();
        uploadBtn.setText("Upload");
        toolBar1.add(uploadBtn);
        searchACCs = new JButton();
        searchACCs.setText("Actualizar");
        toolBar1.add(searchACCs);
        addAccBtn = new JButton();
        addAccBtn.setText("Agregar ACC");
        toolBar1.add(addAccBtn);
        removeFenixAccBtn = new JButton();
        removeFenixAccBtn.setText("Eliminar ACC");
        toolBar1.add(removeFenixAccBtn);
        checkJiraStatusBtn = new JButton();
        checkJiraStatusBtn.setText("Comprobar estado Jira");
        toolBar1.add(checkJiraStatusBtn);
        generateSpecificationRequirementsBtn = new JButton();
        generateSpecificationRequirementsBtn.setText("Generar Esp.Req.");
        toolBar1.add(generateSpecificationRequirementsBtn);
        configFenixTable = new JButton();
        configFenixTable.setText("Config");
        toolBar1.add(configFenixTable);
        final JLabel label1 = new JLabel();
        label1.setText("FENIX");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        totalEstimatedText = new JTextField();
        panel3.add(totalEstimatedText, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Total estimado");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Total incurrido");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        totalIncurridoText = new JTextField();
        panel3.add(totalIncurridoText, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        estimadoVersusIncurridoText = new JTextField();
        estimadoVersusIncurridoText.setText("");
        panel3.add(estimadoVersusIncurridoText, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Estimado/Incurrido");
        panel3.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        accScrollPane = new JScrollPane();
        panel2.add(accScrollPane, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        accScrollPane.setViewportView(accTable);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPane1.setLeftComponent(panel4);
        jiraScrollPane = new JScrollPane();
        panel4.add(jiraScrollPane, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        jiraScrollPane.setViewportView(jiraTable);
        jiraFiltersCmb = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        jiraFiltersCmb.setModel(defaultComboBoxModel1);
        panel4.add(jiraFiltersCmb, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JToolBar toolBar2 = new JToolBar();
        panel4.add(toolBar2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        refreshJiraBtn = new JButton();
        refreshJiraBtn.setText("Actualizar");
        toolBar2.add(refreshJiraBtn);
        jiraToAccBtn = new JButton();
        jiraToAccBtn.setText(">>");
        toolBar2.add(jiraToAccBtn);
        final JLabel label5 = new JLabel();
        label5.setText("JIRA");
        panel4.add(label5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtJiraTask = new JTextField();
        txtJiraTask.setText("");
        panel4.add(txtJiraTask, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPanel.addTab("Incidencias", panel5);
        incidenciasScrollPane = new JScrollPane();
        panel5.add(incidenciasScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        incidenciasScrollPane.setViewportView(incidenciasTable);
        final JToolBar toolBar3 = new JToolBar();
        panel5.add(toolBar3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));
        addIncidenciaBtn = new JButton();
        addIncidenciaBtn.setText("Add");
        toolBar3.add(addIncidenciaBtn);
        saveIncidenciasBtn = new JButton();
        saveIncidenciasBtn.setText("Guardar excel");
        toolBar3.add(saveIncidenciasBtn);
        uploadIncidenciasBtn = new JButton();
        uploadIncidenciasBtn.setText("Upload");
        toolBar3.add(uploadIncidenciasBtn);
        refreshIncidenciasBtn = new JButton();
        refreshIncidenciasBtn.setText("Actualizar");
        toolBar3.add(refreshIncidenciasBtn);
        removeIncidenciaBtn = new JButton();
        removeIncidenciaBtn.setText("Eliminar");
        toolBar3.add(removeIncidenciaBtn);
        otPanel = new JPanel();
        otPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelParent.add(otPanel, gbc);
        final JLabel label6 = new JLabel();
        label6.setText("OT");
        otPanel.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        peticionesDisponiblesCmb = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        peticionesDisponiblesCmb.setModel(defaultComboBoxModel2);
        otPanel.add(peticionesDisponiblesCmb, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        forceDownloadCheckBox = new JCheckBox();
        forceDownloadCheckBox.setText("Forzar descarga");
        otPanel.add(forceDownloadCheckBox, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        /* AQUI */

        final JPanel panel_dudas = new JPanel();
        panel_dudas.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

        tabbedPanel.addTab("Dudas", panel_dudas);
        incidenciasScrollPane = new JScrollPane();
        panel5.add(incidenciasScrollPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        dudasScrollPane.setViewportView(dudasTable);

        final JToolBar toolBar_dudas = new JToolBar();
        panel_dudas.add(toolBar_dudas, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 20), null, 0, false));

        addDudasBtn = new JButton();
        addDudasBtn.setText("Add");
        toolBar_dudas.add(addDudasBtn);

        saveDudasBtn = new JButton();
        saveDudasBtn.setText("Guardar excel");
        toolBar_dudas.add(saveDudasBtn);

        uploadDudasBtn = new JButton();
        uploadDudasBtn.setText("Upload");
        toolBar_dudas.add(uploadDudasBtn);

        refreshDudasBtn = new JButton();
        refreshDudasBtn.setText("Actualizar");
        toolBar_dudas.add(refreshDudasBtn);

        removeDudasBtn = new JButton();
        removeDudasBtn.setText("Eliminar");
        toolBar_dudas.add(removeDudasBtn);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelParent;
    }


    class JiraTableCellRenderer extends JenixTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JiraTableModel model = (JiraTableModel) table.getModel();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            } else {
                c.setBackground(model.getRowColour(row, accTable.getModel().getList()));
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }


    public JMenuItem menuIncidenciaInterna(final JenixTable tabla) {
        JMenuItem menuCrearIncidenciaInterna = new JMenuItem("Crear incidencia interna");
        menuCrearIncidenciaInterna.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getModelSelectedRows();

                    if (selected.length > 0) {
                        FenixIncidencia incidencia = new FenixIncidencia();
                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);
                        incidencia.setNombreIncidencia(acc.getNombre());
                        incidencia.setDescripcion(acc.getDescripcion());
                        incidencia.setOtCorrector(getPeticionSelected(peticionesDisponiblesCmb).toString());
                        incidencia.setIdPeticionOt(incidencia.getOtCorrector());
                        InternalIncidenceDialog dialog = new InternalIncidenceDialog(panelParent, incidencia);
                        dialog.pack();

                        if (dialog.getPayload() != null) {
                            // ensure tab is initialized
                            initTabIncidencias();
                            incidenciasTable.getList().add(dialog.getPayload());
                            incidenciasTable.getModel().fireTableDataChanged();
                            fenixService.saveIncidencia(incidenciasTable.getList());
                        }

                    }
                } catch (Exception ex) {
                    handleException(ex);
                }

            }

        });
        return menuCrearIncidenciaInterna;
    }



    public JMenuItem menuDuda(final JenixTable tabla) {
        JMenuItem menuCrearDuda = new JMenuItem("Crear duda");
        VariableService vs = new VariableService();

       /* JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("c:\\file.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject =  (JSONObject) obj;*/

        menuCrearDuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                try {
                    int[] selected = tabla.getModelSelectedRows();

                    if (selected.length > 0) {
                        FenixDuda duda = new FenixDuda();
                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);

                        String autor= acc.getResponsable();


                       // duda.setAcc();
                      //  System.out.println("PETICION "+acc.getIdPeticionOtAsociada());

                        if(acc.getIdAcc()!=null){
                            duda.setAcc(String.valueOf(acc.getIdAcc()));
                        }else{
                            duda.setAcc("");
                        }

                        duda.setIdOt(getPeticionSelected(peticionesDisponiblesCmb).toString());
                        System.out.println("MY GOD "+acc.getIdPeticionOtAsociada());

                        duda.setDescripcion(acc.getDescripcion());
                        duda.setEstado(DudaEstadoType.ABIERTA.getDescription());
                        duda.setResponsableConsulta(vs.getUserVariables().getFenixUser());
                        duda.setRespRespuestaProyecto(vs.getUserVariables().getFenixUser());
                        duda.setAutorUltAct(acc.getResponsable());

                        if(acc.getResponsable()==null || StringUtils.isBlank(acc.getResponsable())){
                            duda.setAutorUltAct("");
                        }else{
                            duda.setAutorUltAct(autor.substring(0,6));
                        }

                        duda.setCreador(acc.getResponsable());


                        DudaDialog dudaDialog = new DudaDialog(panelParent,duda);
                        dudaDialog.pack();

                        if (dudaDialog.getPayload() != null) {
                            initTabDudas();
                            System.out.println("PASSEI");
                            dudasTable.getList().add(dudaDialog.getPayload());
                            dudasTable.getModel().fireTableDataChanged();
                            fenixService.saveDuda(dudasTable.getList());
                        }

                    }
                } catch (Exception ex) {
                    handleException(ex);
                }

            }

        });
        return menuCrearDuda;
    }


    public JMenuItem menuDuplicar(final JenixTable tabla) {
        JMenuItem menuDuplicar = new JMenuItem("Duplicar");
        menuDuplicar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getModelSelectedRows();

                    if (selected.length > 0) {

                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);
                        FenixAcc copy = SerializationUtils.clone(acc);
                        copy.getBitacora().clear();
                        copy.setIdAcc(null);
                        copy.setResponsable(null);
                        accTable.addRow(copy);
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }

            }

        });
        return menuDuplicar;
    }

    private void resetForm() {
        jiraTable.getModel().clear();
        accTable.getModel().clear();
        incidenciasTable.getModel().clear();
        totalEstimatedText.setText("");
        totalIncurridoText.setText("");
        estimadoVersusIncurridoText.setText("");

        ComponentStateService.getInstance().clearInitialized(EverisComponentType.values());
    }

    public void initTabDudas() {
        if (!ComponentStateService.getInstance().isInitialized(EverisComponentType.TAB_DUDA)) {

            ComponentStateService.getInstance().addInitializedComponent(EverisComponentType.TAB_DUDA);
            dudasTable.getModel().load(fenixService.searchDudasByOtId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));

      /*
      ComponentStateService.getInstance().addInitializedComponent(EverisComponentType.TAB_INCIDENCIA);
      incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));
*/

            JComboBox estadoDudaCmb = new JComboBox();
            SwingUtil.loadComboBox(DudaEstadoType.class, estadoDudaCmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.ESTADO.ordinal()).setCellEditor(new DefaultCellEditor(estadoDudaCmb));

            JComboBox ambitoDudaCmb = new JComboBox();
            SwingUtil.loadComboBox(DudaAmbitoType.class, ambitoDudaCmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.AMBITO.ordinal()).setCellEditor(new DefaultCellEditor(ambitoDudaCmb));

            JComboBox criticidadDudaCmb = new JComboBox();
            SwingUtil.loadComboBox(Criticidad.class, criticidadDudaCmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.CRITICIDAD.ordinal()).setCellEditor(new DefaultCellEditor(criticidadDudaCmb));


            JComboBox faseLocalizadaDudaCmb = new JComboBox();
            SwingUtil.loadComboBox(DudaFaseLocalizadaType.class, faseLocalizadaDudaCmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.F_LOCALIZADA.ordinal()).setCellEditor(new DefaultCellEditor(faseLocalizadaDudaCmb));

      /*JComboBox ambitoDudaCmb = new JComboBox();
      SwingUtil.loadComboBox(DudaEstadoType.class, ambitoDudaCmb, false);
      dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.A.ordinal()).setCellEditor(new DefaultCellEditor(ambitoDudaCmb));*/

            JComboBox relativaACmb = new JComboBox();
            SwingUtil.loadComboBox(DudaRelativaAType.class, relativaACmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.RELATIVA_A.ordinal()).setCellEditor(new DefaultCellEditor(relativaACmb));

            JComboBox docEntIncCmb = new JComboBox();
            SwingUtil.loadComboBox(DudaDocEntrIncType.class, docEntIncCmb, false);
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.DOC_INCOMP.ordinal()).setCellEditor(new DefaultCellEditor(docEntIncCmb));


            TableUtil.configureColumnWidths(dudasTable, DudaTableModel.Columns.class);

            addDudasBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        FenixDuda fenixDuda = new FenixDuda();
                        VariableService vs = new VariableService();

                        fenixDuda.setEstado(DudaEstadoType.ABIERTA.getDescription());
                        fenixDuda.setIdOt(getPeticionSelected(peticionesDisponiblesCmb).toString());

                        fenixDuda.setResponsableConsulta(vs.getUserVariables().getFenixUser());
                        fenixDuda.setRespRespuestaProyecto(vs.getUserVariables().getFenixUser());

                        fenixDuda.setIdRequerimiento(Long.valueOf("1218336")); //PETICAO


                        dudasTable.addRow(fenixDuda);
                    } catch (Exception ex) {
                        handleException(ex);
                    }
                }
            });

            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_ALTA.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_ALTA.ordinal()).setCellRenderer(new DateCellRenderer());

            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_PREVISTA_RESPUESTA.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_PREVISTA_RESPUESTA.ordinal()).setCellRenderer(new DateCellRenderer());

            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_ULT_ACT.ordinal())
                    .setCellEditor(new CalendarCellEditor());
            dudasTable.getColumnModel().getColumn(DudaTableModel.Columns.FECHA_ULT_ACT.ordinal()).setCellRenderer(new DateCellRenderer());

        }
    }
}
