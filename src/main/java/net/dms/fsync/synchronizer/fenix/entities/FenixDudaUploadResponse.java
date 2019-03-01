package net.dms.fsync.synchronizer.fenix.entities;

import java.util.ArrayList;
import java.util.List;

public class FenixDudaUploadResponse {

	private List<FenixIncidenciaUploadResponseItem> items = new ArrayList();

	public List<FenixIncidenciaUploadResponseItem> getItems() {
		return items;
	}

	public void setItems(List<FenixIncidenciaUploadResponseItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "FenixDudaUploadResponse{" +
			   "items=" + items +
			   '}';
	}
}
