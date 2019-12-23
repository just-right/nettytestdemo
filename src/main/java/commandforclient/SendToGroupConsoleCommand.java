package commandforclient;

import io.netty.channel.Channel;
import protocol.SendToGroupRequestPacket;
import protocol.SessionUtil;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/23 14:14
 **/
public class SendToGroupConsoleCommand implements ConsoleCommand{

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入groupID,消息-发送消息，空格隔开！");
        String groupId = scanner.next();
        String message = scanner.next();
        String userName = SessionUtil.getSession(channel).getUserName();
        SendToGroupRequestPacket requestPacket = new SendToGroupRequestPacket();

        requestPacket.setGroupId(groupId);
        requestPacket.setMessage(message);
        requestPacket.setUserName(userName);

        channel.writeAndFlush(requestPacket);

    }
}
