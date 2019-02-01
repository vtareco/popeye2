package net.dms.fsync.swing.Panes;

import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.JenixSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ServerChangePane extends JPanel {


    private JTextField fenixServerJtf, jiraServerJtf, proxyHostJtf, proxyPortJtf;
    private JFileChooser pathJfc;
    private JButton acceptChangesJbtn, declineChangesJbtn, openFileChooser;
    private JLabel proxyJl,fenixServerJl,jiraServerJl,currentPathJl;

    public ServerChangePane(JenixSettings js) {
        setLayout(null);
        this.setSize(600, 400);
      this.setName("ApplicationProperties");
        loadPane(js);

    }

    public void loadPane(JenixSettings js) {
        pathJfc = new JFileChooser();
        fenixServerJtf = new JTextField();
        jiraServerJtf = new JTextField();
        proxyHostJtf = new JTextField();
        proxyPortJtf = new JTextField();
        acceptChangesJbtn = new JButton();
        declineChangesJbtn = new JButton();
        openFileChooser = new JButton();
        currentPathJl = new JLabel();
        fenixServerJl = new JLabel();
        jiraServerJl = new JLabel();
        proxyJl = new JLabel();
        labelConfigutationLoader();
        buttonConfigurationLoad(js.getAp().getWorkingDirectory());
        textFieldConfigurationLoader(js);
        pathConfigurationLoader(js);





        acceptChangesJbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeServerFunction(js);
            }
        });



        openFileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pathChooserFunction(js);
            }
        });

        openFileChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                openFileChooser.setText("Choose new directory");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                openFileChooser.setText("Current path: " + js.getAp().getWorkingDirectory());
            }
        });

        this.setVisible(true);
    }


   private void  changeServerFunction(JenixSettings js){
       LocalVariables lv = new LocalVariables();

       js.getAp().setFenirUrl(fenixServerJtf.getText());
       js.getAp().setJiraUrl(jiraServerJtf.getText());
       js.getAp().setProxyHost(proxyHostJtf.getText());
       js.getAp().setProxyHost(proxyHostJtf.getText());

     lv.alterConf(js);

    }

    private void textFieldConfigurationLoader(JenixSettings js) {
        fenixServerJtf.setText(js.getAp().getFenixUrl());
        fenixServerJtf.setBounds(160,30,300,25);
        fenixServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(fenixServerJtf);


        jiraServerJtf.setText(js.getAp().getJiraUrl());
        jiraServerJtf.setBounds(160,90,300,25);
        jiraServerJtf.setFont(new Font("Arial", Font.PLAIN, 16));
       this.add(jiraServerJtf);


        proxyHostJtf.setText(js.getAp().getProxyHost());
        proxyHostJtf.setBounds(160,150,300,25);
        proxyHostJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyHostJtf);

        proxyPortJtf.setText(js.getAp().getProxyPort());
        proxyPortJtf.setBounds(480,150,50,25);
        proxyPortJtf.setFont(new Font("Arial", Font.PLAIN, 16));
        this.add(proxyPortJtf);


    }

    private void labelConfigutationLoader() {

        fenixServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        fenixServerJl.setText("Fenix URL: ");
        fenixServerJl.setHorizontalAlignment(JLabel.LEFT);
        fenixServerJl.setBounds(20,30,150,25);
        this.add(fenixServerJl);

        jiraServerJl.setFont(new Font("Arial", Font.PLAIN, 16));
        jiraServerJl.setText("Jira URL: ");
        jiraServerJl.setHorizontalAlignment(JLabel.LEFT);
        jiraServerJl.setBounds(20,90,150,25);
        this.add(jiraServerJl);

        proxyJl.setFont((new Font("Arial", Font.PLAIN, 16)));
        proxyJl.setText("Proxy URL & Port : ");
        proxyJl.setHorizontalAlignment(JLabel.LEFT);
        proxyJl.setBounds(20,150,150,25);
        this.add(proxyJl);

    }


    private void pathConfigurationLoader(JenixSettings js) {
        pathJfc.setCurrentDirectory(new File(js.getAp().getWorkingDirectory()));
        pathJfc.setDialogTitle("Working path");
        pathJfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        pathJfc.setAcceptAllFileFilterUsed(false);

    }


    private void buttonConfigurationLoad(String path) {
        openFileChooser.setText("Current path: " + path );
        openFileChooser.setBounds(200, 230, 200, 30);
        this.add(openFileChooser);


        acceptChangesJbtn.setText("Save");
        acceptChangesJbtn.setBounds(30, 300, 110, 30);
        this.add(acceptChangesJbtn);


        declineChangesJbtn.setText("Cancel");
        declineChangesJbtn.setBounds(455, 300, 110, 30);
       // this.add(declineChangesJbtn);
    }


    private void pathChooserFunction(JenixSettings js) {

        if (pathJfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            js.getAp().setWorkingDirectory(pathJfc.getSelectedFile().toString());

            buttonConfigurationLoad(js.getAp().getWorkingDirectory());
        } else {
            System.out.println("No Selection ");
        }


    }


}

