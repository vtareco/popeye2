package net.dms.popeye.settings.entities;

/**
 * Created by dminanos on 19/11/2017.
 */
public class ConnectionSettings {
    private String proxyHost;
    private String proxyPort;

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
}
