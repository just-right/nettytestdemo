package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.SendToGroupRequestPacket;
import protocol.SendToGroupResponsePacket;
import protocol.SessionUtil;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 14:20
 **/
@ChannelHandler.Sharable
public class SendToGroupRequestHandler extends SimpleChannelInboundHandler<SendToGroupRequestPacket> {
    public static final SendToGroupRequestHandler INSTANCE = new SendToGroupRequestHandler();
//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SendToGroupRequestPacket requestPacket) {

        //
//        threadPoolExecutor.submit(new Runnable() {
//            @Override
//            public void run() {
//                long beginTime = System.currentTimeMillis();
//                channelHandlerContext.channel().writeAndFlush("").addListener(future -> {
//                    if (future.isDone()){
//                        long endTime = System.currentTimeMillis();
//                        System.out.println("花费时间："+(endTime-beginTime));
//                    }
//                });
//            }
//        });
        String groupId = requestPacket.getGroupId();
        String message = requestPacket.getMessage();
        String userName = requestPacket.getUserName();
        SendToGroupResponsePacket responsePacket = new SendToGroupResponsePacket();
        responsePacket.setMessage(message);
        responsePacket.setSuccess(true);
        responsePacket.setUserName(userName);
        responsePacket.setGroupId(groupId);

        ChannelGroup group =  SessionUtil.getChannelGroup(groupId);
        group.writeAndFlush(responsePacket);

    }
}
