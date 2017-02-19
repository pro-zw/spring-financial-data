package org.wzstudio.spring.finance.data.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.wzstudio.spring.finance.data.config.SpringRootConfig;
import org.wzstudio.spring.finance.data.config.SpringWebConfig;
import org.wzstudio.spring.finance.data.domain.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, SpringRootConfig.class})
public class ETFIndexRepositoryTest {

    @Autowired
    private ETFIndexRepository indexRepository;

    @Autowired
    private ETFIndexConfigRepository indexConfigRepository;

    @Autowired
    @Qualifier("blackRockUSETFIndexDownloader")
    private ETFIndexDownloader indexDownloader;

    @Test
    public void testSave() throws IOException, ParseException {
        ETFIndexConfig indexConfig = indexConfigRepository.findAll().get(0);
        ETFIndex index = indexDownloader.download(indexConfig).get();

        int id = indexRepository.save(index);
        assertTrue(id > 0);
    }

    @Test
    public void testFindById() {
        ETFIndex index =
                indexRepository.findById(1);
        assertEquals(index.name, "iShares MSCI Japan ETF");
    }

    @Test
    public void testFindAll() {
        List<ETFIndex> indexes =
                indexRepository.findAll();
        assertFalse(indexes.isEmpty());
        indexes.stream().forEach(index -> assertTrue(index.peRatio.isPresent()));
        indexes.stream().forEach(index -> assertTrue(index.pbRatio.isPresent()));
    }

    @Test
    public void testFindPERatios() {
        List<ETFIndexRatio> indexPERatios =
                indexRepository.findPERatios(1);
        assertTrue(indexPERatios.size() > 0);
    }

    @Test
    public void testFindPBRatios() {
        List<ETFIndexRatio> indexPBRatios =
                indexRepository.findPBRatios(1);
        assertTrue(indexPBRatios.size() > 0);
    }

    @Test
    public void testFindDistributionYields() {
        List<ETFIndexRatio> indexDistributionYields =
                indexRepository.findDistributionYields(1);
        assertTrue(indexDistributionYields.size() > 0);
    }

    @Test
    public void testFindNetAssetses() {
        List<ETFIndexNetAssets> indexNetAssetses =
                indexRepository.findNetAssetses(1);
        assertTrue(indexNetAssetses.size() > 0);
    }

    @Test
    public void testFindSharesOutstandings() {
        List<ETFIndexSharesOutStanding> indexSharesOutstandings =
                indexRepository.findSharesOutstandings(1);
        assertTrue(indexSharesOutstandings.size() > 0);
    }

    @Test
    public void testFindNumberOfHoldingses() {
        List<ETFIndexNumberOfHoldings> indexNumberOfHoldingses =
                indexRepository.findNumberOfHoldingses(1);
        assertTrue(indexNumberOfHoldingses.size() > 0);
    }

    @Test
    public void testFindClosingPrices() {
        List<ETFIndexClosingPrice> indexClosingPrices =
                indexRepository.findClosingPrices(1);
        assertTrue(indexClosingPrices.size() > 0);
    }
}
