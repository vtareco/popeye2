package net.dms.fsync.synchronizer.fenix.entities;

import java.util.ArrayList;
import java.util.List;

public class FenixPeticion {
    private Long id;

    List<FenixAcc> accList = new ArrayList<>();

    public List<FenixAcc> getAccList() {
        return accList;
    }

    public void setAccList(List<FenixAcc> accList) {
        this.accList = accList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FenixAcc searchAcc(Long id){
        if (id == null){
            return null;
        }
        return accList.stream().filter(a -> id.equals(a.getIdAcc())).findFirst().orElse(null);
    }
}
