package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCode;

import java.nio.charset.Charset;
import java.util.Random;

/**
 * @author luffy
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        String userName = "user-";
        String pwd = "password";
        loginRequest(ctx,userName,pwd);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buffer = (ByteBuf)msg;
        PacketCode packetCode = new PacketCode();
        Packet packet = packetCode.decode(buffer);

        if (packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket)packet;
            if (responsePacket.isSuccess()){
                AttributeKey<String> nameAttrKey = AttributeKey.valueOf("userName");
                AttributeKey<String> pwdAttrKey = AttributeKey.valueOf("passWord");
                AttributeKey<Boolean> loginSuccessAttrKey = AttributeKey.valueOf("loginSuccess");

                ctx.channel().attr(nameAttrKey).set(responsePacket.getUserName());
                ctx.channel().attr(pwdAttrKey).set(responsePacket.getPassWord());
                ctx.channel().attr(loginSuccessAttrKey).set(true);
                System.out.println("登录成功！");
            }else {
                ctx.channel().attr(AttributeKey.newInstance("loginSuccess")).set(false);
                System.out.println("登录失败，失败原因："+responsePacket.getReason());
            }

            this.loginRequest(ctx,"user-","password");
        }
    }


    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("向服务端发送消息"+System.currentTimeMillis()).getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
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
}
