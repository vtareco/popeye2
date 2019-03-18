package net.dms.fsync.synchronizer.fenix.entities;

import java.util.Date;
import java.util.List;

public class IncidenciaExterna {

    private String idAcc;
    private List valuesAccTable;
    private List<FenixIncidencia> valuesIncidenciaTable;

    private  String nome;
    private String tipoIncidencia;
    private String localizadaEm;
    private  String urgencia;
    private String impacto;
    private String prioridade;
    private String otCorrectora;
    private String accCorrectora;
    private String tareaCausante;
    private String description;
    private Date dataIncio;
    private Date dataFim;
    private Date dataCentro;


    public List<FenixIncidencia> getValuesIncidenciaTable() {
        return valuesIncidenciaTable;
    }

    public void setValuesIncidenciaTable(List<FenixIncidencia> valuesIncidenciaTable) {
        this.valuesIncidenciaTable = valuesIncidenciaTable;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getLocalizadaEm() {
        return localizadaEm;
    }

    public void setLocalizadaEm(String localizadaEm) {
        this.localizadaEm = localizadaEm;
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

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getOtCorrectora() {
        return otCorrectora;
    }

    public void setOtCorrectora(String otCorrectora) {
        this.otCorrectora = otCorrectora;
    }

    public String getAccCorrectora() {
        return accCorrectora;
    }

    public void setAccCorrectora(String accCorrectora) {
        this.accCorrectora = accCorrectora;
    }

    public String getTareaCausante() {
        return tareaCausante;
    }

    public void setTareaCausante(String tareaCausante) {
        this.tareaCausante = tareaCausante;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(Date dataIncio) {
        this.dataIncio = dataIncio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataCentro() {
        return dataCentro;
    }

    public void setDataCentro(Date dataCentro) {
        this.dataCentro = dataCentro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List getValuesAccTable() {
        return valuesAccTable;
    }

    public void setValuesAccTable(List valuesAccTable) {
        this.valuesAccTable = valuesAccTable;
    }

    public String getIdAcc() {
        return idAcc;
    }

    public void setIdAcc(String idAcc) {
        this.idAcc = idAcc;
    }
}
