package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.MessageRequestPacket;
import protocol.MessageResponsePacket;
import protocol.PacketCode;

import java.util.Date;
import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/20 22:59
 **/
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket)  {
        String message = responsePacket.getMessage();
        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        System.out.println(fromUserId+":"+fromUserName+" -> "+message);
//        this.sendMsgToServer(ctx);
    }

    private void sendMsgToServer(ChannelHandlerContext ctx){
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        requestPacket.setMessage(msg);
        ctx.channel().writeAndFlush(requestPacket);
    }
}
