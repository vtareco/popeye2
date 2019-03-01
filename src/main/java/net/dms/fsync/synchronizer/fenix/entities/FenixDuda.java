package net.dms.fsync.synchronizer.fenix.entities;

import java.util.Date;

/**
 * Created by vics on 05/02/2019.
 */
public class FenixDuda {

	private Long idDuda;
	private Date fechaAlta;
	private String estado;
	private String acc;
	private String descripcion;
	private Long idRequerimiento;
	private String respuesta;
	private Date fechaPrevistaRespuesta;
	private String respRespuestaProyecto;
	private String responsableConsulta;
	private String respRespuestaCliente;
	private Long agrupacion;
	private Long idRelacionada;
	private String criticidad;
	private String fLocalizada;
	private String relativaA;
	private String creador;
	private String docIncomp;
	private Date fechaUltAct;
	private String autorUltAct;
	private String ambito;

	private String idOt;

	public FenixDuda() {
	}

	public String getIdOt() {
		return idOt;
	}

	public void setIdOt(String idot) {
		this.idOt = idot;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getResponsableConsulta() {
		return responsableConsulta;
	}

	public void setResponsableConsulta(String responsableConsulta) {
		this.responsableConsulta = responsableConsulta;
	}

	public String getCreador() {
		return creador;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}

	public Long getIdRequerimiento() {
		return idRequerimiento;
	}

	public void setIdRequerimiento(Long idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public Long getIdDuda() {
		return idDuda;
	}

	public void setIdDuda(Long idDuda) {
		this.idDuda = idDuda;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getFechaPrevistaRespuesta() {
		return fechaPrevistaRespuesta;
	}

	public void setFechaPrevistaRespuesta(Date fechaPrevistaRespuesta) {
		this.fechaPrevistaRespuesta = fechaPrevistaRespuesta;
	}

	public String getRespRespuestaProyecto() {
		return respRespuestaProyecto;
	}

	public void setRespRespuestaProyecto(String respRespuestaProyecto) {
		this.respRespuestaProyecto = respRespuestaProyecto;
	}

	public String getRespRespuestaCliente() {
		return respRespuestaCliente;
	}

	public void setRespRespuestaCliente(String respRespuestaCliente) {
		this.respRespuestaCliente = respRespuestaCliente;
	}

	public Long getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(Long agrupacion) {
		this.agrupacion = agrupacion;
	}

	public Long getIdRelacionada() {
		return idRelacionada;
	}

	public void setIdRelacionada(Long idRelacionada) {
		this.idRelacionada = idRelacionada;
	}

	public String getCriticidad() {
		return criticidad;
	}

	public void setCriticidad(String criticidad) {
		this.criticidad = criticidad;
	}

	public String getFLocalizada() {
		return fLocalizada;
	}

	public void setFLocalizada(String fLocalizada) {
		this.fLocalizada = fLocalizada;
	}

	public String getRelativaA() {
		return relativaA;
	}

	public void setRelativaA(String relativaA) {
		this.relativaA = relativaA;
	}

	public String getDocIncomp() {
		return docIncomp;
	}

	public void setDocIncomp(String docIncomp) {
		this.docIncomp = docIncomp;
	}

	public Date getFechaUltAct() {
		return fechaUltAct;
	}

	public void setFechaUltAct(Date fechaUltAct) {
		this.fechaUltAct = fechaUltAct;
	}

	public String getAutorUltAct() {
		return autorUltAct;
	}

	public void setAutorUltAct(String autorUltAct) {
		this.autorUltAct = autorUltAct;
	}
}
