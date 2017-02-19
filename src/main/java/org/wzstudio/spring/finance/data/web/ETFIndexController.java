package org.wzstudio.spring.finance.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wzstudio.spring.finance.data.domain.ETFIndex;
import org.wzstudio.spring.finance.data.service.ETFIndexService;

import java.util.List;

@Controller
@RequestMapping("/etfs")
public class ETFIndexController {

    @Autowired
    ETFIndexService indexService;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        List<ETFIndex> indexes =
                indexService.findAll();
        model.addAttribute("indexes", indexes);

        return "etfs-home";
    }

    @RequestMapping(method = RequestMethod.GET,
                    path = "/{indexId}")
    public String detail(@ModelAttribute("indexId") ETFIndex index,
                         Model model) {
        model.addAttribute("index", index);
        return "etf-charts";
    }
}
