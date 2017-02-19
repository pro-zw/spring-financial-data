package org.wzstudio.spring.finance.data.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.wzstudio.spring.finance.data.config.SpringRootConfig;
import org.wzstudio.spring.finance.data.config.SpringWebConfig;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfig;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, SpringRootConfig.class})
public class ETFIndexConfigRepositoryTest {

    @Autowired
    ETFIndexConfigRepository indexConfigRepository;

    @Test
    public void testFindAll() {
        List<ETFIndexConfig> configs = indexConfigRepository.findAll();
        assertFalse(configs.isEmpty());
    }
}
