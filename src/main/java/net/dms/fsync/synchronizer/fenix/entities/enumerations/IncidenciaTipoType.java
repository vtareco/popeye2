package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 23/05/2017.
 */
public enum IncidenciaTipoType implements VisualizableType{
    ERROR_DF("ERROR_DF", new IncidenciaLocalizadaEnType[]{
            IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS}),
    ERROR_DT("ERROR_DT", new IncidenciaLocalizadaEnType[]{
            IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS}),
     ERROR_EJECUCION_PRUEBAS("ERROR_EJECUCION_PRUEBAS", new IncidenciaLocalizadaEnType[]{
            IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS}),
    ERROR_EN_CODIGO("ERROR_EN_CODIGO", new IncidenciaLocalizadaEnType[]{
            IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS, IncidenciaLocalizadaEnType.PEER_REVIEW_CODIGO}),
    ERROR_PLAN_DE_PRUEBAS("ERROR_PLAN_DE_PRUEBAS", new IncidenciaLocalizadaEnType[]{
        IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS}),
    ERROR_ESTANDARES_CODIFICACION("ERROR_ESTANDARES_CODIFICACION", new IncidenciaLocalizadaEnType[]{
                    IncidenciaLocalizadaEnType.PRUEBAS_UNITARIAS, IncidenciaLocalizadaEnType.PEER_REVIEW_CODIGO}),;

    private IncidenciaLocalizadaEnType[] localizadaEnTypes;
    private String description;

    IncidenciaTipoType(String description, IncidenciaLocalizadaEnType[] localizadaEnTypes) {
        this.description = description;
        this.localizadaEnTypes = localizadaEnTypes;
    }

    public String getDescription() {
        return description;
    }
}
