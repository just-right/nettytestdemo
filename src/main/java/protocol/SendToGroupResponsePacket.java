package protocol;

import lombok.Data;
import sun.dc.pr.PRError;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 14:11
 **/
@Data
public class SendToGroupResponsePacket extends Packet{
    private boolean success;
    private String userName;
    private String groupId;
    private String reason;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_RESPONSE;
    }
}
