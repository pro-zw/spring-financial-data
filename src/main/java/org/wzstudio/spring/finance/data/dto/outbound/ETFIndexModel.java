package org.wzstudio.spring.finance.data.dto.outbound;

import org.wzstudio.spring.finance.data.domain.*;

import java.util.Optional;

/**
 * The ETF index summary information sent to the client, may be used in a ETF index table etc.
 */
public class ETFIndexModel {
    /**
     * The database primary key for the index
     */
    public final int id;

    /**
     * Asset class of the index, e.g. equity or fixed income
     */
    public final String assetClass;

    /**
     * Name of the index, e.g. iShare Asia 50 ETF.
     */
    public final String name;

    /**
     * The exchange market of the index, e.g. ASX
     */
    public final String exchangeMarket;

    /**
     * The net assets of the index
     */
    public final ETFIndexMoneyNetAssets netAssets;

    /**
     * The shares outstanding of the index
     */
    public final ETFIndexSharesOutStanding sharesOutstanding;

    /**
     * The number of holdings of the index
     */
    public final ETFIndexNumberOfHoldings numberOfHoldings;

    /**
     * The closing price of the index
     */
    public final ETFIndexMoneyClosingPrice closingPrice;

    /**
     * The PE ratio of the index
     */
    public final Optional<ETFIndexRatio> peRatio;

    /**
     * The PB ratio of the index
     */
    public final Optional<ETFIndexRatio> pbRatio;

    /**
     * The distribution yield of the index
     */
    public final ETFIndexRatio distributionYield;

    /**
     * Constructor
     */
    public ETFIndexModel(ETFIndex index) {
        this.id = index.id;
        this.assetClass = index.assetClass;
        this.name = index.name;
        this.exchangeMarket = index.exchangeMarket;
        this.netAssets = index.netAssets;
        this.sharesOutstanding = index.sharesOutstanding;
        this.numberOfHoldings = index.numberOfHoldings;
        this.closingPrice = index.closingPrice;
        this.peRatio = index.peRatio;
        this.pbRatio = index.pbRatio;
        this.distributionYield = index.distributionYield;
    }
}
