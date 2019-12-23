package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.haproxy.HAProxyCommand;
import protocol.Command;
import protocol.Packet;
import protocol.QuitGroupRequestPacket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 15:20
 **/
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;


    public IMHandler(){

        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST,MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.CREATE_GROUP_REQUEST,CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.JOIN_GROUP_REQUEST,JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.QUIT_GROUP_REQUEST,QuitGroupRequestHandler.INSTANCE);

        handlerMap.put(Command.LIST_GROUP_MEMBERS_REQUEST,ListGroupMembersRequestHandler.INSTANCE);

        handlerMap.put(Command.SEND_GROUP_REQUEST,SendToGroupRequestHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_REQUEST,LogoutRequestHandler.INSTANCE);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(channelHandlerContext,packet);
    }
}
