package net.dms.fsync.swing.Panes;

import net.dms.fsync.settings.Internationalization;
import net.dms.fsync.swing.components.Toast;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class ServerChangePane extends JPanel {


    private JTextField fenixServerJtf, jiraServerJtf, proxyHostJtf, proxyPortJtf;
    private JFileChooser pathJfc;
    private JButton acceptChangesJbtn, declineChangesJbtn, openFileChooser;
    private JLabel proxyJl, fenixServerJl, jiraServerJl, workingLanguageJl;
    private String currentPath, currentLanguage;
    private JComboBox languageSelectorJcmb;

    public ServerChangePane(String path) {
        setLayout(null);
        this.setSize(600, 400);
        this.setName(Internationalization.getStringTranslated("applicationPropertiess"));
        loadPane(path);
    }


    public void loadPane(String path) {
        LocalVariables lv = new LocalVariables();
        pathJfc = new JFileChooser();
        fenixServerJtf = new JTextField();
        jiraServerJtf = new JTextField();
        proxyHostJtf = new JTextField();
        proxyPortJtf = new JTextField();
        acceptChangesJbtn = new JButton();
        languageSelectorJcmb = new JComboBox();
        declineChangesJbtn = new JButton();
        openFileChooser = new JButton();
        workingLanguageJl = new JLabel();
        fenixServerJl = new JLabel();
        jiraServerJl = new JLabel();
        proxyJl = new JLabel();
        System.out.println(Internationalization.getStringTranslated("responsible"));
        ApplicationProperties ap = lv.getApFromJson(path);
        labelConfigutationLoader();
        buttonConfigurationLoad(ap.getWorkingDirectory());
        textFieldConfigurationLoader(ap);
        pathConfigurationLoader(ap);
        loadLanguagesCmb(ap.getWorkingLanguage());

        currentPath = ap.getWorkingDirectory();
        currentLanguage = ap.getWorkingLanguage();
        acceptChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!currentLanguage.equals(languageSelectorJcmb.getSelectedItem().toString())) {
                    currentLanguage = languageSelectorJcmb.getSelectedItem().toString();
                    currentPathChange(ap, path);
                    Toast.display(Internationalization.getStringTranslated("toastLanguageChanged"), Toast.ToastType.INFO);
                } else {
                    currentPathChange(ap, path);
                    Toast.display(Internationalization.getStringTranslated("toastSuccess"), Toast.ToastType.INFO);
                }


                //JOptionPane.showMessageDialog(null,"Success","Information",1,null);
            }
        });

        languageSelectorJcmb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ap.setWorkingLanguage(languageSelectorJcmb.getSelectedItem().toString());
            }
        });

        openFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathChooserFunction(ap);
            }
        });

        openFileChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                openFileChooser.setText(Internationalization.getStringTranslated("choseNewDirectory"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                openFileChooser.setText(Internationalization.getStringTranslated("currentPath") + ap.getWorkingDirectory());
            }
        });

        this.setVisible(true);
    }


    private void currentPathChange(ApplicationProperties ap, String path) {

        if (currentPath.equals(ap.getWorkingDirectory())) {
            changeServerFunction(ap, path);


        } else {
            changeServerFunction(ap, path);
            changeWorkingPath(ap.getWorkingDirectory());
            currentPath = ap.getWorkingDirectory();

        }

    }

    private void changeWorkingPath(String newDirPath) {
        File oldDir = new File(currentPath + "/JenixSettings");
        File newDir = new File(newDirPath + "/JenixSettings");
        File oldTableView = new File(currentPath + "/FENIX_ACC.json");
        File newTableView = new File(newDirPath + "/FENIX_ACC.json");
        try {
            if (!newDir.exists()) {
                FileUtils.copyDirectory(oldDir, newDir);

            }

            if (oldTableView.exists() && !newTableView.exists()) {
                FileUtils.copyFile(oldTableView, newTableView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void changeServerFunction(ApplicationProperties ap, String path) {
        LocalVariables lv = new LocalVariables();

        ap.setFenixUrl(fenixServerJtf.getText());
        ap.setJiraUrl(jiraServerJtf.getText());
        ap.setProxyHost(proxyHostJtf.getText());
        ap.setProxyHost(proxyHostJtf.getText());
        ap.setProxyPort(proxyPortJtf.getText());
        lv.serverConfiguration(ap, path);
        // lv.alterConf(js);

    }

    private void textFieldConfigurationLoader(ApplicationProperties ap) {
        fenixServerJtf.setText(ap.getFenixUrl());
        fenixServerJtf.setBounds(160, 30, 300, 25);
        fenixServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(fenixServerJtf);


        jiraServerJtf.setText(ap.getJiraUrl());
        jiraServerJtf.setBounds(160, 90, 300, 25);
        jiraServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(jiraServerJtf);


        proxyHostJtf.setText(ap.getProxyHost());
        proxyHostJtf.setBounds(160, 150, 300, 25);
        proxyHostJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyHostJtf);

        proxyPortJtf.setText(ap.getProxyPort());
        proxyPortJtf.setBounds(480, 150, 50, 25);
        proxyPortJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyPortJtf);


    }

    private void labelConfigutationLoader() {

        fenixServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        fenixServerJl.setText("Fenix URL: ");
        fenixServerJl.setHorizontalAlignment(JLabel.LEFT);
        fenixServerJl.setBounds(20, 30, 150, 25);
        this.add(fenixServerJl);

        jiraServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        jiraServerJl.setText("Jira URL: ");
        jiraServerJl.setHorizontalAlignment(JLabel.LEFT);
        jiraServerJl.setBounds(20, 90, 150, 25);
        this.add(jiraServerJl);

        proxyJl.setFont((new Font("Arial", Font.PLAIN, 16)));
        proxyJl.setText("Proxy URL & Port : ");
        proxyJl.setHorizontalAlignment(JLabel.LEFT);
        proxyJl.setBounds(20, 150, 150, 25);
        this.add(proxyJl);

        workingLanguageJl.setFont(new Font("Arial", Font.PLAIN, 16));
        workingLanguageJl.setBounds(20, 190, 150, 25);
        workingLanguageJl.setText(Internationalization.getStringTranslated("languageLower"));
        workingLanguageJl.setHorizontalAlignment(JLabel.LEFT);

        this.add(workingLanguageJl);

    }


    private void pathConfigurationLoader(ApplicationProperties ap) {

        pathJfc.setCurrentDirectory(new File(ap.getWorkingDirectory()));
        pathJfc.setDialogTitle("Working path");
        pathJfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pathJfc.setAcceptAllFileFilterUsed(false);

    }


    private void buttonConfigurationLoad(String path) {
        openFileChooser.setText(Internationalization.getStringTranslated("currentPath") + " : " + path);
        openFileChooser.setBounds(200, 230, 200, 30);
        this.add(openFileChooser);


        acceptChangesJbtn.setText(Internationalization.getStringTranslated("saveLower"));
        acceptChangesJbtn.setBounds(30, 300, 110, 30);
        this.add(acceptChangesJbtn);


        declineChangesJbtn.setText(Internationalization.getStringTranslated("cancelLower"));
        declineChangesJbtn.setBounds(455, 300, 110, 30);
        // this.add(declineChangesJbtn);
    }

    private void loadLanguagesCmb(String workingLanguage) {
        LocalVariables lv = new LocalVariables();

        languageSelectorJcmb.setBounds(160, 190, 150, 25);
        languageSelectorJcmb.setModel(new DefaultComboBoxModel(lv.AvaliableLanguages().toArray()));
        languageSelectorJcmb.setSelectedItem(workingLanguage);
        this.add(languageSelectorJcmb);


    }

    private void pathChooserFunction(ApplicationProperties ap) {

        if (pathJfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            ap.setWorkingDirectory(pathJfc.getSelectedFile().toString());

            buttonConfigurationLoad(ap.getWorkingDirectory());
        } else {
            System.out.println("No Selection ");
        }


    }


}

