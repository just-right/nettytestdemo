package protocol;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 16:31
 **/
public class HeartbeatResponsePacket extends Packet{

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
