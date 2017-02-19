package org.wzstudio.spring.finance.data.web.formatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.service.ETFIndexService;

import java.text.ParseException;
import java.util.Locale;

public class ETFIndexFormatter implements Formatter<ETFIndex> {

    @Autowired
    ETFIndexService indexService;

    @Override
    public ETFIndex parse(String text, Locale locale) throws ParseException {
        final Integer indexId = Integer.valueOf(text);
        return this.indexService.findById(indexId);
    }

    @Override
    public String print(ETFIndex object, Locale locale) {
        return (object != null ? Integer.toString(object.id) : "");
    }
}
