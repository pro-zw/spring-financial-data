package org.wzstudio.spring.finance.data.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.wzstudio.spring.finance.data.config.SpringRootConfig;
import org.wzstudio.spring.finance.data.config.SpringWebConfig;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfig;

import java.io.IOException;
import java.text.ParseException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, SpringRootConfig.class})
public class BlackRockUSETFIndexDownloaderTest {

    @Autowired
    @Qualifier("blackRockUSETFIndexDownloader")
    private ETFIndexDownloader etfIndexDownloader;

    @Test
    public void testDownload() throws IOException, ParseException {
        // Equity
        /*
        ETFIndexConfig indexConfig = new ETFIndexConfig(
                -1,
                "https://www.ishares.com/us/products/239665/ishares-msci-japan-etf",
                "Japan");
        ETFIndex index = etfIndexDownloader.download(indexConfig).get();

        assertEquals("iShares MSCI Japan ETF", index.name);
        assertEquals("Equity", index.assetClass);
        assertTrue(index.peRatio.isPresent());
        assertTrue(index.pbRatio.isPresent());
        */

        // Fixed income
        /*
        indexConfig = new ETFIndexConfig(
                -1,
                "https://www.ishares.com/us/products/239458/ishares-core-total-us-bond-market-etf",
                "United States"
        );
        index = etfIndexDownloader.download(indexConfig).get();
        assertEquals("iShares Core U.S. Aggregate Bond ETF", index.name);
        assertEquals("Fixed Income", index.assetClass);
        assertFalse(index.peRatio.isPresent());
        assertFalse(index.pbRatio.isPresent());
        */
    }
}
