package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import protocol.HeartbeatResponsePacket;
import protocol.PacketDecoder;
import protocol.PacketEncoder;
import protocol.Spliter;

/**
 * @author luffy
 **/
public class NettyServer {

    private final static int START_PORT = 8080;

    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功！Port:" + port);
            } else {
                bind(serverBootstrap, port + 1);
            }
        });

    }



    public static void main(String[] args){

        //线程组-监听端口 & 处理数据读写
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        /*
        * childHandler-处理数据读写 & handler-服务端启动
        * attr-添加属性 & childAttr-所有Channel添加属性
        * childOption-设置TCP底层属性
        * option-服务端Channel设置属性
        */
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch){
                        //pipeline-channelHandlerContext(channelHandler) --> 双向链表
                        /**
                         * inboundA  -->  inboundB  -->  inboundC
                         *                                  |
                         *                                 ∨
                         * outboundA <--  outboundB <--  outboundC
                         *
                         */
                        //读数据逻辑链-顺序执行
//                        ch.pipeline().addLast("inboundA",new ServerInBoundHandler());
//                        ch.pipeline().addLast("inboundB",new SecondInBoundServerHandler());
//                        //写数据逻辑-倒序执行
//                        ch.pipeline().addLast("outboundA",new ServerOutBoundHandler());
//                        ch.pipeline().addLast("outboundB",new SecondOutBoundServerHandler());
                          ch.pipeline().addLast(new IMIdleStateHandler());
                          ch.pipeline().addLast(new Spliter());
//                          ch.pipeline().addLast(new PacketDecoder());
                          ch.pipeline().addLast(PacketCodeHandler.INSTANCE);
                          ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                          ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                          ch.pipeline().addLast(AuthHandler.INSTANCE);
                          ch.pipeline().addLast(IMHandler.INSTANCE);
//                          ch.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                          ch.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//
//                          ch.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                          ch.pipeline().addLast(LogoutRequestHandler.INSTANCE);
//                          ch.pipeline().addLast(ListGroupMembersRequestHandler.INSTANCE);
//                          ch.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//
//                          ch.pipeline().addLast(SendToGroupRequestHandler.INSTANCE);

//                          ch.pipeline().addLast(new PacketEncoder());


                    }
                })
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch){
                        System.out.println("服务端正在启动！");
                    }
                })
                .childAttr(AttributeKey.newInstance("password"),"pwd")
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .option(ChannelOption.SO_BACKLOG,1024);

        NettyServer.bind(serverBootstrap,NettyServer.START_PORT);

    }

}
