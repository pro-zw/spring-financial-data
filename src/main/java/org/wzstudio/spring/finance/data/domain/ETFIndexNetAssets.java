package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class ETFIndexNetAssets extends DatedData<BigDecimal> {

    public ETFIndexNetAssets(BigDecimal data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexNetAssets of(BigDecimal data, LocalDate recordDate) {
        return new ETFIndexNetAssets(data, recordDate);
    }
}
