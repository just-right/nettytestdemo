package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/20 20:46
 **/
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        //将解码后的对象存入list-->向下一个handler传递 -- 自动释放内存（默认使用堆外内存）
        list.add(new PacketCode().decode(byteBuf));
    }
}
