package net.dms.fsync.synchronizer.LocalVariables.business;

import net.dms.fsync.httphandlers.common.Utils;
import net.dms.fsync.settings.entities.EverisVariables;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.Filter;
import net.dms.fsync.synchronizer.LocalVariables.entities.OtInfo;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;

//CREATED BY VDIVIZIN 08/02/2019
public class VariableService {

    public static final String EVERIS_CONF_JIRA_FILTRO = "everis.conf.jira.filtro.";
    LocalVariables localVariables = new LocalVariables();

    public UserChange getUserVariables() {
        UserChange uc = new UserChange();
        uc.setFenixUser(localVariables.getVariables().get(EverisVariables.FENIX_USER.getVariableName()));
        uc.setFenixPassword(localVariables.getVariables().get(EverisVariables.FENIX_PASSWORD.getVariableName()));
        uc.setJirauser(localVariables.getVariables().get(EverisVariables.JIRA_USER.getVariableName()));
        uc.setJiraPassword(localVariables.getVariables().get(EverisVariables.JIRA_PASSWORD.getVariableName()));

        return uc;
    }

    public ApplicationProperties getApplicationVariables() {
        ApplicationProperties ap = new ApplicationProperties();
        ap.setFenixUrl(localVariables.getVariables().get(EverisVariables.FENIX_URL_BASE.getVariableName()));
        ap.setJiraUrl(localVariables.getVariables().get(EverisVariables.JIRA_URL_BASE.getVariableName()));
        ap.setProxyHost(localVariables.getVariables().get(EverisVariables.PROXY_HOST.getVariableName()));
        ap.setProxyPort(localVariables.getVariables().get(EverisVariables.PROXY_PORT.getVariableName()));
        ap.setWorkingDirectory(localVariables.getVariables().get(EverisVariables.WORKING_DIRECTORY.getVariableName()));
        ap.setWorkingLanguage("en");
        return ap;
    }





    public UserChange readJsonUserConf(String path) {
        UserChange uc = new UserChange();
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;

            uc.setFenixUser((String) jsonObject.get("fenixUser"));
            uc.setFenixPassword((String) jsonObject.get("fenixPassword"));
            uc.setJirauser((String) jsonObject.get("jiraUser"));
            uc.setJiraPassword((String) jsonObject.get("jiraPassword"));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return uc;
    }

    public ArrayList<Filter> readJsonFilterList(String path) {
        ArrayList<Filter> arListfilters = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("type").equals(WorkingJira.getJiraBmw())) {
                JSONArray jsonArray = (JSONArray) jsonObject.get("querys");
                for (Object f : jsonArray) {
                    Filter filter = new Filter(((JSONObject) f).get("filterName").toString(), ((JSONObject) f).get("filterQuery").toString());
                    arListfilters.add(filter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arListfilters;
    }

    public void writeServerConfIntoJson(ApplicationProperties ap, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONObject newCredentials = new JSONObject();


            jsonObject.put("fenixUrl", ap.getFenixUrl());
            jsonObject.put("jiraUrl", ap.getJiraUrl());
            jsonObject.put("proxyHost", ap.getProxyHost());
            jsonObject.put("proxyPort", ap.getProxyPort());
            jsonObject.put("workingLanguage",ap.getWorkingLanguage());
            char[] currectPath = ap.getWorkingDirectory().toCharArray();
            for (int i = 0; i < currectPath.length; i++) {
                if (currectPath[i] == '\\') {
                    currectPath[i] = '/';
                }
            }
            jsonObject.put("workingDirectory", String.valueOf(currectPath));

            FileWriter file = new FileWriter(path);
            newCredentials.writeJSONString(jsonObject, file);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void writeUserConfIntoJson(UserChange uc, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            JSONObject newUser = new JSONObject();

            jsonObject.put("fenixUser", uc.getFenixUser());
            jsonObject.put("fenixPassword", uc.getFenixPassword());
            jsonObject.put("jiraUser", uc.getJirauser());
            jsonObject.put("jiraPassword", uc.getJiraPassword());

            FileWriter file = new FileWriter(path);
            newUser.writeJSONString(jsonObject, file);
            file.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public void writeFilterConfIntoJson(Filter filter, String path) {
        JSONParser jsonParser = new JSONParser();
        int validator = 0;
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("type").toString().equals(WorkingJira.getJiraBmw())) {
                JSONArray jsonArray = (JSONArray) jsonObject.get("querys");
                for (Object f : jsonArray) {
                    if (((JSONObject) f).get("filterName").toString().equals(filter.getFilterName())) {
                        ((JSONObject) f).remove("filterQuery");
                        ((JSONObject) f).put("filterQuery", filter.getFilterQuery());
                        validator = 1;

                    }
                }
                if (validator == 0) {
                    JSONObject newFilter = new JSONObject();
                    newFilter.put("filterName", filter.getFilterName());
                    newFilter.put("filterQuery", filter.getFilterQuery());
                    jsonArray.add(newFilter);
                }
                JSONObject newQuerys = new JSONObject();
                newQuerys.put("querys", jsonArray);
                FileWriter file = new FileWriter(path);
                newQuerys.writeJSONString(jsonObject, file);
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void filterDeleteFromJson(Filter filter, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            if (jsonObject.get("type").toString().equals(WorkingJira.getJiraBmw())) {
                JSONArray jsonArray = (JSONArray) jsonObject.get("querys");
                for (Object f : jsonArray) {
                    if (((JSONObject) f).get("filterName").toString().equals(filter.getFilterName())) {
                        jsonArray.remove(f);
                        JSONArray newFilters = jsonArray;
                        JSONObject newQuerys = new JSONObject();
                        newQuerys.put("querys", newFilters);
                        FileWriter file = new FileWriter(path);
                        newQuerys.writeJSONString(jsonObject, file);
                        break;
                    }
                }
            }
            JSONObject newQuerys = new JSONObject();
            FileWriter file = new FileWriter(path);
            newQuerys.writeJSONString(jsonObject, file);
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public ApplicationProperties readJsonServerConf(String path) {
        ApplicationProperties ap = new ApplicationProperties();
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser.parse(new FileReader(path));
            JSONObject jsonObject = (JSONObject) object;
            ap.setFenixUrl((String) jsonObject.get("fenixUrl"));
            ap.setJiraUrl((String) jsonObject.get("jiraUrl"));
            ap.setProxyHost((String) jsonObject.get("proxyHost"));
            ap.setProxyPort((String) jsonObject.get("proxyPort"));
            ap.setWorkingDirectory((String) jsonObject.get("workingDirectory"));
            ap.setWorkingLanguage((String)jsonObject.get("workingLanguage"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ap;
    }


    public void createUserJson(UserChange uc, String path) {
        JSONObject userJson = new JSONObject();
        userJson.put("fenixUser", uc.getFenixUser());
        userJson.put("jiraUser", uc.getJirauser());
        userJson.put("fenixPassword", uc.getFenixPassword());
        userJson.put("jiraPassword", uc.getJiraPassword());

        try (FileWriter file = new FileWriter(path)) {
            file.write(userJson.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFilterJson(String path) {
        JSONObject newFilters = new JSONObject();
        InputStream url2 = getClass().getResourceAsStream("/bmw/rsp/everis_overriden.conf");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(url2))) {
            String filterName;
            String filterQuery;

            JSONArray filters = new JSONArray();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(EVERIS_CONF_JIRA_FILTRO)) {
                    JSONObject filter = new JSONObject();
                    filterName = line.substring(EVERIS_CONF_JIRA_FILTRO.length(), line.indexOf("="));
                    filterQuery = line.substring(line.indexOf("=") + 1);
                    filter.put("filterName", filterName);
                    filter.put("filterQuery", filterQuery);
                    filters.add(filter);
                }
                System.out.println(line);
            }


            newFilters.put("type", WorkingJira.getJiraBmw());
            newFilters.put("querys", filters);
            Writer file = new FileWriter(path);
            file.write(newFilters.toJSONString());

            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void createApplicationPropertiesJson(ApplicationProperties ap, String path) {
        JSONObject applcationPropertiesJson = new JSONObject();
        applcationPropertiesJson.put("fenixUrl", ap.getFenixUrl());
        applcationPropertiesJson.put("proxyPort", ap.getProxyPort());
        applcationPropertiesJson.put("jiraUrl", ap.getJiraUrl());
        applcationPropertiesJson.put("workingDirectory", ap.getWorkingDirectory());
        applcationPropertiesJson.put("proxyHost", ap.getProxyHost());
        applcationPropertiesJson.put("workingLanguage",ap.getWorkingLanguage());

        try (FileWriter file = new FileWriter(path)) {
            file.write(applcationPropertiesJson.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Filter getFilter(String filterName, String path) {
        Filter filter = new Filter();
        filter.setFilterName("null");
        ArrayList<Filter> arFilters = readJsonFilterList(path);
        for (Filter f : arFilters) {
            if (f.getFilterName().equals(filterName)) {
                filter = f;
                return filter;
            }
        }
        return filter;
    }



    /*public FenixAcc readInfo(String peticion){
        FenixAcc acc = new FenixAcc();
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();

        JSONParser jsonparser = new JSONParser();


        try{
            Object obj = jsonparser.parse(new FileReader(projectPath+"/"+peticion+"/OT_INFO"+"/info.json"));
            System.out.println("PETICAO BOYS "+peticion);

            //projectPath + "/" + peticionSelected + "/OT_INFO" + "/info.json")

            JSONObject object = (JSONObject) obj;

            acc.setIdOt((String) object.get("ID_Peticion"));

            System.out.println("OBJETO "+object);

            return acc;

        }catch(Exception e){
            e.printStackTrace();
        }


        return null;
    }*/

    public OtInfo readInfo(String peticion){
        LocalVariables lv = new LocalVariables();
        OtInfo otinfo = new OtInfo();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();
        JSONParser jsonparser = new JSONParser();
        try{
            Object obj = jsonparser.parse(new FileReader(projectPath+"/"+peticion+"/OT_INFO"+"/info.json"));
            JSONObject object = (JSONObject) obj;
            otinfo.setId_peticion((String) object.get("ID_Peticion"));
            otinfo.setCodigoPeticionCliente((String) object.get("Codigo_Peticion_Cliente"));
            // a.setIdOt((String) object.get("ID_Peticion"));
            // System.out.println("LOL "+a.getIdOt());
            System.out.println("OBJETO INFO"+otinfo);
            return otinfo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public OtInfo setInfo(String peticion,OtInfo otinfo){
        LocalVariables lv = new LocalVariables();
        ApplicationProperties ap = lv.getApFromJson(WorkingJira.getJsonApplicationProperties());
        String projectPath = ap.getWorkingDirectory();
        try {
            new File(projectPath + "/" + peticion + "/OT_INFO").mkdirs();
            System.out.println("CAMINHO "+projectPath + "/" + peticion + "/OT_INFO");

            JSONObject object = new JSONObject();
            object.put("ID_Peticion",otinfo.getId_peticion());
            object.put("Codigo_Peticion_Cliente",otinfo.getCodigoPeticionCliente());

            try (FileWriter f = new FileWriter(projectPath + "/" + peticion + "/OT_INFO" + "/info.json")) {
                f.write(object.toJSONString());
                System.out.println("OT_INFO: " + object);
            }

            return otinfo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getAvailableLanguages() {
       ArrayList<String> arLanguages = new ArrayList<>();
        for (File file : new File(Utils.getProgramRoot()+"\\classes\\i18n").listFiles()) {
            if (file.isFile()) {
                arLanguages.add(file.getName().substring(file.getName().indexOf("e_")+2,file.getName().indexOf(".p")));
            }
        }

        return arLanguages;
    }

}
