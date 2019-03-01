package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 23/05/2017.
 */
public enum IncidenciaLocalizadaEnType implements VisualizableType {
	HERRAMIENTA_TESTING("HERRAMIENTA_TESTING"),
	PEER_REVIEW_CODIGO("PEER_REVIEW_CODIGO"),
	PEER_REVIEW_DF("PEER_REVIEW_DF"),
	PEER_REVIEW_EJECUCION_PRUEBAS("PEER_REVIEW_EJECUCION_PRUEBAS"),
	PRUEBAS_FUNCIONALIDAD("PRUEBAS_FUNCIONALIDAD"),
	PRUEBAS_INTEGRADAS("PRUEBAS_INTEGRADAS"),
	PRUEBAS_UNITARIAS("PRUEBAS_UNITARIAS");

	private String description;

	IncidenciaLocalizadaEnType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
