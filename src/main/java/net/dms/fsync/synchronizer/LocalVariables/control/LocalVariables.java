package net.dms.fsync.synchronizer.LocalVariables.control;

import net.dms.fsync.settings.entities.EverisConfig;
import net.dms.fsync.settings.entities.EverisPropertiesType;
import net.dms.fsync.settings.entities.EverisVariables;

import net.dms.fsync.synchronizer.LocalVariables.business.VariableService;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.Filter;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.fenix.entities.FenixAcc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LocalVariables {

    public Map<String, String> getVariables() {
        Map<String, String> variables = LocalVariables();
        return variables;
    }


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




    public void userConfiguration(UserChange uc,String path){
        VariableService vs = new VariableService();
        vs.writeUserConfIntoJson(uc,path);
    }


   public void serverConfiguration(ApplicationProperties ap, String path){
       VariableService vs = new VariableService();
       vs.writeServerConfIntoJson(ap,path);
   }

   public void filterConfiguration(Filter filter,String path){
       VariableService vs = new VariableService();
       vs.writeFilterConfIntoJson(filter,path);
   }
    public void filterDelete(Filter filter, String path) {
        VariableService vs = new VariableService();
        vs.filterDeleteFromJson(filter,path);
    }




   public   ApplicationProperties getApFromJson(String path){
       VariableService vs = new VariableService();

        return vs.readJsonServerConf(path);
   }

  public UserChange getUcFromJson(String path) {
      VariableService vs = new VariableService();
      return vs.readJsonUserConf(path);
  }

  public ArrayList<Filter> filterList(String path){
      VariableService vs = new VariableService();

      return vs.readJsonFilterList(path);
  }


  public Filter getSelectedFilter(String filterName,String path){
        VariableService vs = new VariableService();
        return vs.getFilter(filterName,path);
    }


    public String readOtInfoFile(String peticionselect){
        VariableService vs = new VariableService();
        return vs.readInfo(peticionselect);
    }



}
