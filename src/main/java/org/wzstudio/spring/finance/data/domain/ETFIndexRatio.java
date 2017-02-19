package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;

public class ETFIndexRatio extends DatedData<Float> {

    public ETFIndexRatio(Float data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexRatio of(Float data, LocalDate recordDate) {
        return new ETFIndexRatio(data, recordDate);
    }
}
