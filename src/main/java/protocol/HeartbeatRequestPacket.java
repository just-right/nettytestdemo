package protocol;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 16:30
 **/
public class HeartbeatRequestPacket extends Packet{

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
