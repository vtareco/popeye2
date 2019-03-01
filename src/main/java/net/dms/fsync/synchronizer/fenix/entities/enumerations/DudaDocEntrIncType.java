package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 05/02/2019.
 */
public enum DudaDocEntrIncType implements VisualizableType {
	SI("Si"),
	NO("No");

	private String description;

	DudaDocEntrIncType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
