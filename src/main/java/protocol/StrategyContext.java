package protocol;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @author luffy
 **/
@Data
public class StrategyContext {

    private Strategy strategy;

    public void runStrategyMethod(ChannelHandlerContext ctx, Packet packet){
        strategy.strategyMethod(ctx,packet);
    }

}
