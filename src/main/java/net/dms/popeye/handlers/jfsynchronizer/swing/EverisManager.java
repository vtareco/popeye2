package net.dms.popeye.handlers.jfsynchronizer.swing;

import net.dms.popeye.handlers.jfsynchronizer.bussiness.FenixService;
import net.dms.popeye.handlers.jfsynchronizer.bussiness.JiraService;
import net.dms.popeye.handlers.jfsynchronizer.control.EverisConfig;
import net.dms.popeye.handlers.jfsynchronizer.control.EverisPropertiesType;
import net.dms.popeye.handlers.jfsynchronizer.control.FenixAccMapper;

import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixAcc;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.FenixIncidencia;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraIssue;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.JiraSearchResponse;
import net.dms.popeye.handlers.jfsynchronizer.fenix.entities.enumerations.*;
import net.dms.popeye.handlers.jfsynchronizer.swing.components.*;
import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDatePanel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by dminanos on 17/04/2017.
 */
public class EverisManager {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable jiraTable;
    private JTable accTable;
    private JButton refreshJiraBtn;
    private JScrollPane jiraScrollPane;
    private JButton searchACCs;
    private JButton jiraToAccBtn;
    private JButton uploadBtn;
    private JButton saveAccExcelBtn;
    private JComboBox peticionesDisponiblesCmb;
    private JComboBox jiraFiltersCmb;
    private JCheckBox forceDownloadCheckBox;
    private JButton addAcc;
    private JButton removeFenixAccBtn;
    private JScrollPane removeAccBtn;
    private JTextField totalEstimatedText;
    private JTextField totalIncurridoText;
    private JButton addButton;
    private JComboBox otCmb;
    private MyJTable<IncidenciaTableModel, FenixIncidencia> incidenciasTable;
    private JButton saveIncidencias;
    private JTextField txtJiraTask;


    JiraService jiraService = new JiraService();
    FenixService fenixService = new FenixService();
    FenixAccMapper accMapper = new FenixAccMapper();
    EverisConfig config = EverisConfig.getInstance();


    private Map<String, String> jiraFilters = config.getJiraFilters();


    public static void main(String[] args) {
        JFrame frame = new JFrame("EverisManager");
        frame.setContentPane(new EverisManager().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public EverisManager() {
        refreshJiraBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshJira();
            }
        });

        searchACCs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    ((AccTableModel) accTable.getModel()).load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));

                    refreshTotales();

                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });
        jiraToAccBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<FenixAcc> accs = new ArrayList<>();

                    List<JiraIssue> issues = ((JiraTableModel) jiraTable.getModel()).getElements(jiraTable.getSelectedRows());

                    for (JiraIssue issue : issues) {
                        FenixAcc acc = accMapper.mapJiraIssue2Acc(issue);
                        acc.setIdPeticionOtAsociada(getPeticionSelected(peticionesDisponiblesCmb));
                        accs.add(acc);
                    }


                    ((AccTableModel) accTable.getModel()).getList().addAll(accs);
                    ((AccTableModel) accTable.getModel()).fireTableDataChanged();
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });
        uploadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fenixService.uploadACCs(getPeticionSelected(peticionesDisponiblesCmb));
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });
        saveAccExcelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fenixService.saveACCs(((AccTableModel) accTable.getModel()).getList());
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });
        peticionesDisponiblesCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (getPeticionSelected(peticionesDisponiblesCmb) != null) {

                        SwingUtilities.invokeLater(new Runnable(){
                            public void run(){
                                ((AccTableModel) accTable.getModel()).clear();
                                ((AccTableModel) accTable.getModel()).load(fenixService.searchAccByPeticionId(getPeticionSelected(peticionesDisponiblesCmb), forceDownloadCheckBox.isSelected()));
                                refreshTotales();
                            }
                        });
                        refreshTotales();
                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });

        init();
        jiraFiltersCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSelectedFilterById()){
                    txtJiraTask.setVisible(true);
                }else {
                    txtJiraTask.setVisible(false);
                    refreshJira();
                }
            }
        });



        addAcc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccTableModel accTableModel = (AccTableModel) accTable.getModel();
                FenixAcc acc = new FenixAcc();

                acc.setCriticidad(AccCriticidad.MEDIA.getDescription());
                acc.setIdPeticionOtAsociada(getPeticionSelected(peticionesDisponiblesCmb));
                acc.setEstado(AccStatus.EN_EJECUCION.getDescription());
                acc.setRechazosEntrega(0);
                accTableModel.getList().add(acc);
                accTableModel.fireTableDataChanged();
            }
        });
        removeFenixAccBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccTableModel accTM = (AccTableModel) accTable.getModel();
                accTM.getList().removeAll(accTM.getElements(accTable.getSelectedRows()));
                accTM.fireTableDataChanged();
            }
        });
        saveIncidencias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenixService.saveIncidencia(incidenciasTable.getList());
            }
        });

    }

    private void refreshTotales() {
        //TODO FIXME

        List<FenixAcc> accs = ((AccTableModel) accTable.getModel()).getList();
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

    private void refreshJira(){
        try{
            String filter;
            if (isSelectedFilterById()){
                filter = String.format(getJiraFilterSelected(), txtJiraTask.getText());
            }else {
                filter = getJiraFilterSelected();
            }
            if (filter != null) {

                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        searchJiras(((JiraTableModel)jiraTable.getModel())::load, filter);
                    }
                });


            }
        }catch(Exception ex){
            handleException(ex);
        }
    }

    private void searchJiras(Consumer<List<JiraIssue>> consumer, String filter){
        JiraSearchResponse response = jiraService.search(filter);
        consumer.accept(response.getIssues());
    }

    private void handleException(Exception ex) {
        System.out.println("error swing: " +ex.getMessage());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(panel1, ex.getMessage());
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
        initTabIncidencias();
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
        for (String responsableJira : responsableJiraEveris.keySet()){
            accResponsableEditor.addItem(responsableJiraEveris.get(responsableJira));

        }
        accResponsableEditor.setToolTipText(responsableJiraEveris.toString());

        JComboBox accTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccType.class, accTypeEditor, false);

        JComboBox accSubTypeEditor = new JComboBox();
        SwingUtil.loadComboBox(AccSubType.class, accSubTypeEditor, false);


        AccTableModel accTableModel = new AccTableModel(new ArrayList<FenixAcc>());
        accTable.setModel(accTableModel);
        accTable.getColumnModel().getColumn(AccTableModel.Columns.ESTADO.ordinal()).setCellEditor(new DefaultCellEditor(accStatusEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.RESPONSABLE.ordinal()).setCellEditor(new DefaultCellEditor(accResponsableEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.TIPO.ordinal()).setCellEditor(new DefaultCellEditor(accTypeEditor));
        accTable.getColumnModel().getColumn(AccTableModel.Columns.SUB_TIPO.ordinal()).setCellEditor(new DefaultCellEditor(accSubTypeEditor));

        JiraTableModel jiraTableModel = new JiraTableModel(new ArrayList<JiraIssue>());
        jiraTable.setModel(jiraTableModel);
        jiraTable.setDefaultRenderer(Object.class, new JiraTableCellRenderer());
        jiraTable.setFillsViewportHeight(true);
        jiraTable.setAutoCreateRowSorter(true);

        accTable.setDefaultRenderer(Object.class, new AccTableCellRenderer());
        accTable.setFillsViewportHeight(true);
        accTable.setAutoCreateRowSorter(true);
        accTable.setOpaque(true);
        accTable.setRowSelectionAllowed(true);
        accTable.setColumnSelectionAllowed(false);
        accTable.setSelectionBackground(MyColors.ROW_SELECTED);
        accTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        SwingUtil.agregarMenu(accTable, new JMenuItem[]{SwingUtil.menuCopiar(accTable)});
    }

    private void initTabIncidencias(){
        List<String> peticionesActuales = fenixService.getPeticionesActuales();
        SwingUtil.loadComboBox(peticionesActuales, otCmb, true);

        otCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getPeticionSelected(otCmb) != null) {

                        SwingUtilities.invokeLater(new Runnable(){
                            public void run(){
                                /*
                                List<FenixAcc> accs = fenixService.searchAccByPeticionId(getPeticionSelected(otCmb), false);

                                JComboBox tareaCausanteCmb = new JComboBox();
                                SwingUtil.loadComboBox(accs.stream().filter(a -> a.canBeTareaCausante()).collect(Collectors.toSet()), tareaCausanteCmb, false);
                                incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.TAREA_CAUSANTE.ordinal()).setCellEditor(new DefaultCellEditor(tareaCausanteCmb));

                                JComboBox<FenixAcc> accCorrectoraCmb = new JComboBox();
                                SwingUtil.loadComboBox(accs.stream().filter(a -> a.canBeAccCorrectora()).collect(Collectors.toSet()), accCorrectoraCmb, false);
                                incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.ACC_CORRECTOR.ordinal()).setCellEditor(new DefaultCellEditor(accCorrectoraCmb));
*/
                                incidenciasTable.getModel().load(fenixService.searchIncidenciasByOtId(getPeticionSelected(otCmb), false));

                                Map<IncidenciasMetaDataType, Map> incidenciasMetadata =  fenixService.getIncidenciasMetaData(getPeticionSelected(otCmb));

                                JComboBox accCorrectoCmb = new JComboBox();
                                SwingUtil.loadComboBox(SwingUtil.mapToSelectOptionList((Map<String, String>)incidenciasMetadata.get(IncidenciasMetaDataType.ACC_CORRECTOR)), accCorrectoCmb, false);
                                incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.ACC_CORRECTOR.ordinal()).setCellEditor(new DefaultCellEditor(accCorrectoCmb));

                                JComboBox tareaCausanteCmb = new JComboBox();
                                SwingUtil.loadComboBox(SwingUtil.mapToSelectOptionList((Map<String, String>)incidenciasMetadata.get(IncidenciasMetaDataType.TAREA_CAUSANTE)), tareaCausanteCmb, false);
                                incidenciasTable.getColumnModel().getColumn(IncidenciaTableModel.Columns.TAREA_CAUSANTE.ordinal()).setCellEditor(new DefaultCellEditor(tareaCausanteCmb));



                            }
                        });

                    }
                } catch (Exception ex) {
                    handleException(ex);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FenixIncidencia fenixIncidencia = new FenixIncidencia();
                    fenixIncidencia.setEstado(IncidenciaEstadoType.EN_EJECUCION.getDescription());
                    fenixIncidencia.setImpacto(IncidenciaImpactoType.BLOQUEANTE.getDescription());
                    fenixIncidencia.setResueltaPorCliente("NO");
                    fenixIncidencia.setPrioridad(IncidenciaPrioridadType.MEDIA.getDescription());
                    fenixIncidencia.setOtCorrector(getPeticionSelected(otCmb).toString());

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

    private void createUIComponents() {
        IncidenciaTableModel incidenciaTableModel = new IncidenciaTableModel(new ArrayList<>());
        incidenciasTable = new MyJTable(incidenciaTableModel);
        jiraScrollPane = new JScrollPane(incidenciasTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        incidenciasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }


    class JiraTableCellRenderer extends MyTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JiraTableModel model = (JiraTableModel) table.getModel();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected){
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }else {
                c.setBackground(model.getRowColour(row, ((AccTableModel) accTable.getModel()).getList()));
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }

    class AccTableCellRenderer extends MyTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            AccTableModel model = (AccTableModel) table.getModel();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
               super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }else{
                c.setBackground(model.getRowColour(row));
                c.setForeground(Color.BLACK);
            }
            return c;
        }
    }

}
