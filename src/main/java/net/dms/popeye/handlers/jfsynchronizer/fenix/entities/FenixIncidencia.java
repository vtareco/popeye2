package net.dms.popeye.handlers.jfsynchronizer.fenix.entities;

import java.util.Date;
import java.util.Objects;

/**
 * Created by dminanos on 22/05/2017.
 */
public class FenixIncidencia {
    private Long idInterno;
    private Long idIncidencia;
    private String idPeticionOt;
    private String nombreIncidencia;
    private String localizadaEn;
    private String tipoIncidencia;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private Double esfuerzoHh;
    private String urgencia;
    private String impacto;
    private String resueltaPorCliente;
    private String prioridad;
    private Date fechaPrevistaCentro;
    private String tareaCausante;
    private String otCorrector;
    private String accCorrector;
    private String estado;

    public FenixIncidencia() {
        this.idInterno = -1 * System.currentTimeMillis();
    }

    public Long getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Long idIncidencia) {
        this.idIncidencia = idIncidencia;
        setIdInterno(idIncidencia);
    }

    public String getIdPeticionOt() {
        return idPeticionOt;
    }

    public void setIdPeticionOt(String idPeticionOt) {
        this.idPeticionOt = idPeticionOt;
    }

    public String getNombreIncidencia() {
        return nombreIncidencia;
    }

    public void setNombreIncidencia(String nombreIncidencia) {
        this.nombreIncidencia = nombreIncidencia;
    }

    public String getLocalizadaEn() {
        return localizadaEn;
    }

    public void setLocalizadaEn(String localizadaEn) {
        this.localizadaEn = localizadaEn;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getEsfuerzoHh() {
        return esfuerzoHh;
    }

    public void setEsfuerzoHh(Double esfuerzoHh) {
        this.esfuerzoHh = esfuerzoHh;
    }

    public String getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public String getResueltaPorCliente() {
        return resueltaPorCliente;
    }

    public void setResueltaPorCliente(String resueltaPorCliente) {
        this.resueltaPorCliente = resueltaPorCliente;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaPrevistaCentro() {
        return fechaPrevistaCentro;
    }

    public void setFechaPrevistaCentro(Date fechaPrevistaCentro) {
        this.fechaPrevistaCentro = fechaPrevistaCentro;
    }

    public String getTareaCausante() {
        return tareaCausante;
    }

    public void setTareaCausante(String tareaCausante) {
        this.tareaCausante = tareaCausante;
    }

    public String getOtCorrector() {
        return otCorrector;
    }

    public void setOtCorrector(String otCorrector) {
        this.otCorrector = otCorrector;
    }

    public String getAccCorrector() {
        return accCorrector;
    }

    public void setAccCorrector(String accCorrector) {
        this.accCorrector = accCorrector;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdInterno() {
        return idInterno;
    }

    public void setIdInterno(Long idInterno) {
        this.idInterno = idInterno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FenixIncidencia that = (FenixIncidencia) o;
        return Objects.equals(idInterno, that.idInterno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInterno);
    }
}
