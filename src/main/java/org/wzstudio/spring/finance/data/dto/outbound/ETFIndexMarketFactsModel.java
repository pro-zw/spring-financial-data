package org.wzstudio.spring.finance.data.dto.outbound;

import org.joda.time.LocalDate;
import org.wzstudio.spring.finance.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

/**
 * ETF index market facts, e.g. closing prices & shares outstanding
 */
public class ETFIndexMarketFactsModel {
    /**
     * The index outline
     */
    public final ETFIndexModel index;

    /**
     * The net assets date labels
     */
    public final List<LocalDate> netAssetsDates;

    /**
     * The net assetses
     */
    public final List<BigDecimal> netAssetses;

    /**
     * The shares outstanding date labels
     */
    public final List<LocalDate> sharesOutstandingDates;

    /**
     * The share outstandings
     */
    public final List<Long> sharesOutstandings;

    /**
     * The number of holdings date labels
     */
    public final List<LocalDate> numberOfHoldingsDates;

    /**
     * The number of holdingses
     */
    public final List<Integer> numberOfHoldingses;

    /**
     * The closing price date labels
     */
    public final List<LocalDate> closingPriceDates;

    /**
     * The closing prices
     */
    public final List<BigDecimal> closingPrices;

    /**
     * Constructor
     */
    public ETFIndexMarketFactsModel(ETFIndex index) {
        this.index = new ETFIndexModel(index);

        List<ETFIndexNetAssets> netAssetses = index.netAssetses();
        this.netAssetsDates = unmodifiableList(
                netAssetses.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.netAssetses = unmodifiableList(
                netAssetses.stream().map(data -> data.data).collect(Collectors.toList())
        );

        List<ETFIndexSharesOutStanding> sharesOutstandings = index.sharesOutstandings();
        this.sharesOutstandingDates = unmodifiableList(
                sharesOutstandings.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.sharesOutstandings = unmodifiableList(
                sharesOutstandings.stream().map(data -> data.data).collect(Collectors.toList())
        );

        List<ETFIndexNumberOfHoldings> numberOfHoldingses = index.numberOfHoldingses();
        this.numberOfHoldingsDates = unmodifiableList(
                numberOfHoldingses.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.numberOfHoldingses = unmodifiableList(
                numberOfHoldingses.stream().map(data -> data.data).collect(Collectors.toList())
        );

        List<ETFIndexClosingPrice> closingPrices = index.closingPrices();
        this.closingPriceDates = unmodifiableList(
                closingPrices.stream().map(data -> data.recordDate).collect(Collectors.toList())
        );
        this.closingPrices = unmodifiableList(
                closingPrices.stream().map(data -> data.data).collect(Collectors.toList())
        );
    }
}
