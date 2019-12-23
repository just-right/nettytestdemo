package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.QuitGroupResponsePacket;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:17
 **/
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupResponsePacket responsePacket) throws Exception {
        boolean flag = responsePacket.isSuccess();
        if (flag){
            System.out.println("退出群聊"+responsePacket.getGroupId()+"成功！");
        }else {
            System.out.println("退出群聊"+responsePacket.getGroupId()+"失败！"+responsePacket.getReason());
        }
    }
}
