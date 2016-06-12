package jp.co.sugikeitter.repository.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@ToString(exclude="password")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;

    @Getter(AccessLevel.NONE)
    private String password;

    private String authority;

    private boolean enabled;

}
