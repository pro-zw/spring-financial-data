package org.wzstudio.spring.finance.data.dao;

import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.domain.ETFIndexConfig;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

/**
 * Interface for the task that downloads index info from the Internet.
 */
public interface ETFIndexDownloader {
    Optional<ETFIndex> download(ETFIndexConfig config) throws IOException, ParseException;
}
