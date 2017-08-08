package net.dms.popeye.handlers.jfsynchronizer.fenix.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dminanos on 21/04/2017.
 */
public class FenixACCUploadResponseItem {
    private String nombre;
    @JsonProperty(value = "pet_cliente")
    private String petCliente;
    private Integer linea;
    private String motivo;
    @JsonProperty(value = "es_error")
    private Boolean error;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPetCliente() {
        return petCliente;
    }

    public void setPetCliente(String petCliente) {
        this.petCliente = petCliente;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "FenixACCUploadResponseItem{" +
                "nombre='" + nombre + '\'' +
                ", petCliente='" + petCliente + '\'' +
                ", linea=" + linea +
                ", motivo='" + motivo + '\'' +
                ", error=" + error +
                '}';
    }
}
