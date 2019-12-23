package server;

import com.sun.org.apache.regexp.internal.RE;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.QuitGroupRequestPacket;
import protocol.QuitGroupResponsePacket;
import protocol.SessionUtil;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:59
 **/
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket requestPacket) {
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        String groupId = requestPacket.getGroupId();

        ChannelGroup group =  SessionUtil.getChannelGroup(groupId);
        group.remove(channelHandlerContext.channel());

        responsePacket.setGroupId(groupId);
        responsePacket.setSuccess(true);
//        channelHandlerContext.channel().writeAndFlush(responsePacket);
        channelHandlerContext.writeAndFlush(responsePacket);



    }
}
