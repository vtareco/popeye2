package net.dms.fsync.synchronizer.LocalVariables.entities;

public class JsonPaths {

	private String userJsonPath;
	private String applicationPropertiesPath;
	private String filtersPath;

	public JsonPaths(String userJsonPath, String applicationPropertiesPath, String filtersPath) {
		this.userJsonPath = userJsonPath;
		this.applicationPropertiesPath = applicationPropertiesPath;
		this.filtersPath = filtersPath;
	}

	public String getUserJsonPath() {
		return userJsonPath;
	}

	public void setUserJsonPath(String userJsonPath) {
		this.userJsonPath = userJsonPath;
	}

	public String getApplicationPropertiesPath() {
		return applicationPropertiesPath;
	}

	public void setApplicationPropertiesPath(String applicationPropertiesPath) {
		this.applicationPropertiesPath = applicationPropertiesPath;
	}

	public String getFiltersPath() {
		return filtersPath;
	}

	public void setFiltersPath(String filtersPath) {
		this.filtersPath = filtersPath;
	}
}
