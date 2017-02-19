package org.wzstudio.spring.finance.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfig;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ETFIndexConfigRepository {

    private JdbcTemplate jdbcTemplate;

    public ETFIndexConfigRepository(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ETFIndexConfig> findAll() {
         List<ETFIndexConfig> configs = this.jdbcTemplate.query(
                "SELECT id, url, geographic_exposure FROM etf_index_config WHERE suspended IS FALSE",
                (rs, rowNum) -> {
                    return new ETFIndexConfig(
                            rs.getInt("id"),
                            rs.getString("url"),
                            rs.getString("geographic_exposure")
                    );

                }
        );
        return java.util.Collections.unmodifiableList(configs);
    }
}
