package net.dms.fsync.settings.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminanos on 19/11/2017.
 */
public class JiraSettings {

	private Map<String, String> filters = new HashMap<>();

	private Map<String, String> responsibles = new HashMap<>();

	private String filterByIds;

	public String getFilterByIds() {
		return filterByIds;
	}

	public void setFilterByIds(String filterByIds) {
		this.filterByIds = filterByIds;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public Map<String, String> getResponsibles() {
		return responsibles;
	}

	public void setResponsibles(Map<String, String> responsibles) {
		this.responsibles = responsibles;
	}
}
