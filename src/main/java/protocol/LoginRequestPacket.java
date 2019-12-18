package protocol;

import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class LoginRequestPacket extends Packet{
    private Integer userId;
    private String userName;
    private String passWord;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    @Override
    public Strategy getStrategy() {
        return new LoginRequestStrategy();
    }
}
