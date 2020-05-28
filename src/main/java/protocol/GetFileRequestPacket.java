package protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @className: GetFileRequestPacket
 * @description
 * @author: luffy
 * @date: 2020/5/22 14:21
 * @version:V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GetFileRequestPacket extends Packet{
    private boolean getFileFlag;
    private String fileName;

    @Override
    public Byte getCommand() {
        return Command.GET_FILE_REQUEST;
    }

}
