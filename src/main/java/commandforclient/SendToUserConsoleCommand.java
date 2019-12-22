package commandforclient;

import io.netty.channel.Channel;
import protocol.MessageRequestPacket;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:12
 **/
public class SendToUserConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息给某个用户：userID+Message!");
        String userId = scanner.next();
        String message = scanner.next();
        MessageRequestPacket packet = new MessageRequestPacket(userId,message);
        channel.writeAndFlush(packet);
    }
}
