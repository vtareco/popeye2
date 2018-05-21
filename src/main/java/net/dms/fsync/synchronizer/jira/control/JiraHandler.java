package net.dms.fsync.synchronizer.jira.control;


import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminanos on 10/04/2017.
 */
public class JiraHandler {

    public static void main(String[] args){

        // HttpClient client = HttpClientBuilder.create().build();

        CloseableHttpClient httpclient = HttpClients.createDefault();
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";

        HttpPost request = new HttpPost("https://jira64.nttdata-emea.com/jira/login.jsp?os_destination=%2Fsecure%2FDashboard.jspa");

        request.setHeader("User-Agent", "");
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "en-US,en;q=0.5");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));




        HttpResponse response = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(request);
            response = httpclient.execute(request);
            System.out.println("response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int responseCode = response.getStatusLine().getStatusCode();
    }

    private static void query(CloseableHttpClient httpclient){
        HttpPost request = new HttpPost("https://jira64.nttdata-emea.com/jira/secure/QueryComponent!Jql.jspa");

        request.setHeader("User-Agent", "");
        request.setHeader("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Language", "en-US,en;q=0.5");

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));




        HttpResponse response = null;
        try {
            request.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = httpclient.execute(request);
            response = httpclient.execute(request);
            System.out.println("response: " + response);
        } catch (IOException e) {
            throw new AppException(e);
        }
        int responseCode = response.getStatusLine().getStatusCode();
    }
}
