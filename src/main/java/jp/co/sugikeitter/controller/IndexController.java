package jp.co.sugikeitter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author sugikeitter
 *
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
    public String index() {
        logger.info("index");
        return "index";
    }
}
