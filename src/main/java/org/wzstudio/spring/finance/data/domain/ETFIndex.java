package org.wzstudio.spring.finance.data.domain;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.wzstudio.spring.finance.data.dao.ETFIndexRepository;

import java.util.List;
import java.util.Optional;

/**
 * ETF(Exchange Traded Funds) index basic data.
 */
@Configurable(dependencyCheck = true)
public class ETFIndex {

    @Autowired
    private ETFIndexRepository indexRepository;

    /**
     * The database primary key for the index
     */
    public final int id;

    /**
     * The date on which the index is created, e.g. 13-Nov-2007
     */
    public final LocalDate inceptionDate;

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
     * The source where the index info is downloaded
     */
    public final ETFIndexConfig config;

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

    public ETFIndex(int id,
                    LocalDate inceptionDate,
                    String assetClass,
                    String name,
                    String exchangeMarket,
                    ETFIndexConfig config,
                    ETFIndexMoneyNetAssets netAssets,
                    ETFIndexSharesOutStanding sharesOutstanding,
                    ETFIndexNumberOfHoldings numberOfHoldings,
                    ETFIndexMoneyClosingPrice closingPrice,
                    Optional<ETFIndexRatio> peRatio,
                    Optional<ETFIndexRatio> pbRatio,
                    ETFIndexRatio distributionYield) {
        this.id = id;
        this.inceptionDate = inceptionDate;
        this.assetClass = assetClass;
        this.name = name;
        this.exchangeMarket = exchangeMarket;

        this.config = config;

        this.netAssets = netAssets;
        this.sharesOutstanding = sharesOutstanding;
        this.numberOfHoldings = numberOfHoldings;
        this.closingPrice = closingPrice;
        this.peRatio = peRatio;
        this.pbRatio = pbRatio;
        this.distributionYield = distributionYield;
    }

    @Override
    public String toString() {
        return String.format("Index name: %s\n" +
                        "Asset class: %s\n" +
                        "Inception date: %s\n" +
                        "Exchange market: %s\n" +
                        "Major country exposure: %s",
                name, assetClass, inceptionDate, exchangeMarket, config.geographicExposure);
    }

    public List<ETFIndexRatio> peRatios() {
        return indexRepository.findPERatios(this.id);
    }

    public List<ETFIndexRatio> pbRatios() {
        return indexRepository.findPBRatios(this.id);
    }

    public List<ETFIndexRatio> distributionYields() {
        return indexRepository.findDistributionYields(this.id);
    }

    public List<ETFIndexNetAssets> netAssetses() {
        return indexRepository.findNetAssetses(this.id);
    }

    public List<ETFIndexSharesOutStanding> sharesOutstandings() {
        return indexRepository.findSharesOutstandings(this.id);
    }

    public List<ETFIndexNumberOfHoldings> numberOfHoldingses() {
        return indexRepository.findNumberOfHoldingses(this.id);
    }

    public List<ETFIndexClosingPrice> closingPrices() {
        return indexRepository.findClosingPrices(this.id);
    }
}
