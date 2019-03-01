package net.dms.fsync.settings.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminanos on 19/11/2017.
 */
public class Settings {

	private List<Actor> actores = new ArrayList();

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}
}

