package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.CreateGroupResponsePacket;
import sun.security.krb5.internal.PAData;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:42
 **/
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupResponsePacket packet) throws Exception {
        System.out.println("群创建成功，群ID为："+packet.getGroupId());
        System.out.println("群成员包括："+packet.getUserNameList());
    }
}
