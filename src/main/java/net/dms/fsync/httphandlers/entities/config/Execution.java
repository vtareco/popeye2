package net.dms.fsync.httphandlers.entities.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dminanos on 11/04/2017.
 */
@XmlRootElement
public class Execution {


    private ProxyConfiguration proxyConfiguration;

    private List<Action> actions;

    @XmlElementWrapper
    @XmlElement(name="action")
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @XmlElement(name = "proxy")
    public ProxyConfiguration getProxyConfiguration() {
        return proxyConfiguration;
    }

    public void setProxyConfiguration(ProxyConfiguration proxyConfiguration) {
        this.proxyConfiguration = proxyConfiguration;
    }

    @Override
    public String toString() {
        return "Execution{" +
                "actions=" + actions +
                '}';
    }
}
