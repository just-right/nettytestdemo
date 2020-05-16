package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/21 20:47
 **/
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    /**
     * 粘包拆包 --- LENGTH_FIELD_OFFSET(4+1+1+1) 偏移量 LENGTH_FIELD_LENGTH数据长度
     * 详情通信协议设计
     */
    public Spliter(){
        super(Integer.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //判断通信数据 格式 ----- 魔数
        if (in.getInt(in.readerIndex())!=PacketCode.MAGIC_NUMBER){
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
