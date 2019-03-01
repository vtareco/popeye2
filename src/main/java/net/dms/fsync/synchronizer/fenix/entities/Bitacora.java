package net.dms.fsync.synchronizer.fenix.entities;

import java.io.Serializable;
import java.util.Date;

public class Bitacora implements Serializable {

	private Long id;
	private String user;
	private Date creationDate;
	private String comment;

	public Bitacora() {
	}

	public Bitacora(String user, String comment) {
		this.id = -1 * System.currentTimeMillis();
		this.creationDate = new Date();
		this.user = user;
		this.comment = comment;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
