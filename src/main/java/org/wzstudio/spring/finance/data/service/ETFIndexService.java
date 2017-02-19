package org.wzstudio.spring.finance.data.service;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.wzstudio.spring.finance.data.dao.ETFIndexConfigRepository;
import org.wzstudio.spring.finance.data.dao.ETFIndexDownloader;
import org.wzstudio.spring.finance.data.dao.ETFIndexRepository;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfig;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfigType;
import org.wzstudio.spring.finance.data.dto.outbound.ETFIndexMarketFactsModel;
import org.wzstudio.spring.finance.data.dto.outbound.ETFIndexModel;
import org.wzstudio.spring.finance.data.dto.outbound.ETFIndexValuationModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ETFIndexService {

    @Autowired
    @Qualifier("blackRockUSETFIndexDownloader")
    private ETFIndexDownloader blackRockUSETFIndexDownloader;

    @Autowired
    private ETFIndexConfigRepository indexConfigRepository;

    @Autowired
    private ETFIndexRepository indexRepository;

    @Autowired
    private Logger logger;

    public void downloadAll() {
        Map<ETFIndexConfigType, List<ETFIndexConfig>> configsMap =
                indexConfigRepository.findAll().stream()
                        .collect(
                                Collectors.groupingBy(config -> config.type)
                        );

        configsMap.forEach((type, configs) -> {
            if (type == ETFIndexConfigType.ISHARE_US) {
                downloadAll(blackRockUSETFIndexDownloader, configs);
            }
        });
    }

    public void save(ETFIndex index) {
        indexRepository.save(index);
    }

    public ETFIndex findById(int indexId) {
        return indexRepository.findById(indexId);
    }

    public List<ETFIndex> findAll() {
        return indexRepository.findAll();
    }

    public ETFIndexModel indexModel(ETFIndex index) {
        return new ETFIndexModel(index);
    }

    public ETFIndexValuationModel indexValuationModel(ETFIndex index) {
        return new ETFIndexValuationModel(index);
    }

    public ETFIndexMarketFactsModel indexMarketFactsModel(ETFIndex index) {
        return new ETFIndexMarketFactsModel(index);
    }

    private void downloadAll(ETFIndexDownloader downloader,
                             List<ETFIndexConfig> configs) {
        for (ETFIndexConfig config : configs) {
            try {
                Optional<ETFIndex> etfIndexOptional = downloader.download(config);
                if (etfIndexOptional.isPresent()) {
                    save(etfIndexOptional.get());
                }
            } catch (Exception ex) {
                logger.error(String.format("%s failed to download an ETF index from the url %s. The remaining downloading procedures are all interrupted. Exception: %s", downloader, config.url, ex));
                break;
            }
        }

        logger.info(String.format("%s downloaded all ETF indexes successfully.", downloader));
    }
}
