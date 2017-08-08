package net.dms.popeye.handlers.entities.config;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by dminanos on 11/04/2017.
 */
public class Header {

    private String name;

    private String value;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Header{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
