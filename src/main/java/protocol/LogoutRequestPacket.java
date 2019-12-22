package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:00
 **/
@Data
public class LogoutRequestPacket extends Packet{
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
