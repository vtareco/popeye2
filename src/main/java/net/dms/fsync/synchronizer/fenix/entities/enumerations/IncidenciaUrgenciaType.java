package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 24/05/2017.
 */
public enum IncidenciaUrgenciaType implements VisualizableType {
	ALTA("ALTA"),
	MEDIA("MEDIA"),
	BAJA("BAJA");

	private String description;

	IncidenciaUrgenciaType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
