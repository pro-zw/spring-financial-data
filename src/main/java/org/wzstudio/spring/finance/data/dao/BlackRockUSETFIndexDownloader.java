package org.wzstudio.spring.finance.data.dao;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.wzstudio.spring.finance.data.domain.*;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Optional;

/**
 * The iShare US website etfs info downloader
 */
@Component
@Qualifier("blackRockUSETFIndexDownloader")
public class BlackRockUSETFIndexDownloader implements ETFIndexDownloader {
    final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
    final NumberFormat numberFormat = NumberFormat.getNumberInstance(java.util.Locale.US);

    @Override
    public Optional<ETFIndex> download(ETFIndexConfig config) throws IOException, ParseException {
        if (config.type != ETFIndexConfigType.ISHARE_US) {
            throw new IllegalArgumentException("Invalid index info url for " + this.getClass().getName());
        }

        final Document indexDoc = Jsoup.connect(config.url).get();
        final Element keyFactsElem = indexDoc.select("#keyFundFacts").first();
        final Element riskElem = indexDoc.select("#fundamentalsAndRisk").first();

        // Basic ETF index info
        String name = indexDoc.select("h1.product-title").first().text();
        LocalDate inceptionDate = dateTimeFormatter.parseLocalDate(
                keyFactsElem.select("div.col-inceptionDate span.data").first().text());
        String assetClass = keyFactsElem.select("div.col-assetClass span.data").first().text();
        String exchangeMarket = keyFactsElem.select("div.col-exchange span.data").first().text();

        // Historical ETF index facts
        // Net assets
        Money netAssets =  Money.of(
                CurrencyUnit.USD,
                numberFormat.parse(keyFactsElem.select("div.col-totalNetAssets span.data")
                        .first().text().replace("$", "")).doubleValue()
        );
        LocalDate netAssetsDate = dateTimeFormatter.parseLocalDate(
                keyFactsElem.select("div.col-totalNetAssets span.as-of-date")
                        .first().text().replace("as of ", ""));
        ETFIndexMoneyNetAssets latestNetAssets =
                ETFIndexMoneyNetAssets.of(netAssets, netAssetsDate);

        // Shares outstanding
        long sharesOutstanding = numberFormat.parse(
                keyFactsElem.select("div.col-sharesOutstanding span.data").first().text()).longValue();
        LocalDate sharesOutstandingDate = dateTimeFormatter.parseLocalDate(
                keyFactsElem.select("div.col-sharesOutstanding span.as-of-date")
                        .first().text().replace("as of ", ""));
        ETFIndexSharesOutStanding latestSharesOutstanding =
                ETFIndexSharesOutStanding.of(sharesOutstanding, sharesOutstandingDate);

        // Number of holdings
        int numberOfHoldings = numberFormat.parse(
                keyFactsElem.select("div.col-numHoldings span.data").first().text()).intValue();
        LocalDate numberOfHoldingsDate = dateTimeFormatter.parseLocalDate(
                keyFactsElem.select("div.col-numHoldings span.as-of-date")
                        .first().text().replace("as of ", ""));
        ETFIndexNumberOfHoldings latestNumberOfHoldings =
                ETFIndexNumberOfHoldings.of(numberOfHoldings, numberOfHoldingsDate);

        // Closing price
        Money closingPrice =  Money.of(
                CurrencyUnit.USD,
                numberFormat.parse(keyFactsElem.select("div.col-closingPrice span.data")
                        .first().text()).doubleValue()
        );
        LocalDate closingPriceDate = dateTimeFormatter.parseLocalDate(
                keyFactsElem.select("div.col-closingPrice span.as-of-date")
                        .first().text().replace("as of ", ""));
        ETFIndexMoneyClosingPrice latestClosingPrice =
                ETFIndexMoneyClosingPrice.of(closingPrice, closingPriceDate);

        // PE ratio
        Elements peRatioElem = riskElem.select("div.col-priceEarnings span.data");
        Optional<ETFIndexRatio> latestPERatio = Optional.empty();
        if (!peRatioElem.isEmpty()) {
            latestPERatio = Optional.of(ETFIndexRatio.of(
                    numberFormat.parse(peRatioElem.first().text()).floatValue(),
                    dateTimeFormatter.parseLocalDate(
                            riskElem.select("div.col-priceEarnings span.as-of-date").first().text().replace("as of ", ""))
            ));
        }

        // PB ratio
        Elements pbRatioElem = riskElem.select("div.col-priceBook span.data");
        Optional<ETFIndexRatio> latestPBRatio = Optional.empty();
        if (!pbRatioElem.isEmpty()) {
            latestPBRatio = Optional.of(ETFIndexRatio.of(
                    numberFormat.parse(pbRatioElem.first().text()).floatValue(),
                    dateTimeFormatter.parseLocalDate(
                            riskElem.select("div.col-priceBook span.as-of-date").first().text().replace("as of ", ""))
            ));
        }

        // Distribution yield
        float distributionYield = numberFormat.parse(
                riskElem.select("div.col-distYield span.data")
                        .first().text().replace("%", "")).floatValue();
        LocalDate distributionYieldDate = dateTimeFormatter.parseLocalDate(
                riskElem.select("div.col-distYield span.as-of-date")
                        .first().text().replace("as of ", ""));
        ETFIndexRatio latestDistributionYield =
                ETFIndexRatio.of(distributionYield, distributionYieldDate);

        return Optional.of(new ETFIndex(-1,
                inceptionDate, assetClass, name, exchangeMarket, config,
                latestNetAssets, latestSharesOutstanding,
                latestNumberOfHoldings, latestClosingPrice,
                latestPERatio, latestPBRatio, latestDistributionYield));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
