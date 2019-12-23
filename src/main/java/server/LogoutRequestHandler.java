package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.LogoutRequestPacket;
import protocol.LogoutResponsePacket;
import protocol.SessionUtil;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:03
 **/
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutRequestPacket requestPacket)  {
        LogoutResponsePacket responsePacket = new LogoutResponsePacket();
        SessionUtil.unBindSession(channelHandlerContext.channel());
        responsePacket.setSuccess(true);
//        channelHandlerContext.channel().writeAndFlush(responsePacket);
        channelHandlerContext.writeAndFlush(responsePacket);

    }
}
