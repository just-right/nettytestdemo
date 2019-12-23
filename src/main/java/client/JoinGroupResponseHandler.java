package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.JoinGroupResponsePacket;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:08
 **/
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupResponsePacket responsePacket) throws Exception {
        boolean flag = responsePacket.isSuccess();
        if (flag){
            System.out.println("加入群："+responsePacket.getGroupId()+"成功！");
        }else {
            System.out.println("加入群："+responsePacket.getGroupId()+"失败！"+responsePacket.getReason());
        }
    }
}
