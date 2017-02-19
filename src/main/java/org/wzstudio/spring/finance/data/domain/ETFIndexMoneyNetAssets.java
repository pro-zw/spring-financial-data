package org.wzstudio.spring.finance.data.domain;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class ETFIndexMoneyNetAssets extends DatedData<Money> {

    public ETFIndexMoneyNetAssets(Money data, LocalDate recordDate) {
        super(data, recordDate);
    }

    public static ETFIndexMoneyNetAssets of(Money data, LocalDate recordDate) {
        return new ETFIndexMoneyNetAssets(data, recordDate);
    }

    public static ETFIndexMoneyNetAssets of(BigDecimal amount,
                                            String currencyCode,
                                            LocalDate recordDate) {
        return new ETFIndexMoneyNetAssets(
                Money.of(
                        CurrencyUnit.of(currencyCode),
                        amount.setScale(2, BigDecimal.ROUND_HALF_UP)
                ),
                recordDate
        );
    }
}
