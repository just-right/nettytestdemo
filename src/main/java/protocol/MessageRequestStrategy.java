package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * @author luffy
 **/
public class MessageRequestStrategy implements Strategy{
    /**
     * 客户端发送消息后，服务端响应
     */
    @Override
    public void strategyMethod(ChannelHandlerContext ctx, Packet packet) {
        PacketCode packetCode = new PacketCode();
        MessageRequestPacket requestPacket = (MessageRequestPacket)packet;
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        System.out.println(new Date()+"：收到客户端消息："+requestPacket.getMessage());
        responsePacket.setMessage("我赞同！");
        ByteBuf resBuffer = packetCode.encode(responsePacket);
        ctx.channel().writeAndFlush(resBuffer);
    }
}
