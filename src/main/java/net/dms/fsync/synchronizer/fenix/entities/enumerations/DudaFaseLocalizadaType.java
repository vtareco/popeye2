package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by vics on 05/02/2019.
 */
public enum DudaFaseLocalizadaType implements VisualizableType{
    DF("DF"),
    PR_DF("PR_DF"),
    DT_CC("DT/CC"),
    PR_DT_CC("PR_DT/CC"),
    CO("CO"),
    PR_CO("PR_CO"),
    PRU_INT("PRU_INT"),
    PRU_FUNC("PRU_FUNC"),
    ENTR("ENTR");


    private String description;

    DudaFaseLocalizadaType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
