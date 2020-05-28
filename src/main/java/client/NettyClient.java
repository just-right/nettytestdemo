package client;

import commandforclient.ConsoleCommandManager;
import commandforclient.LoginConsoleCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import protocol.*;

import java.util.Scanner;
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
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
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

    private static void startConsoleThread(Channel channel) {
        Scanner sc = new Scanner(System.in);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager manager = new ConsoleCommandManager();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (SessionUtil.getSession(channel)==null) {
                    loginConsoleCommand.exec(sc,channel);
                    waitForLoginResponse();
                } else {
                    manager.exec(sc,channel);
                }
            }
        }).start();
    }
    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args){
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootStrap = new Bootstrap();
        bootStrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch){
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(LoginResponseHandler.INSTANCE);
                        ch.pipeline().addLast(MessageResponseHandler.INSTANCE);
                        ch.pipeline().addLast(CreateGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(JoinGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(QuitGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(GetFileResponseHandler.INSTANCE);
                        ch.pipeline().addLast(ListGroupMembersResponseHandler.INSTANCE);
                        ch.pipeline().addLast(LogoutResponseHandler.INSTANCE);
                        ch.pipeline().addLast(SendToGroupResponseHandler.INSTANCE);
                        ch.pipeline().addLast(new PacketEncoder());

                        ch.pipeline().addLast(new HeartBeatTimerHandler());

                    }
                })
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true);
        NettyClient.connect("127.0.0.1",8080,5,bootStrap);



    }

}
