package net.dms.fsync.httphandlers.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminanos on 13/04/2017.
 */
public class ActionResponse {

	private String name;
	private Map<String, String> variables = new HashMap<String, String>();
	private Object response;

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
