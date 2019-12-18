package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.*;

import java.nio.charset.Charset;

/**
 * @author luffy
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buffer = (ByteBuf)msg;

        PacketCode packetCode = new PacketCode();
        Packet packet = packetCode.decode(buffer);

        StrategyContext context = new StrategyContext();
        context.setStrategy(packet.getStrategy());
        context.runStrategyMethod(ctx,packet);

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



}
