package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.HeartbeatRequestPacket;

import java.util.concurrent.TimeUnit;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 16:34
 **/
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        ctx.executor().schedule(()->{
            if (ctx.channel().isActive()){
                ctx.writeAndFlush(new HeartbeatRequestPacket());
                scheduleSendHeartBeat(ctx);
            }
        },HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
    }
}
