package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.bussiness.FenixService;
import net.dms.popeye.handlers.jfsynchronizer.bussiness.JiraService;
import net.dms.popeye.handlers.jfsynchronizer.control.EverisConfig;
import net.dms.popeye.handlers.jfsynchronizer.control.EverisPropertiesType;
import net.dms.popeye.handlers.jfsynchronizer.control.FenixAccMapper;

import net.dms.popeye.handlers.jfsynchronizer.control.TableSettingControl;
import net.dms.popeye.handlers.jfsynchronizer.entities.TableType;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraIssue;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraSearchResponse;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.*;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.*;
import net.dms.popeye.handlers.jfsynchronizer.swing.dialogs.AccDialog;
import net.dms.popeye.handlers.jfsynchronizer.swing.dialogs.ColumnsSettingDialog;
import net.dms.popeye.handlers.jfsynchronizer.swing.dialogs.InternalIncidenceDialog;
import net.dms.popeye.handlers.jfsynchronizer.swing.models.AccTableModel;
import net.dms.popeye.handlers.jfsynchronizer.swing.models.IncidenciaTableModel;
import net.dms.popeye.handlers.jfsynchronizer.swing.models.JiraTableModel;
import net.dms.popeye.settings.business.SettingsService;
import net.dms.popeye.settings.entities.Actor;
import net.dms.popeye.settings.entities.ColumnSetting;
import net.dms.popeye.settings.entities.Settings;
import net.dms.popeye.settings.entities.TableSetting;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
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
    private JPopupMenu refreshMenu;


    JiraService jiraService = new JiraService();
    FenixService fenixService = new FenixService();
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
        SwingUtil.registerListener(refreshJiraBtn, this::refreshJira, this::handleException);
        SwingUtil.registerListener(searchACCs, this::loadAccs, this::handleException);
        SwingUtil.registerListener(jiraToAccBtn, this::jiraToAcc, this::handleException);
        SwingUtil.registerListener(uploadBtn, this::uploadAccs, this::handleException);
        SwingUtil.registerListener(saveAccExcelBtn, this::saveAccs, this::handleException);
        SwingUtil.registerListener(peticionesDisponiblesCmb, this::loadAccs, this::handleException);
        SwingUtil.registerListener(uploadIncidenciasBtn, this::uploadIncidencias, this::handleException);
        SwingUtil.registerListener(refreshIncidenciasBtn, this::refreshIncidencias, this::handleException);


        init();

        SwingUtil.registerListener(jiraFiltersCmb, this::filterSelectorHandler, this::handleException);
        SwingUtil.registerListener(addAccBtn, this::addAcc, this::handleException);
        SwingUtil.registerListener(removeFenixAccBtn, this::removeAcc, this::handleException);

        SwingUtil.registerListener(saveIncidenciasBtn, this::saveIncidencias, this::handleException);
        SwingUtil.registerListener(removeIncidenciaBtn, this::removeIncidencia, this::handleException);
        SwingUtil.registerListener(checkJiraStatusBtn, this::checkJiraStatus, this::handleException);
        SwingUtil.registerListener(configFenixTable, this::configFenixTable, this::handleException);


        tabbedPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    initTabIncidencias();
                }catch (Exception ex){
                    handleException(ex);
                }
            }
        });
    }

    private void configFenixTable() {
        TableSettingControl settingControl = new TableSettingControl();
        TableSetting tableSetting = settingControl.load(TableType.FENIX_ACC);

        ColumnsSettingDialog dialog = new ColumnsSettingDialog(panelParent, tableSetting);
        dialog.pack();
        if (dialog.getPayload() != null) {
            settingControl.save(TableType.FENIX_ACC, dialog.getPayload());
        }
    }

    private void refreshIncidencias() {
        incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), this.forceDownloadCheckBox.isSelected()));

    }

    private void checkJiraStatus() {
        List<FenixAcc> accs= accTable.getModel().getList();
        Set<String> jiraCodes = accs.stream().map(FenixAcc::getCodigoPeticionCliente).collect(Collectors.toSet());
        jiraCodes = jiraCodes.stream().filter(c -> isValidJiraCode(c)).collect(Collectors.toSet());

        // TODO FIXME, filter should be parametrized
        //String jql = String.format(settings.getJiraSettings().getFilterByIds(), String.join(",", jiraCodes));
        String jql = String.format("issue in (%s)", String.join(",", jiraCodes));

        JiraSearchResponse jiraSearchResponse = jiraService.search(jql);
        List<JiraIssue>issues = jiraSearchResponse.getIssues();
        for(FenixAcc acc : accs) {
            JiraIssue issue = issues.stream().filter(i -> i.getKey().equals(acc.getCodigoPeticionCliente())).findFirst().orElse(null);
            if (issue != null){
                acc.setJiraStatus(issue.getFields().getStatus().getName());
            }
        }
        accTable.updateUI();
    }

    private void saveIncidencias() {
        fenixService.saveIncidencia(incidenciasTable.getList());
    }

    private void removeAcc() {
        AccTableModel accTM = accTable.getModel();
        accTM.getList().removeAll(accTM.getElements(accTable.getSelectedRows()));
        accTM.fireTableDataChanged();
    }

    private void removeIncidencia(){
        IncidenciaTableModel tableModel = incidenciasTable.getModel();
        tableModel.getList().removeAll(tableModel.getElements(incidenciasTable.getSelectedRows()));
        tableModel.fireTableDataChanged();
    }

    private void addAcc() {
        AccTableModel accTableModel =  accTable.getModel();
        FenixAcc acc = new FenixAcc();

        acc.setCriticidad(AccCriticidad.MEDIA.getDescription());
        acc.setIdPeticionOtAsociada(getPeticionSelected(peticionesDisponiblesCmb));
        acc.setEstado(AccStatus.EN_EJECUCION.getDescription());
        acc.setRechazosEntrega(0);

        AccDialog dialog = new AccDialog(panelParent, acc);
        dialog.pack();

        FenixAcc editedAcc = dialog.getPayload();
        if (editedAcc != null) {
            accTableModel.getList().add(editedAcc);
            accTableModel.fireTableDataChanged();
            accTable.setRowSelectionInterval(accTableModel.getRowCount(), accTableModel.getRowCount());
        }






    }

    private void filterSelectorHandler() {
        if (isSelectedFilterById()){
            txtJiraTask.setVisible(true);
        }else {
            txtJiraTask.setVisible(false);
            refreshJira();
        }
    }

    private void uploadIncidencias() {
        fenixService.uploadIncidencias(getPeticionSelected(peticionesDisponiblesCmb));
        incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(peticionesDisponiblesCmb), true));
    }

    private void saveAccs() {
        fenixService.saveACCs( accTable.getModel().getList());
    }

    private void uploadAccs() {
        fenixService.uploadACCs(getPeticionSelected(peticionesDisponiblesCmb));
        accTable.getModel().load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), true));
        refreshTotales();
    }

    private void jiraToAcc() {
        List<FenixAcc> accs = new ArrayList<>();

        List<JiraIssue> issues = ((JiraTableModel) jiraTable.getModel()).getElements(jiraTable.getSelectedRows());

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

    private static boolean isValidJiraCode(String jiraCode){
        if (jiraCode == null) {
            return false;
        }else {
            Pattern pattern = Pattern.compile(".+-.+", Pattern.CASE_INSENSITIVE);
            return   pattern.matcher(jiraCode).matches();
        }
    }

    private void refreshTotales() {
        //TODO FIXME

        List<FenixAcc> accs = accTable.getModel().getList();
        double totalEstimado = 0;
        double totalIncurrido = 0;

        for(      FenixAcc acc :accs )     {

            if (acc.getIncurrido() != null) {
                totalIncurrido = totalIncurrido + acc.getIncurrido();
            }
            if (acc.getEsfuerzo() != null && acc.getIncurrido() != null && acc.getIncurrido().doubleValue() != 0
                    || !acc.getEstado().equals(AccStatus.DESESTIMADA.getDescription())) {
                totalEstimado = totalEstimado + acc.getTotalEsfuerzo();
            }

        }
                       totalEstimatedText.setText(Double.toString(totalEstimado));
                       totalIncurridoText.setText(Double.toString(totalIncurrido));


    }

    private boolean isSelectedFilterById() {
        return EverisPropertiesType.JIRA_FILTRO_BY_ID.getProperty().equals(jiraFiltersCmb.getSelectedItem());
    }

    private void loadAccs(){
        resetForm();
        if (getPeticionSelected(peticionesDisponiblesCmb) != null) {
            accTable.getModel().load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));
            refreshTotales();
        }
    }

    private void refreshJira(){
            String filter;
            if (isSelectedFilterById()){
                filter = String.format(getJiraFilterSelected(), txtJiraTask.getText());
            }else {
                filter = getJiraFilterSelected();
            }
            if (filter != null) {
                searchJiras(((JiraTableModel)jiraTable.getModel())::load, filter);

            }
    }

    private void searchJiras(Consumer<List<JiraIssue>> consumer, String filter){
        JiraSearchResponse response = jiraService.search(filter);
        consumer.accept(response.getIssues());
    }

    private void handleException(Exception ex) {
        System.out.println("error swing: " +ex.getMessage());
        try {
            ex.printStackTrace();
           JLabel label = new JLabel("<html><p> " + ex.getMessage() + "</p></html>");
           label.setMaximumSize(new Dimension(480, 300));
           label.setPreferredSize(new Dimension(480, 300));
            JOptionPane.showMessageDialog(panelParent, label);
        }catch (Exception ex2){
            ex2.printStackTrace();
        }
    }

    private Long getPeticionSelected(JComboBox combo){
        String selected = (String)combo.getSelectedItem();
        if (StringUtils.isEmpty(selected)){
            return null;
        }else{
            return new Long(selected.split("-")[0]);
        }
    }

    private String getJiraFilterSelected(){
        String selected = (String)jiraFiltersCmb.getSelectedItem();
        if (StringUtils.isEmpty(selected)){
            return null;
        }else{
            return jiraFilters.get(selected);
        }
    }


    private void init() {
        initTabSyncJiraFenix();

    }

    private void initTabSyncJiraFenix() {

        List<String> peticionesActuales = fenixService.getPeticionesActuales();
        SwingUtil.loadComboBox(peticionesActuales, peticionesDisponiblesCmb, true);

        SwingUtil.loadComboBox(jiraFilters.keySet(), jiraFiltersCmb, true);
       // txtJiraTask.setVisible(false);


        JComboBox accStatusEditor = new JComboBox();
        SwingUtil.loadComboBox(AccStatus.class, accStatusEditor, false);


        JComboBox accResponsableEditor = new JComboBox();
        Map<String, String> responsableJiraEveris = config.getMapResponsableJiraEveris();


        for (Actor responsableJira : settings.getActores()){
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
        accTable.setFillsViewportHeight(true);
        accTable.setAutoCreateRowSorter(true);
        accTable.setOpaque(true);
        accTable.setRowSelectionAllowed(true);
        accTable.setColumnSelectionAllowed(false);
        accTable.setSelectionBackground(MyColors.ROW_SELECTED);
        accTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        SwingUtil.agregarMenu(accTable, new JMenuItem[]{menuEditarAcc(accTable), SwingUtil.menuCopiar(accTable),  menuIncidenciaInterna(accTable), menuDuplicar(accTable)});



    }

    private JMenuItem menuEditarAcc(JenixTable<AccTableModel, FenixAcc> tabla) {
        JMenuItem menuDuplicar = new JMenuItem("Editar");
        menuDuplicar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getSelectedRows();

                    if (selected.length > 0) {

                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);

                        AccDialog dialog = new AccDialog(panelParent, acc);
                        dialog.pack();
                        accTable.getModel().fireTableDataChanged();
                        tabla.setRowSelectionInterval(selected[0], selected[0]);

                    }
                } catch (Exception ex) {
                    handleException(ex);
                }

            }

        });
        return menuDuplicar;
    }


    private void initTabIncidencias(){

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

    }


    class JiraTableCellRenderer extends JenixTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JiraTableModel model = (JiraTableModel) table.getModel();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected){
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }else {
                c.setBackground(model.getRowColour(row, accTable.getModel().getList()));
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }


    public JMenuItem menuIncidenciaInterna(final JTable tabla) {
        JMenuItem menuCrearIncidenciaInterna = new JMenuItem("Crear incidencia interna");
        menuCrearIncidenciaInterna.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getSelectedRows();

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


    public JMenuItem menuDuplicar(final JTable tabla) {
        JMenuItem menuDuplicar = new JMenuItem("Duplicar");
        menuDuplicar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    int[] selected = tabla.getSelectedRows();

                    if (selected.length > 0) {

                        FenixAcc acc = ((AccTableModel) tabla.getModel()).getPayload(selected[0]);
                        FenixAcc copy = SerializationUtils.clone(acc);
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
    private void resetForm(){
        jiraTable.getModel().clear();
        accTable.getModel().clear();
        incidenciasTable.getModel().clear();
        totalEstimatedText.setText("");
        totalIncurridoText.setText("");

        ComponentStateService.getInstance().clearInitialized(EverisComponentType.values());
    }

}
