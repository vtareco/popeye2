package net.dms.fsync.synchronizer.LocalVariables.entities;

public class JenixSettings {

    ApplicationProperties ap;
    UserChange uc;

    public JenixSettings(ApplicationProperties ap, UserChange uc) {
        this.ap = ap;
        this.uc = uc;
    }
    public JenixSettings() {

    }


    public ApplicationProperties getAp() {
        return ap;
    }

    public void setAp(ApplicationProperties ap) {
        this.ap = ap;
    }

    public UserChange getUc() {
        return uc;
    }

    public void setUc(UserChange uc) {
        this.uc = uc;
    }
}
