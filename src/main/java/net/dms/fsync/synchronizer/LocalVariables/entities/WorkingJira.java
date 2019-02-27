package net.dms.fsync.synchronizer.LocalVariables.entities;

import net.dms.fsync.synchronizer.LocalVariables.control.LocalVariables;

public class WorkingJira {


    public static String idPeticion;

    private static String jenixFoulder = "c://JenixSettings";
    private static String jsonUserCreate = jenixFoulder + "/UserConfig.json";
    private static String jsonApplicationProperties = jenixFoulder + "/ApplicationProperties.json";
    private static String jsonFilters = jenixFoulder + "/Filters.json";

    private static String fenixProduction  = "https://fe";
    private static String fenixTest = "http://10";


    private static String jiraBmw = "jira.bmw";
    private static String jiraFilterProperty = "everis.conf.jira.filtro.";

    private static String osUser = "os_username";
    private static String osPassword = "os_password";
    private static String user = "username";
    private static String password = "password";
    private static String jUser = "j_username";
    private static String jPassword = "j_password";
    private static String upload = "upload";
    private static String headerOrigin = "Origin";
    private static String headerReferer = "Referer";
    private static String search = "search";
    private static String logIn = "login";
    private static String download = "download";
    private static String loginConfirm= "login_confirm";
    private static String metaInfo = "metainfo";


    private static String fenix_Upload_incidencias = "fenix_upload_incidencias";
    private static String fenixUploadAccs = "fenix_upload_accs";
    private static String fenixLogin = "fenix_login";
    private static String fenixMetaInfoIncidencias = "fenix_metainfo_incidencias";
    private static String fenixDownloadIncidencias = "fenix_download_incidencias";
    private static String fenixDownloadDudas = "fenix_download_dudas";
    private static String fenixDownloadAccsIncurridos = "fenix_download_accs_incurridos";
    public static String fenixDownloadAccs = "fenix_download_accs";
    private static String extranetLoginConfirm = "extranet_login_confirm";
    private static String extranetLogin = "extranet_ogin";
    private static String authJiraIssuesSearch = "AUTH_jira_issues_search";



    private static String fenixLoginLoginUrl = " fenix/validarLoginAction.dojo?timestamp=${everis.fenix.timestamp}";
    private static String fenixUploadincidenciasUploadUrl = "/fenix/requerimientos/IncidenciaNuevoCargaMasivaAction.do?perform=carga";
    private static String fenixUploadincidenciasUploadReferer = "/fenix/requerimientos/CargaMasivaIncidenciasNuevo.jsp";
    private static String fenixUploadAccsUploadUrl = "/fenix/actuaciones/accCargaMasivaAction.do?perform=cargaACCs";
    private static String jiraIssuesSearch = "jira_issues_search";
    private static String jiraIssuesSearchLogInUrl = "/jira/login.jsp";
    private static String jiraIssuesSearchRetrieveStatusUrl = "/jira/rest/api/2/search?jql=${everis.jira.jql}";
    private static String jiraIssuesSearchLogIn = "login";
    private static String jiraISsuesSearchRetrieveIssues = "retrieve-issues";

    public static String getIdPeticion() {
        return idPeticion;
    }

    public static void setIdPeticion(String idPeticion) {
        WorkingJira.idPeticion = idPeticion;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getLoginConfirm() {
        return loginConfirm;
    }

    public static String getDownload() {
        return download;
    }

    public static String getSearch() {
        return search;
    }

    public static String getFenixProduction() {
        return fenixProduction;
    }

    public static String getFenixTest() {
        return fenixTest;
    }

    public static String getMetaInfo() {

        return metaInfo;
    }

    public static String getjUser() {
        return jUser;
    }

    public static String getjPassword() {
        return jPassword;
    }

    public static String getFenixUploadAccsUploadUrl() {
        return fenixUploadAccsUploadUrl;
    }


    public static String getLogIn() {
        return logIn;
    }

    public static String getFenixUploadincidenciasUploadReferer() {
        return fenixUploadincidenciasUploadReferer;
    }

    public static String getFenixUploadincidenciasUploadUrl() {
        return fenixUploadincidenciasUploadUrl;
    }

    public static String getHeaderOrigin() {
        return headerOrigin;
    }

    public static String getHeaderReferer() {
        return headerReferer;
    }

    public static String getUpload() {
        return upload;
    }

    public static String getJenixFoulder() {
        return jenixFoulder;
    }

    public static String getJsonUserCreate() {
        return jsonUserCreate;
    }

    public static String getJsonApplicationProperties() {
        return jsonApplicationProperties;
    }

    public static String getJsonFilters() {
        return jsonFilters;
    }

    public static String getJiraBmw() {
        return jiraBmw;
    }

    public static String getJiraFilterProperty() {
        return jiraFilterProperty;
    }

    public static String getOsUser() {
        return osUser;
    }

    public static String getOsPassword() {
        return osPassword;
    }

    public static String getFenix_Upload_incidencias() {
        return fenix_Upload_incidencias;
    }

    public static String getFenixUploadAccs() {
        return fenixUploadAccs;
    }

    public static String getFenixLogin() {
        return fenixLogin;
    }

    public static String getFenixMetaInfoIncidencias() {
        return fenixMetaInfoIncidencias;
    }

    public static String getFenixDownloadIncidencias() {
        return fenixDownloadIncidencias;
    }

    public static String getFenixDownloadDudas() {
        return fenixDownloadDudas;
    }

    public static String getFenixDownloadAccsIncurridos() {
        return fenixDownloadAccsIncurridos;
    }

    public static String getFenixDownloadAccs() {
        return fenixDownloadAccs;
    }

    public static String getExtranetLoginConfirm() {
        return extranetLoginConfirm;
    }

    public static String getExtranetLogin() {
        return extranetLogin;
    }

    public static String getAuthJiraIssuesSearch() {
        return authJiraIssuesSearch;
    }

    public static String getJiraIssuesSearch() {
        return jiraIssuesSearch;
    }

    public static String getJiraIssuesSearchLogInUrl() {
        return jiraIssuesSearchLogInUrl;
    }

    public static String getJiraIssuesSearchRetrieveStatusUrl() {
        return jiraIssuesSearchRetrieveStatusUrl;
    }

    public static String getJiraIssuesSearchLogIn() {
        return jiraIssuesSearchLogIn;
    }

    public static String getJiraISsuesSearchRetrieveIssues() {
        return jiraISsuesSearchRetrieveIssues;
    }

}
