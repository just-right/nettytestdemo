package commandforclient;

import io.netty.channel.Channel;
import protocol.JoinGroupRequestPacket;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 10:26
 **/
public class JoinGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket requestPacket = new JoinGroupRequestPacket();

        System.out.println("输入群聊ID，加入群聊：");
        String groupId = scanner.next();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);
    }
}
