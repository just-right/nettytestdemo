package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.MessageRequestPacket;
import protocol.MessageResponsePacket;

import java.util.Date;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/20 21:27
 **/
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        //接收客户端消息请求packet，向下一个handler传递消息响应packet
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setVersion(messageRequestPacket.getVersion());
        System.out.println(new Date()+"：收到客户端消息："+messageRequestPacket.getMessage());
        responsePacket.setMessage("我赞同！");
        ctx.channel().writeAndFlush(responsePacket);

    }
}
