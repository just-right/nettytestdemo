package protocol;

import lombok.Data;

import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 20:52
 **/
@Data
public class CreateGroupRequestPacket extends Packet{
    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
