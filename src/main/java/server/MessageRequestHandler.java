package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.MessageRequestPacket;
import protocol.MessageResponsePacket;
import protocol.Session;
import protocol.SessionUtil;

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
        Session session = SessionUtil.getSession(ctx.channel());
        responsePacket.setVersion(messageRequestPacket.getVersion());
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUserName());
//        System.out.println(new Date()+"：收到客户端消息："+messageRequestPacket.getMessage());
        responsePacket.setMessage(messageRequestPacket.getMessage());
        //获取要发送消息对象的Channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (toUserChannel!=null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(responsePacket);
        }else {
            System.out.println("用户："+messageRequestPacket.getToUserId()+"不在线！");
        }
//        ctx.channel().writeAndFlush(responsePacket);

    }
}
