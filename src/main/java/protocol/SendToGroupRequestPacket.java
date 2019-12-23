package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 14:09
 **/
@Data
public class SendToGroupRequestPacket extends  Packet{
    private String userName;
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return Command.SEND_GROUP_REQUEST;
    }
}
