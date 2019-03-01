package net.dms.fsync.synchronizer.fenix.entities;

public class FenixRequirementSpecification {

	private String codigo;
	private String description;
	private String storyPoints;
	private String hours;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStoryPoints() {
		return storyPoints;
	}

	public void setStoryPoints(String storyPoints) {
		this.storyPoints = storyPoints;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}
}
