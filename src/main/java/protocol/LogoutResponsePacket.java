package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:03
 **/
@Data
public class LogoutResponsePacket extends Packet{
    private boolean success;
    private String reason;
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
