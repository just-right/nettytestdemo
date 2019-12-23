package protocol;

import lombok.Data;

import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:30
 **/
@Data
public class ListGroupMembersResponsePacket extends Packet{
    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
