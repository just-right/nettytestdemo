package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.PacketDecoder;
import protocol.PacketEncoder;

import java.util.concurrent.TimeUnit;

/**
 * @author luffy
 **/
public class NettyClient {
    private static final int MAX_RETRY = 5;

    private static void connect(final String host,final int port,final int retry,final Bootstrap bootstrap){
        bootstrap.connect(host,port).addListener(future ->  {
                if (future.isSuccess()){
                    System.out.println("客户端连接成功！");
                }else if(retry == 0){
                    System.out.println("连接失败！");
                }
                else {
                    int order = (MAX_RETRY - retry) + 1;
                    int delay = 1 << order;
                    bootstrap.config().group().schedule(() -> connect(host, port, retry - 1, bootstrap), delay, TimeUnit.SECONDS);
                }
        });
    }

    public static void main(String[] args){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootStrap = new Bootstrap();
        bootStrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch){
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                })
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);
        NettyClient.connect("127.0.0.1",8080,5,bootStrap);



    }

}
