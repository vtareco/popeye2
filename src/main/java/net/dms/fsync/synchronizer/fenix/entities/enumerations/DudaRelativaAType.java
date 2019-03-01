package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 05/02/2019.
 */
public enum DudaRelativaAType implements VisualizableType {
	REQUERIMIENTOS("Requerimientos"),
	DF("DF"),
	DT_CC("DT/CC"),
	CODIGO("Código"),
	PLAN_DE_PRUEBAS("Plan de Pruebas");

	private String description;

	DudaRelativaAType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
