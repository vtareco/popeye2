package net.dms.fsync.synchronizer.fenix.entities;

public class FenixResponsable {
    private Double esfuerzo;
    private String nombre;
    private String numero;

    public FenixResponsable(Double esfuerzo, String nombre, String numero) {
        this.esfuerzo = esfuerzo;
        this.nombre = nombre;
        this.numero = numero;
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
}
