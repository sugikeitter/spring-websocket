package jp.co.sugikeitter.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sugikeitter.repository.UserInfoRepository;
import jp.co.sugikeitter.repository.model.UserInfo;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ChatController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    /**
     * Simply selects the home view to render by returning its name.
     */
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(Model model, Principal principal) {
        logger.info("/chat");

        String userName = principal.getName();
        List<UserInfo> userNameList = userInfoRepository.findAllAccount();

        model.addAttribute("userName", userName);
        model.addAttribute("userNameList", userNameList);

        return "chat";
    }
}
