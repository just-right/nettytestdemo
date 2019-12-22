package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.Scanner;

/**
 * @author luffy
 **/
public class MessageResponseStrategy implements Strategy{

    /**
     * 服务端发送消息后，客户端响应
     */
    @Override
    public void strategyMethod(ChannelHandlerContext ctx, Packet packet) {
        MessageResponsePacket responsePacket = (MessageResponsePacket)packet;
        String message = responsePacket.getMessage();
        System.out.println(new Date()+"：收到服务端新消息："+message);
        this.sendMsgToServer(ctx);
    }

    private void sendMsgToServer(ChannelHandlerContext ctx){
        PacketCode packetCode = new PacketCode();
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        requestPacket.setMessage(msg);
        ByteBuf buffer = packetCode.encode(requestPacket);
        ctx.channel().writeAndFlush(buffer);
    }
}
