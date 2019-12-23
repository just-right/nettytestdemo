package commandforclient;

import io.netty.channel.Channel;
import protocol.ListGroupMembersRequestPacket;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 11:22
 **/
public class ListGroupMembersConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket requestPacket = new ListGroupMembersRequestPacket();
        System.out.println("请输入groupId，获取成员列表");
        String groupId = scanner.next();
        requestPacket.setGroupId(groupId);
        channel.writeAndFlush(requestPacket);

    }
}
