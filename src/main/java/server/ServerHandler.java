package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import protocol.*;
import sun.rmi.runtime.Log;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author luffy
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buffer = (ByteBuf)msg;

        PacketCode packetCode = new PacketCode();
        Packet packet = packetCode.decode(buffer);

        if (packet instanceof LoginRequestPacket){
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
        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket requestPacket = (MessageRequestPacket)packet;
            MessageResponsePacket responsePacket = new MessageResponsePacket();
            responsePacket.setVersion(requestPacket.getVersion());
            System.out.println(new Date()+"：收到客户端消息："+requestPacket.getMessage());
            responsePacket.setMessage("你好！");
            ByteBuf resBuffer = packetCode.encode(responsePacket);
            ctx.channel().writeAndFlush(resBuffer);
        }

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("服务端已收到，返回给客户端-->").getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
    }

    private ByteBuf getByteBufForStart(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("客户端已连接，hello-->").getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
    }

    private ByteBuf getByteBufForLoginSuccess(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("登录成功！").getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
    }

    private ByteBuf getByteBufForLoginFail(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("登录失败！").getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
    }

    private ByteBuf getByteBufForMsg(ChannelHandlerContext ctx){
        ByteBuf buffer = ctx.alloc().buffer();
        byte[] byteArray = ("你好").getBytes(Charset.forName("utf-8"));
        buffer.writeBytes(byteArray);
        return buffer;
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
