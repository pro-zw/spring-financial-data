package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

public class ETFIndexNumberOfHoldings extends DatedData<Integer> {

    public ETFIndexNumberOfHoldings(Integer data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexNumberOfHoldings of(Integer data, LocalDate recordDate) {
        return new ETFIndexNumberOfHoldings(data, recordDate);
    }
}
