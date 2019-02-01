package net.dms.fsync.synchronizer.LocalVariables.control;

import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.EverisVariables;

import net.dms.fsync.synchronizer.LocalVariables.entities.JenixSettings;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;


import java.io.*;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LocalVariables {

    public Map<String, String> getVariables() {
        Map<String, String> variables = LocalVariables();
        return variables;
    }

    /*
        public Map<String, String> getVariables2(){
            Map<String, String> variables = LocalVariables2();
            return variables;
        }
    */
    private Map<String, String> LocalVariables() {
        Map<String, String> localvariables = new HashMap<String, String>();
        localvariables.put(EverisVariables.FENIX_TIMESTAMP.getVariableName(), new Long(System.currentTimeMillis()).toString());
        localvariables.put(EverisVariables.FENIX_URL_BASE.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_URL_BASE));
        localvariables.put(EverisVariables.FENIX_USER.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_USER));
        localvariables.put(EverisVariables.FENIX_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_PASSWORD));
        localvariables.put(EverisVariables.FENIX_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_PASSWORD));
        //Added by vdivizin on 25/01/2019
        localvariables.put(EverisVariables.JIRA_USER.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_USER));
        localvariables.put(EverisVariables.JIRA_PASSWORD.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_PASSWORD));
        localvariables.put(EverisVariables.JIRA_URL_BASE.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.JIRA_URL_BASE));
        localvariables.put(EverisVariables.WORKING_DIRECTORY.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.WORKING_DIRECTORY));
        localvariables.put(EverisVariables.PROXY_HOST.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.PROXY_HOST));
        localvariables.put(EverisVariables.PROXY_PORT.getVariableName(), EverisConfig.getInstance().getProperty(EverisPropertiesType.PROXY_PORT));

        return localvariables;
    }

    public void alterConf(JenixSettings js) {

        URL url = this.getClass().getClassLoader().getResource("bmw/rsp/everis_overriden.conf");
        File everisOverConfPath = new File(url.getPath());
        //System.out.println(everisOverConfPath);

        File everisReadOverFile = new File(everisOverConfPath.toString());
        File everisWriteOverFIle = new File(everisOverConfPath.toString());

        ArrayList<String> newLines = new ArrayList<>();


        try (
                BufferedReader br = new BufferedReader(new FileReader(everisReadOverFile)
                )
        ) {
            String line;

            while ((line = br.readLine()) != null) {

                //System.out.println(line);
                if (line.startsWith("everis.conf.fenix.user=") && !js.getUc().getFenixUser().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getUc().getFenixUser());
                } else if (line.startsWith("everis.conf.fenix.password=") && !js.getUc().getFenixPassword().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getUc().getFenixPassword());
                } else if (line.startsWith("everis.conf.jira.user=") && !js.getUc().getJirauser().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getUc().getJirauser());
                } else if (line.startsWith("everis.conf.jira.password=") && !js.getUc().getJiraPassword().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getUc().getJiraPassword());
                } else if (line.startsWith("everis.conf.fenix.urlbase=") && !js.getAp().getFenixUrl().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getAp().getFenixUrl());
                } else if (line.startsWith("everis.conf.jira.urlbase=") && !js.getAp().getJiraUrl().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getAp().getJiraUrl());
                } else if (line.startsWith("everis.conf.proxyConfiguration.host=") && !js.getAp().getProxyHost().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getAp().getProxyHost());
                } else if (line.startsWith("everis.conf.proxyConfiguration.post=") && !js.getAp().getProxyPort().isEmpty()) {
                    newLines.add(line.substring(0, line.indexOf("=") + 1) + js.getAp().getProxyPort());
                } else if (line.startsWith("everis.conf.project.path=") && !js.getAp().getWorkingDirectory().isEmpty()) {
                    char[] currectPath = js.getAp().getWorkingDirectory().toCharArray();

                    for (int i = 0; i < currectPath.length; i++) {
                        if (currectPath[i] == '\\') {
                            currectPath[i] = '/';
                        }

                    }

                    newLines.add(line.substring(0, line.indexOf("=") + 1) + String.valueOf(currectPath));
                } else {
                    newLines.add(line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(everisWriteOverFIle));
            for (String s : newLines) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

   /* private Map<String, String> LocalVariables2(){
        Map<String, String> localvariables2= new HashMap<String, String>();
        localvariables2.put();
        return localvariables2;
    }
    */

}
