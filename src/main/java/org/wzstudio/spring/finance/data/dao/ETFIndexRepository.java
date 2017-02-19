package org.wzstudio.spring.finance.data.dao;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.wzstudio.spring.finance.data.domain.*;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ETFIndexRepository {
    private JdbcTemplate jdbcTemplate;

    public ETFIndexRepository(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int save(final ETFIndex index) {
        // Save basic index info
        int indexId = jdbcTemplate.queryForObject(
                "INSERT INTO etf_index (etf_index_config_id, inception_date, asset_class, name, exchange_market) VALUES (?, ?, ?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_etf_index_config_id_ukey DO UPDATE SET name = EXCLUDED.name RETURNING id",
                Integer.class,
                index.config.id,
                new java.sql.Date(index.inceptionDate.toDate().getTime()),
                index.assetClass,
                index.name,
                index.exchangeMarket
        );

        // Save the latest index facts we care
        saveLatestNetAssets(indexId, index.netAssets);
        saveLatestSharesOutstanding(indexId, index.sharesOutstanding);
        saveLatestNumberOfHoldings(indexId, index.numberOfHoldings);
        saveLatestClosingPrice(indexId, index.closingPrice);
        if (index.peRatio.isPresent()) {
            saveLatestPERatio(indexId, index.peRatio.get());
        }
        if (index.pbRatio.isPresent()) {
            saveLatestPBRatio(indexId, index.pbRatio.get());
        }
        saveLatestDistributionYield(indexId, index.distributionYield);

        return indexId;
    }

    public ETFIndex findById(int indexId) {
        return jdbcTemplate.queryForObject(
                "SELECT ei.*, eic.url, eic.geographic_exposure FROM etf_index AS ei INNER JOIN etf_index_config AS eic ON ei.etf_index_config_id = eic.id WHERE ei.id = ?",
                new ETFIndexRowMapper(),
                indexId
        );
    }

    public List<ETFIndex> findAll() {
        List<ETFIndex> indexes =
                jdbcTemplate.query(
                        "SELECT ei.*, eic.url, eic.geographic_exposure FROM etf_index AS ei INNER JOIN etf_index_config AS eic ON ei.etf_index_config_id = eic.id",
                        new ETFIndexRowMapper()
                );

        return java.util.Collections.unmodifiableList(indexes);
    }

    public List<ETFIndexRatio> findPERatios(int indexId) {
        List<ETFIndexRatio> indexPERatios = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_pe_ratio WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexRatio.of(
                            rs.getFloat("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexPERatios);
    }

    public List<ETFIndexRatio> findPBRatios(int indexId) {
        List<ETFIndexRatio> indexPBRatios = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_pb_ratio WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexRatio.of(
                            rs.getFloat("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexPBRatios);
    }

    public List<ETFIndexRatio> findDistributionYields(int indexId) {
        List<ETFIndexRatio> indexDistributionYields = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_distribution_yield WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexRatio.of(
                            rs.getFloat("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexDistributionYields);
    }

    public List<ETFIndexNetAssets> findNetAssetses(int indexId) {
        List<ETFIndexNetAssets> indexNetAssetses = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_net_assets WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexNetAssets.of(
                            rs.getBigDecimal("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexNetAssetses);
    }

    public List<ETFIndexSharesOutStanding> findSharesOutstandings(int indexId) {
        List<ETFIndexSharesOutStanding> indexSharesOutstandings = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_shares_outstanding WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexSharesOutStanding.of(
                            rs.getLong("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexSharesOutstandings);
    }

    public List<ETFIndexNumberOfHoldings> findNumberOfHoldingses(int indexId) {
        List<ETFIndexNumberOfHoldings> indexNumberOfHoldingses = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_number_of_holdings WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexNumberOfHoldings.of(
                            rs.getInt("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexNumberOfHoldingses);
    }

    public List<ETFIndexClosingPrice> findClosingPrices(int indexId) {
        List<ETFIndexClosingPrice> indexClosingPrices = jdbcTemplate.query(
                "SELECT record_date, data FROM etf_index_closing_price WHERE etf_index_id = ? ORDER BY record_date ASC",
                (rs, rowNum) -> {
                    return ETFIndexClosingPrice.of(
                            rs.getBigDecimal("data"),
                            new LocalDate(rs.getDate("record_date"))
                    );
                },
                indexId
        );

        return java.util.Collections.unmodifiableList(indexClosingPrices);
    }

    private void saveLatestNetAssets(int indexId,
                                     final ETFIndexMoneyNetAssets netAssets) {
        java.sql.Date recordDate = new java.sql.Date(netAssets.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_net_assets (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_net_assets_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                netAssets.data.getAmount()
        );

        jdbcTemplate.update("UPDATE etf_index SET net_assets = ?, net_assets_currency_code = ?::currency_code, net_assets_record_date = ? WHERE id = ?",
                netAssets.data.getAmount(),
                netAssets.data.getCurrencyUnit().getCode(),
                recordDate,
                indexId
        );
    }


    private void saveLatestSharesOutstanding(int indexId,
                                             ETFIndexSharesOutStanding sharesOutstanding) {
        java.sql.Date recordDate = new java.sql.Date(sharesOutstanding.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_shares_outstanding (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_shares_outstanding_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                sharesOutstanding.data
        );

        jdbcTemplate.update("UPDATE etf_index SET shares_outstanding = ?, shares_outstanding_record_date = ? WHERE id = ?",
                sharesOutstanding.data,
                recordDate,
                indexId
        );
    }

    private void saveLatestNumberOfHoldings(int indexId,
                                            ETFIndexNumberOfHoldings numberOfHoldings) {
        java.sql.Date recordDate = new java.sql.Date(numberOfHoldings.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_number_of_holdings (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_number_of_holdings_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                numberOfHoldings.data
        );

        jdbcTemplate.update("UPDATE etf_index SET number_of_holdings = ?, number_of_holdings_record_date = ? WHERE id = ?",
                numberOfHoldings.data,
                recordDate,
                indexId
        );
    }

    private void saveLatestClosingPrice(int indexId,
                                        ETFIndexMoneyClosingPrice closingPrice) {
        java.sql.Date recordDate = new java.sql.Date(closingPrice.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_closing_price (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_closing_price_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                closingPrice.data.getAmount()
        );

        jdbcTemplate.update("UPDATE etf_index SET closing_price = ?, closing_price_currency_code = ?::currency_code, closing_price_record_date = ? WHERE id = ?",
                closingPrice.data.getAmount(),
                closingPrice.data.getCurrencyUnit().getCode(),
                recordDate,
                indexId);
    }

    private void saveLatestPERatio(int indexId,
                                   ETFIndexRatio peRatio) {
        java.sql.Date recordDate = new java.sql.Date(peRatio.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_pe_ratio (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_pe_ratio_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                peRatio.data
        );

        jdbcTemplate.update("UPDATE etf_index SET pe_ratio = ?, pe_ratio_record_date = ? WHERE id = ?",
                peRatio.data,
                recordDate,
                indexId
        );
    }

    private void saveLatestPBRatio(int indexId,
                                   ETFIndexRatio pbRatio) {
        java.sql.Date recordDate = new java.sql.Date(pbRatio.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_pb_ratio (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_pb_ratio_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                pbRatio.data
        );

        jdbcTemplate.update("UPDATE etf_index SET pb_ratio = ?, pb_ratio_record_date = ? WHERE id = ?",
                pbRatio.data,
                recordDate,
                indexId
        );
    }

    private void saveLatestDistributionYield(int indexId,
                                             ETFIndexRatio distributionYield) {
        java.sql.Date recordDate = new java.sql.Date(distributionYield.recordDate.toDate().getTime());

        jdbcTemplate.update("INSERT INTO etf_index_distribution_yield (etf_index_id, record_date, data) VALUES (?, ?, ?) ON CONFLICT ON CONSTRAINT etf_index_distribution_yield_pkey DO UPDATE SET data = EXCLUDED.data",
                indexId,
                recordDate,
                distributionYield.data
        );

        jdbcTemplate.update("UPDATE etf_index SET distribution_yield = ?, distribution_yield_record_date = ? WHERE id = ?",
                distributionYield.data,
                recordDate,
                indexId
        );
    }
}
