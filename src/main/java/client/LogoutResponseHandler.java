package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.LogoutResponsePacket;
import protocol.SessionUtil;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:16
 **/
@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogoutResponsePacket logoutResponsePacket) throws Exception {
        System.out.println("退出登录成功！");
        SessionUtil.unBindSession(channelHandlerContext.channel());
    }
}
