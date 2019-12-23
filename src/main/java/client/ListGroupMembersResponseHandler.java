package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.ListGroupMembersResponsePacket;
import protocol.Session;

import java.util.List;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:13
 **/
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersResponsePacket responsePacket) throws Exception {
        String groupId =  responsePacket.getGroupId();
        List<Session> sessionList = responsePacket.getSessionList();

        System.out.println("群"+groupId+"中成员包括："+sessionList);
    }
}
