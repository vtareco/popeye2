package net.dms.fsync.synchronizer.LocalVariables.business;


import jdk.nashorn.internal.parser.JSONParser;
import net.dms.fsync.httphandlers.common.Utils;
import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.EverisVariables;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;


public class VariableService {

    LocalVariables localVariables = new LocalVariables();

    public UserChange getUserVariables(){
        UserChange uc = new UserChange();
        uc.setFenixUser(localVariables.getVariables().get(EverisVariables.FENIX_USER.getVariableName()));
        uc.setFenixPassword(localVariables.getVariables().get(EverisVariables.FENIX_PASSWORD.getVariableName()));

        uc.setJirauser(localVariables.getVariables().get(EverisVariables.JIRA_USER.getVariableName()));
        uc.setJiraPassword(localVariables.getVariables().get(EverisVariables.JIRA_PASSWORD.getVariableName()));

        return uc;
    }





    public void writeUserConfJsonFile(UserChange uc){

    }

    public void getUserJsonsVariables(){

    }




    public  void readJsonToConfFile(File jsonUserCreate, File jsonApplicationProperties){






         /*
        try {

            Object obj = new JSONParser().parse(new FileReader(jsonUserCreate));
            JSONObject jsonUserChange = (JSONObject) obj;

            ApplicationProperties ap = new ApplicationProperties();
            String file = EverisConfig.getInstance().getProperty(EverisPropertiesType.FENIX_USER);
            file = Utils.replaceVariable("fenixUser",file,jsonUserChange.get("fenixUser").toString());

            System.out.println(jsonUserChange.get("fenixUser").toString());
            System.out.println(file);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    }


    public ApplicationProperties getApplicationVariables(){
        ApplicationProperties ap = new ApplicationProperties();
        ap.setFenirUrl(localVariables.getVariables().get(EverisVariables.FENIX_URL_BASE.getVariableName()));
        ap.setJiraUrl(localVariables.getVariables().get(EverisVariables.JIRA_URL_BASE.getVariableName()));
        ap.setProxyHost(localVariables.getVariables().get(EverisVariables.PROXY_HOST.getVariableName()));
        ap.setProxyPort(localVariables.getVariables().get(EverisVariables.PROXY_PORT.getVariableName()));
        ap.setWorkingDirectory(localVariables.getVariables().get(EverisVariables.WORKING_DIRECTORY.getVariableName()));
        return ap;
    }


}
