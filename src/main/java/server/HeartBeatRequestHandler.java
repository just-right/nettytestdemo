package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.HeartbeatRequestPacket;
import protocol.HeartbeatResponsePacket;

import java.util.Date;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 16:42
 **/
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartbeatRequestPacket heartbeatRequestPacket) throws Exception {
        System.out.println(new Date()+":收到心跳检测！");
        channelHandlerContext.writeAndFlush(new HeartbeatResponsePacket());
    }
}
