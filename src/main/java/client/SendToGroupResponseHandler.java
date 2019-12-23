package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.SendToGroupResponsePacket;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 14:25
 **/
@ChannelHandler.Sharable
public class SendToGroupResponseHandler extends SimpleChannelInboundHandler<SendToGroupResponsePacket> {
    public static final SendToGroupResponseHandler INSTANCE = new SendToGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupResponsePacket responsePacket) throws Exception {
        String groupId = responsePacket.getGroupId();
        String message = responsePacket.getMessage();
        boolean flag = responsePacket.isSuccess();
        String userName = responsePacket.getUserName();

        if (flag){
            System.out.println("收到"+groupId+"群消息，来自群成员："+userName+":"+message);
        }else {
            System.out.println("收到"+groupId+"群消息，来自群成员："+userName+"的消息失败，失败原因"+responsePacket.getReason());
        }
    }
}
