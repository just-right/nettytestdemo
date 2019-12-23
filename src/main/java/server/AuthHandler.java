package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginUtil;
import sun.dc.pr.PRError;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 14:28
 **/
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!LoginUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            //热插拔 移除登录校验
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (LoginUtil.hasLogin(ctx.channel())){
            System.out.println("登录已经校验，AuthHandler已被移除！");
        }else {
            System.out.println("无登录验证，关闭连接！");
        }
    }
}