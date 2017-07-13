package com.bisnode.bhc.infrastructure;

import java.util.Collection;

public class SelectColumnCriteria {

    private String columnName;
    private Collection<?> filterList;

    public SelectColumnCriteria(String columnName, Collection<?> filterList) {
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
