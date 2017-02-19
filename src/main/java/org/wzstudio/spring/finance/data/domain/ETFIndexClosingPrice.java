package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class ETFIndexClosingPrice extends DatedData<BigDecimal> {

    public ETFIndexClosingPrice(BigDecimal data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexClosingPrice of(BigDecimal data, LocalDate recordDate) {
        return new ETFIndexClosingPrice(data, recordDate);
    }
}
