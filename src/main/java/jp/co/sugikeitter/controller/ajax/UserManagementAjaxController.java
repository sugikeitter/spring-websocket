package jp.co.sugikeitter.controller.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sugikeitter.repository.UserInfoRepository;
import jp.co.sugikeitter.repository.model.UserInfo;

@RestController
public class UserManagementAjaxController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementAjaxController.class);

    @Autowired
    private PasswordEncoder passwordEncorder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @RequestMapping(value = "/checkUserNameDuplication", method = RequestMethod.POST
            ,consumes=MediaType.APPLICATION_JSON_VALUE
            ,produces=MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public String checkUserNameDuplication(@RequestBody UserInfo userInfo) {
        logger.info("[ajaxRequest: /checkUserNameDuplication, userName: " + userInfo.getUserName() + "]");
        // ユーザ名が登録されているかを確認して、結果を返す
        // TODO トランザクションの設定
        int userCount = userInfoRepository.countUserName(userInfo.getUserName());
        if (userCount == 0) {
            return "\"success\"";
        }
        return "\"fail\"";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST
            ,consumes=MediaType.APPLICATION_JSON_VALUE
            ,produces=MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    public String createUser(@RequestBody UserInfo userInfo) {
        logger.info("[ajaxRequest: /createUser, " + userInfo.getUserName() + "]");
        // TODO 例外処理（PK重複、userInfoの必要な情報が空文字だった場合など
        // TODO トランザクションの設定
        this.registerAccount(userInfo, userInfo.getPassword());
        return "\"success\"";
    }

    private int registerAccount(UserInfo userInfo, String password) throws DuplicateKeyException{
        UserInfo newUserInfo = new UserInfo();
        newUserInfo.setUserName(userInfo.getUserName());
        newUserInfo.setPassword(passwordEncorder.encode(password));
        newUserInfo.setAuthority("USER");
        newUserInfo.setEnabled(true);
        return userInfoRepository.insertAccount(newUserInfo);
    }
}
