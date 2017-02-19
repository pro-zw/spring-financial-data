package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

public class ETFIndexSharesOutStanding extends DatedData<Long> {

    public ETFIndexSharesOutStanding(Long data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexSharesOutStanding of(Long data, LocalDate recordDate) {
        return new ETFIndexSharesOutStanding(data, recordDate);
    }
}
