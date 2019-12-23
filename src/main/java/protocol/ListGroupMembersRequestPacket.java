package protocol;

import lombok.Data;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:09
 **/
@Data
public class ListGroupMembersRequestPacket extends Packet{
    private String groupId;
    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
