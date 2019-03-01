package net.dms.fsync.synchronizer.LocalVariables.entities;

public class OtInfo {

	private String id_peticion;
	private String codigoPeticionCliente;

	public OtInfo() {
	}

	public OtInfo(String id_peticion, String codigoPeticionCliente) {
		this.id_peticion = id_peticion;
		this.codigoPeticionCliente = codigoPeticionCliente;
	}

	public String getId_peticion() {
		return id_peticion;
	}

	public void setId_peticion(String id_peticion) {
		this.id_peticion = id_peticion;
	}

	public String getCodigoPeticionCliente() {
		return codigoPeticionCliente;
	}

	public void setCodigoPeticionCliente(String codigoPeticionCliente) {
		this.codigoPeticionCliente = codigoPeticionCliente;
	}
}
