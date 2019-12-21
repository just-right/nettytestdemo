package protocol;

import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class LoginResponsePacket extends Packet{
    private boolean success;
    private String userName;
    private String passWord;
    private String reason;
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }

}
