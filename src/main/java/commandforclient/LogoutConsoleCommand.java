package commandforclient;

import io.netty.channel.Channel;
import protocol.LogoutRequestPacket;
import protocol.SessionUtil;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:11
 **/
public class LogoutConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        if (SessionUtil.getSession(channel)!=null) {
            LogoutRequestPacket requestPacket = new LogoutRequestPacket();
            channel.writeAndFlush(requestPacket);
        }else {
            System.out.println("您已退出登录，无法操作！");
        }
    }
}
