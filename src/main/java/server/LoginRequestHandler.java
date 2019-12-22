package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.*;

import java.util.UUID;

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
            //添加登录标识
            ctx.channel().attr(ProtocolAttributes.LOGIN).set(true);
            System.out.println(requestPacket.getUserName()+"登录成功！");
            String userId = randomUserId();
            SessionUtil.bindSession(new Session(userId,requestPacket.getUserName()),ctx.channel());
            responsePacket.setUserName(requestPacket.getUserName());
            responsePacket.setPassWord(requestPacket.getPassWord());
            responsePacket.setUserId(userId);
            responsePacket.setSuccess(true);
        } else {
            responsePacket.setSuccess(false);
            System.out.println(requestPacket.getUserName()+"登录失败！");
            responsePacket.setReason("账号密码错误！");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean loginValid(LoginRequestPacket packet) {
        return true;
//        boolean loginFlag = false;
//        System.out.println(packet.getUserName());
//        if ("user-".equals(packet.getUserName())) {
//            loginFlag = true;
//        }
//        return loginFlag;
    }
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        SessionUtil.unBindSession(ctx.channel());
    }
}
