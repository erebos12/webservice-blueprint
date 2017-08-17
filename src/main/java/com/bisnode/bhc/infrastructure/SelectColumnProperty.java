package com.bisnode.bhc.infrastructure;

import java.util.Collection;

public class SelectColumnProperty {

    private String columnName;
    private Collection<?> filterList;

    public SelectColumnProperty(String columnName, Collection<?> filterList) {
        this.columnName = columnName;
        this.filterList = filterList;
    }

    public String getColumnName() {
        return columnName;
    }

    public Collection<?> getFilterList() {
        return filterList;
    }
}
