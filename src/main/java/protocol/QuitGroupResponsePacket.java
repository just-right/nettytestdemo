package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:43
 **/
@Data
public class QuitGroupResponsePacket extends Packet{
    private String groupId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
