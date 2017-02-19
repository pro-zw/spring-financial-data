package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

/**
 * Financial data on special date.
 */
public class DatedData<T> {
    public final T data;
    public final LocalDate recordDate;

    public DatedData(T data, LocalDate recordDate) {
        this.data = data;
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
