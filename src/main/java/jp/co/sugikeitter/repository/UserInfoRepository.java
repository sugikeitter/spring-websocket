package jp.co.sugikeitter.repository;

import java.util.List;

import jp.co.sugikeitter.repository.model.UserInfo;

public interface UserInfoRepository {
    int insertAccount(UserInfo userInfo);

    UserInfo findOne(String username);

    int countUserName(String username);

    List<UserInfo> findAllAccount();
}
