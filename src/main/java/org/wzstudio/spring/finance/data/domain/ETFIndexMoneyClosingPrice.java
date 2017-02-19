package org.wzstudio.spring.finance.data.domain;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class ETFIndexMoneyClosingPrice extends DatedData<Money> {

    public ETFIndexMoneyClosingPrice(Money data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexMoneyClosingPrice of(Money data, LocalDate recordDate) {
        return new ETFIndexMoneyClosingPrice(data, recordDate);
    }

    public static ETFIndexMoneyClosingPrice of(BigDecimal amount,
                                               String currencyCode,
                                               LocalDate recordDate) {
        return new ETFIndexMoneyClosingPrice(
                Money.of(
                        CurrencyUnit.of(currencyCode),
                        amount.setScale(2, BigDecimal.ROUND_HALF_UP)
                ),
                recordDate
        );
    }
}
