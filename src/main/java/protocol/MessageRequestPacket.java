package protocol;

import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class MessageRequestPacket extends Packet{

    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }

    @Override
    public Strategy getStrategy() {
        return new MessageRequestStrategy();
    }
}
