package commandforclient;

import io.netty.channel.Channel;
import protocol.QuitGroupRequestPacket;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:25
 **/
public class QuitGroupConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequestPacket requestPacket = new QuitGroupRequestPacket();
        System.out.println("输入groupId，退出群聊");
        String groupId = scanner.next();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);

    }
}
