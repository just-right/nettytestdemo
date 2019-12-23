package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.ListGroupMembersRequestPacket;
import protocol.ListGroupMembersResponsePacket;
import protocol.Session;
import protocol.SessionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:54
 **/
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersRequestPacket requestPacket)  {
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        String groupId = requestPacket.getGroupId();
        ChannelGroup group =  SessionUtil.getChannelGroup(groupId);
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel:group){
            Session tmpSession = SessionUtil.getSession(channel);
            sessionList.add(tmpSession);
        }

        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
//        channelHandlerContext.channel().writeAndFlush(responsePacket);
        channelHandlerContext.writeAndFlush(responsePacket);
    }
}
