package net.dms.fsync.settings.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dms.fsync.httphandlers.entities.exceptions.AppException;
import net.dms.fsync.settings.entities.Settings;

/**
 * Created by dminanos on 19/11/2017.
 */
public class SettingsService {

	private static Settings settings;
	private static SettingsService INSTANCE;

	private SettingsService() {
		loadSettings();
	}

	public static SettingsService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SettingsService();
		}
		return INSTANCE;
	}

	private void loadSettings() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			settings = mapper.readValue(this.getClass().getResourceAsStream("/bmw/rsp/everis.json"), Settings.class);
		} catch(Exception e) {
			throw new AppException(e);
		}
	}

	public Settings getSettings() {
		return settings;
	}
}
