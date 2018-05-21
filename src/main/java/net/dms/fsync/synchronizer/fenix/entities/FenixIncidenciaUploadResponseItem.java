package net.dms.fsync.synchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FenixIncidenciaUploadResponseItem {
     private String linea;
    private String columna;
    private String id;
    private String nombre;
    private String estado;
    private String motivo;
    @JsonProperty(value = "es_error")
    private Boolean esError;

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getEsError() {
        return esError;
    }

    public void setEsError(Boolean esError) {
        this.esError = esError;
    }

    @Override
    public String toString() {
        return "FenixIncidenciaUploadResponseItem{" +
                "linea='" + linea + '\'' +
                ", columna='" + columna + '\'' +
                ", id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", motivo='" + motivo + '\'' +
                ", esError=" + esError +
                '}';
    }
}
