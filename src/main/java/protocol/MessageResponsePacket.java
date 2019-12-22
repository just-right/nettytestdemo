package protocol;

import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class MessageResponsePacket extends Packet{
    private String fromUserId;
    private String fromUserName;
    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }

}
