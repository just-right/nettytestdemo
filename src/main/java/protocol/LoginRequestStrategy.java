package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author luffy
 **/
public class LoginRequestStrategy implements Strategy{
    /**
     * 客户端请求登录，服务端响应
     */
    @Override
    public void strategyMethod(ChannelHandlerContext ctx, Packet packet) {
        PacketCode packetCode = new PacketCode();
        LoginRequestPacket requestPacket = (LoginRequestPacket)packet;
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        if (loginValid(requestPacket)){
            responsePacket.setUserName(requestPacket.getUserName());
            responsePacket.setPassWord(requestPacket.getPassWord());
            responsePacket.setSuccess(true);
        }else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码错误！");
        }
        ByteBuf resBuffer = packetCode.encode(responsePacket);
        ctx.channel().writeAndFlush(resBuffer);
    }

    private boolean loginValid(LoginRequestPacket packet){
        boolean loginFlag = false;
        System.out.println(packet.getUserName());
        if ("user-".equals(packet.getUserName())){
            loginFlag =  true;
        }
        return loginFlag;
    }
}
