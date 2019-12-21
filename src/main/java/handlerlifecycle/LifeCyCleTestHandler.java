package handlerlifecycle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * ChannelInboundHandlerAdapter 生命周期-回调方法
 * 备注：ChannelInitializer原理实现-查看
 * @author luffy
 * @version 1.0
 * @date 2019/12/21 22:38
 **/
public class LifeCyCleTestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * ch.pipeline().addLast(new LifeCyCleTestHandler());
         * 方法后的回调函数，添加一个handler处理器
         */
        System.out.println("handler处理器被添加！");
        super.handlerAdded(ctx);
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        /**
         * 表示当前的 channel 的所有的逻辑处理已经和某个 NIO 线程建立了绑定关系
         * 线程池抓取一个线程绑定channel
         */
        System.out.println("channel绑定到线程（NioEventLoop）！");
        super.channelRegistered(ctx);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 当 channel 的所有的业务逻辑链准备完毕
         * （也就是说 channel 的 pipeline 中已经添加完所有的 handler）
         * 以及绑定好一个 NIO 线程之后，这条连接算是真正激活了
         * 引用计数+1
         */
        System.out.println("channel准备就绪！");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 有数据可读回调
         */
        System.out.println("channel有数据可读！");
        super.channelRead(ctx, msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * 读完一次完整的数据
         * 数据读取完毕
         */
        System.out.println("channel数据读完！");
        super.channelReadComplete(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 连接关闭
         * 引用计数-1
         */
        System.out.println("channel被关闭！");
        super.channelInactive(ctx);
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        /**
         * 连接对应的 NIO 线程移除掉对这条连接的处理-回调
         */
        System.out.println("channel取消线程（NioEventLoop）！");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        /**
         * 给这条连接上添加的所有的业务逻辑处理器都给移除掉-回调
         */
        System.out.println("handler被移除！");
        super.handlerRemoved(ctx);
    }
}
