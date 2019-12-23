package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 16:20
 **/
public class IMIdleStateHandler extends IdleStateHandler {
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler(){
        /**
         * 读空闲时间
         * 写空闲时间
         * 读写空闲时间
         * 时间单位
         */
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println(READER_IDLE_TIME+"未读到数据，断开连接！");
        ctx.channel().close();
    }
}
