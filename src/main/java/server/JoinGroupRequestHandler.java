package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.JoinGroupRequestPacket;
import protocol.JoinGroupResponsePacket;
import protocol.SessionUtil;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:46
 **/
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket requestPacket) throws Exception {
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        String groupId= requestPacket.getGroupId();
        ChannelGroup group =  SessionUtil.getChannelGroup(groupId);
        if (group!=null){
            group.add(channelHandlerContext.channel());
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
        }else {
            responsePacket.setSuccess(false);
        }
        channelHandlerContext.writeAndFlush(responsePacket);
//        channelHandlerContext.channel().writeAndFlush(responsePacket);
    }
}
