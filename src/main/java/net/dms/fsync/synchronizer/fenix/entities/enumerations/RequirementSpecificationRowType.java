package net.dms.fsync.synchronizer.fenix.entities.enumerations;

/**
 * Created by dminanos on 09/11/2018.
 */
public enum RequirementSpecificationRowType {
    CODIGO,
    DESCRIPTION,
    STORY_POINTS,
    HOURS;

    public int getColPosition(){
        return this.ordinal();
    }
}
