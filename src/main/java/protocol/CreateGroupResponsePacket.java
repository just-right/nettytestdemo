package protocol;

import lombok.Data;

import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:04
 **/
@Data
public class CreateGroupResponsePacket extends Packet{
    private boolean success;
    private String groupId;
    private List<String> userNameList;
    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
