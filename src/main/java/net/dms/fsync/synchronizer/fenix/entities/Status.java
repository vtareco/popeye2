package net.dms.fsync.synchronizer.fenix.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by dminanos on 14/04/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
