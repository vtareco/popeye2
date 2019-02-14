package net.dms.fsync.synchronizer.LocalVariables.business;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.sun.xml.internal.txw2.annotation.XmlElement;
import net.dms.fsync.httphandlers.entities.config.*;
import net.dms.fsync.settings.entities.EverisVariables;
import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;
import net.dms.fsync.synchronizer.LocalVariables.entities.ApplicationProperties;
import net.dms.fsync.synchronizer.LocalVariables.entities.Filter;
import net.dms.fsync.synchronizer.LocalVariables.entities.UserChange;
import net.dms.fsync.synchronizer.LocalVariables.entities.WorkingJira;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//CREATED BY VDIVIZIN 08/02/2019
public class VariableService {

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
        return ap;
    }


    public ArrayList<Filter> getFilterVariables() {
        ArrayList<Filter> arFilters = new ArrayList<>();


        return arFilters;
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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

        URL url = this.getClass().getClassLoader().getResource("bmw/rsp/everis_overriden.conf");
        File everisOverConfPath = new File(url.getPath());

        try (BufferedReader br = new BufferedReader(new FileReader(everisOverConfPath.toString()))) {
            String filterName;
            String filterQuery;

            JSONArray filters = new JSONArray();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("everis.conf.jira.filtro.")) {
                    JSONObject filter = new JSONObject();
                    filterName = line.substring(24, line.indexOf("="));
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



}
