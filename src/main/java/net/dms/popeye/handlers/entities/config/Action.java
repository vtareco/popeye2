package net.dms.popeye.handlers.entities.config;

import net.dms.popeye.handlers.entities.enumerations.HttpMethod;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminanos on 10/04/2017.
 */
@XmlRootElement
public class Action {
private String url;
private String name;
    private HttpMethod httpMethod;
    private List<Header> headers = new ArrayList<>();
    private List<Parameter> parameters  = new ArrayList<>();
    private List<ResponseValidator> validators = new ArrayList<>();

    @XmlElement
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }


    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @XmlElementWrapper
    @XmlElement(name="header")
    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    @XmlElement
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlElementWrapper
    @XmlElement(name="parameter")
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }


    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper
    @XmlElement(name="validator")
    public List<ResponseValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<ResponseValidator> validators) {
        this.validators = validators;
    }

    public String toString() {
        return "Action{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", httpMethod=" + httpMethod +
                ", headers=" + headers +
                ", parameters=" + parameters +
                '}';
    }
}
