package net.dms.fsync.httphandlers.entities.config;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by dminanos on 19/04/2017.
 */
public class ProxyConfiguration {

	private String scheme;

	private String host;

	private Integer port;

	@XmlAttribute
	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	@XmlAttribute
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@XmlAttribute
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
