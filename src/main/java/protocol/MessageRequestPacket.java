package protocol;

import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class MessageRequestPacket extends Packet{
    private String toUserId;
    private String message;

    public MessageRequestPacket() {
    }

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

}
