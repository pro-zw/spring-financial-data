package org.wzstudio.spring.finance.data.web;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.dto.outbound.ETFIndexMarketFactsModel;
import org.wzstudio.spring.finance.data.dto.outbound.ETFIndexValuationModel;
import org.wzstudio.spring.finance.data.service.ETFIndexService;

@RestController
@RequestMapping("/api/etfs")
public class ETFIndexRestController {

    @Autowired
    private ETFIndexService indexService;

    @RequestMapping(path = "/{indexId}/valuation",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody
    ETFIndexValuationModel findValuation(@ModelAttribute("indexId") ETFIndex index) {
        return indexService.indexValuationModel(index);
    }

    @RequestMapping(path = "/{indexId}/market-facts",
                    method = RequestMethod.GET,
                    produces = "application/json")
    public @ResponseBody
    ETFIndexMarketFactsModel findMarketFacts(@ModelAttribute("indexId") ETFIndex index) {
        return indexService.indexMarketFactsModel(index);
    }
}
