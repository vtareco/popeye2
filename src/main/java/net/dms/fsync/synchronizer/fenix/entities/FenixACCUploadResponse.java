package net.dms.fsync.synchronizer.fenix.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminanos on 21/04/2017.
 */
public class FenixACCUploadResponse {

    private List<FenixACCUploadResponseItem> items = new ArrayList();

    public List<FenixACCUploadResponseItem> getItems() {
        return items;
    }

    public void setItems(List<FenixACCUploadResponseItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "FenixACCUploadResponse{" +
                "items=" + items +
                '}';
    }
}
