package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/20 22:42
 **/
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket requestPacket) {
        //接收客户端登录请求packet，向下一个handler传递登录响应packet
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(requestPacket.getVersion());
        if (loginValid(requestPacket)) {
            responsePacket.setUserName(requestPacket.getUserName());
            responsePacket.setPassWord(requestPacket.getPassWord());
            responsePacket.setSuccess(true);
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码错误！");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean loginValid(LoginRequestPacket packet) {
        boolean loginFlag = false;
        System.out.println(packet.getUserName());
        if ("user-".equals(packet.getUserName())) {
            loginFlag = true;
        }
        return loginFlag;
    }
}