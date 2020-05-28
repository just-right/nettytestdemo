package protocol;

import io.netty.channel.DefaultFileRegion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @className: GetFileResponsePacket
 * @description
 * @author: luffy
 * @date: 2020/5/22 14:24
 * @version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GetFileResponsePacket extends Packet{

    private boolean sendFileFlag;
    private int startPos;
    private int endPos;
    private String fileName;
    private byte[] bytes;

    @Override
    public Byte getCommand() {
        return Command.GET_FILE_RESPONSE;
    }
}
