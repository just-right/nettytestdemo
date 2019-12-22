package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;
import protocol.MessageRequestPacket;
import protocol.PacketCode;

import java.util.Random;
import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/20 22:51
 **/
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //发送登录请求
        String userName = "user-";
        String pwd = "password";
        loginRequest(ctx, userName, pwd);
//        sendMsgToServer(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {
        if (responsePacket.isSuccess()) {
            AttributeKey<String> nameAttrKey = AttributeKey.valueOf("userName");
            AttributeKey<String> pwdAttrKey = AttributeKey.valueOf("passWord");
            AttributeKey<Boolean> loginSuccessAttrKey = AttributeKey.valueOf("loginSuccess");
            ctx.channel().attr(nameAttrKey).set(responsePacket.getUserName());
            ctx.channel().attr(pwdAttrKey).set(responsePacket.getPassWord());
            ctx.channel().attr(loginSuccessAttrKey).set(true);
            System.out.println("登录成功！");
            //发送消息给服务端
            sendMsgToServer(ctx);

        } else {
            ctx.channel().attr(AttributeKey.newInstance("loginSuccess")).set(false);
            System.out.println("登录失败，失败原因：" + responsePacket.getReason());
        }
    }

    private void loginRequest(ChannelHandlerContext ctx, String userName, String pwd) {

        AttributeKey<String> nameAttrKey = AttributeKey.valueOf("userName");
        AttributeKey<String> pwdAttrKey = AttributeKey.valueOf("passWord");
        AttributeKey<Boolean> loginSuccessAttrKey = AttributeKey.valueOf("loginSuccess");


        boolean nameFlag = ctx.channel().hasAttr(nameAttrKey);
        boolean pwdFlag = ctx.channel().hasAttr(pwdAttrKey);
        boolean loginSuccessFlag = ctx.channel().hasAttr(loginSuccessAttrKey);

        if (nameFlag && pwdFlag && loginSuccessFlag) {
            String name = ctx.channel().attr(nameAttrKey).get();
            String passWord = ctx.channel().attr(pwdAttrKey).get();
            boolean loginSuccess = ctx.channel().attr(loginSuccessAttrKey).get();
            if (loginSuccess && name.equals(userName) && passWord.equals(pwd)) {
                System.out.println("你已登录成功，无需重新登录！");
                return;
            }
        }

        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(new Random().nextInt(10) + 1);
        requestPacket.setUserName(userName);
        requestPacket.setPassWord(pwd);
        ctx.channel().writeAndFlush(requestPacket);
    }

    private void sendMsgToServer(ChannelHandlerContext ctx) {
        MessageRequestPacket requestPacket = new MessageRequestPacket();
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        requestPacket.setMessage(msg);

        ctx.channel().writeAndFlush(requestPacket);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  {
        System.out.println("客户端连接关闭！");
    }
}