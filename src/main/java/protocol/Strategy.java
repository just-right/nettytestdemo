package protocol;

import io.netty.channel.ChannelHandlerContext;

public interface Strategy {
    void strategyMethod(ChannelHandlerContext ctx, Packet packet);
}
