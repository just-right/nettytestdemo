package commandforclient;

import io.netty.channel.Channel;
import protocol.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 20:55
 **/
public class CreateGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket packet = new CreateGroupRequestPacket();
        System.out.println("拉人进去群聊，输入userID以 , 隔开！");
        String userIDs = scanner.next();
        packet.setUserIdList(Arrays.asList(userIDs.split(",")));
        channel.writeAndFlush(packet);
    }
}
