package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.Random;
import java.util.Scanner;

/**
 * @author luffy
 **/
public class LoginResponseStrategy implements Strategy{
    /**
     * 服务端响应登录请求，客户端响应
     */
    @Override
    public void strategyMethod(ChannelHandlerContext ctx, Packet packet) {
        LoginResponsePacket responsePacket = (LoginResponsePacket)packet;
        if (responsePacket.isSuccess()){
            AttributeKey<String> nameAttrKey = AttributeKey.valueOf("userName");
            AttributeKey<String> pwdAttrKey = AttributeKey.valueOf("passWord");
            AttributeKey<Boolean> loginSuccessAttrKey = AttributeKey.valueOf("loginSuccess");
            ctx.channel().attr(nameAttrKey).set(responsePacket.getUserName());
            ctx.channel().attr(pwdAttrKey).set(responsePacket.getPassWord());
            ctx.channel().attr(loginSuccessAttrKey).set(true);
            System.out.println("登录成功！");
            //发送消息给服务端
            sendMsgToServer(ctx);

        }else {
            ctx.channel().attr(AttributeKey.newInstance("loginSuccess")).set(false);
            System.out.println("登录失败，失败原因："+responsePacket.getReason());
        }
        //this.loginRequest(ctx,"user-","password");
    }

    private void loginRequest(ChannelHandlerContext ctx,String userName,String pwd) {

        AttributeKey<String> nameAttrKey = AttributeKey.valueOf("userName");
        AttributeKey<String> pwdAttrKey = AttributeKey.valueOf("passWord");
        AttributeKey<Boolean> loginSuccessAttrKey = AttributeKey.valueOf("loginSuccess");


        boolean nameFlag = ctx.channel().hasAttr(nameAttrKey);
        boolean pwdFlag = ctx.channel().hasAttr(pwdAttrKey);
        boolean loginSuccessFlag = ctx.channel().hasAttr(loginSuccessAttrKey);

        if (nameFlag && pwdFlag && loginSuccessFlag){
            String name = ctx.channel().attr(nameAttrKey).get();
            String passWord= ctx.channel().attr(pwdAttrKey).get();
            boolean loginSuccess = ctx.channel().attr(loginSuccessAttrKey).get();
            if (loginSuccess && name.equals(userName) && passWord.equals(pwd)){
                System.out.println("你已登录成功，无需重新登录！");
                return;
            }
        }

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(new Random().nextInt(10) + 1);
        requestPacket.setUserName(userName);
        requestPacket.setPassWord(pwd);

        PacketCode packetCode = new PacketCode();
        ByteBuf buffer = packetCode.encode(requestPacket);
        ctx.channel().writeAndFlush(buffer);
    }



    private void sendMsgToServer(ChannelHandlerContext ctx){
        PacketCode packetCode = new PacketCode();
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        requestPacket.setMessage(msg);
        ByteBuf buffer = packetCode.encode(requestPacket);
        ctx.channel().writeAndFlush(buffer);
    }
}
