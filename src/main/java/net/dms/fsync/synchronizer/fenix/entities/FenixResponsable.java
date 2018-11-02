package net.dms.fsync.synchronizer.fenix.entities;

import net.dms.fsync.synchronizer.fenix.entities.enumerations.AccSubType;

public class FenixResponsable {
    private Double esfuerzo;
    private String nombre;
    private String numero;
    private String subtipoTarea;

    public FenixResponsable(Double esfuerzo, String nombre, String numero, String subtipoTarea) {
        this.esfuerzo = esfuerzo;
        this.nombre = nombre;
        this.numero = numero;
        this.subtipoTarea = subtipoTarea;
    }

    public Double getEsfuerzo() {
        return esfuerzo;
    }

    public void setEsfuerzo(Double esfuerzo) {
        this.esfuerzo = esfuerzo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSubtipoTarea() {
        return subtipoTarea;
    }

    public void setSubtipoTarea(String subtipoTarea) {
        this.subtipoTarea = subtipoTarea;
    }
}
