package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 14:59
 **/
@Data
public class Session {
    /**
     * 登录用户信息
     * userID - 对应 - userName
     * @param userId
     * @param userName
     */
    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    private String userId;
    private String userName;
}
