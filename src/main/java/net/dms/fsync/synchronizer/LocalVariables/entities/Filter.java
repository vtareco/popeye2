package net.dms.fsync.synchronizer.LocalVariables.entities;

import java.util.UUID;

public class Filter {


    private String filterName;
    private String filterQuery;

    public Filter(String filterName, String filterQuery) {

        this.filterName = filterName;
        this.filterQuery = filterQuery;
    }
    public Filter() {


    }


    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterQuery() {
        return filterQuery;
    }

    public void setFilterQuery(String filterQuery) {
        this.filterQuery = filterQuery;
    }
}
