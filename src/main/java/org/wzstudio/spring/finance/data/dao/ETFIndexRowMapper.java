package org.wzstudio.spring.finance.data.dao;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.wzstudio.spring.finance.data.domain.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ETFIndexRowMapper implements RowMapper<ETFIndex> {

    @Override
    public ETFIndex mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ETFIndex(
                rs.getInt("id"),
                new LocalDate(rs.getDate("inception_date")),
                rs.getString("asset_class"),
                rs.getString("name"),
                rs.getString("exchange_market"),
                new ETFIndexConfig(
                        rs.getInt("etf_index_config_id"),
                        rs.getString("url"),
                        rs.getString("geographic_exposure")
                ),
                ETFIndexMoneyNetAssets.of(
                        rs.getBigDecimal("net_assets"),
                        rs.getString("net_assets_currency_code"),
                        new LocalDate(rs.getDate("net_assets_record_date"))
                ),
                ETFIndexSharesOutStanding.of(
                        rs.getLong("shares_outstanding"),
                        new LocalDate(rs.getDate("shares_outstanding_record_date"))
                ),
                ETFIndexNumberOfHoldings.of(
                        rs.getInt("number_of_holdings"),
                        new LocalDate(rs.getDate("number_of_holdings_record_date"))

                ),
                ETFIndexMoneyClosingPrice.of(
                        rs.getBigDecimal("closing_price"),
                        rs.getString("closing_price_currency_code"),
                        new LocalDate(rs.getDate("closing_price_record_date"))
                ),
                Optional.of(
                        ETFIndexRatio.of(
                                rs.getFloat("pe_ratio"),
                                new LocalDate(rs.getDate("pe_ratio_record_date"))
                        )
                ),
                Optional.of(
                        ETFIndexRatio.of(
                                rs.getFloat("pb_ratio"),
                                new LocalDate(rs.getDate("pb_ratio_record_date"))
                        )
                ),
                ETFIndexRatio.of(
                    rs.getFloat("distribution_yield"),
                    new LocalDate(rs.getDate("distribution_yield_record_date"))
                )
        );
    }
}
