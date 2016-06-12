package jp.co.sugikeitter.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sugikeitter.repository.model.UserInfo;

@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    @Autowired
    private NamedParameterJdbcTemplate npJdbcTemplate;

    @Override
    @Transactional(readOnly=true)
    public int insertAccount(UserInfo userInfo) {
        return npJdbcTemplate.update("INSERT INTO user_info(user_name, password, authority, enabled)"
                + " VALUES(:userName, :password, :authority, :enabled)",
                new BeanPropertySqlParameterSource(userInfo));
    }

    @Override
    @Transactional(readOnly=true)
    public UserInfo findOne(String username) {
        return npJdbcTemplate.queryForObject(
                "SELECT * FROM user_info WHERE user_name = :username",
                new MapSqlParameterSource().addValue("username", username),
                new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
    }

    @Override
    @Transactional(readOnly=true)
    public int countUserName(String username) {
        return npJdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_info WHERE user_name = :username",
                new MapSqlParameterSource().addValue("username", username),
                Integer.class);
    }

    @Override
    @Transactional(readOnly=true)
    public List<UserInfo> findAllAccount() {
        return npJdbcTemplate.query(
                "SELECT * FROM user_info",
                new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
    }
}
