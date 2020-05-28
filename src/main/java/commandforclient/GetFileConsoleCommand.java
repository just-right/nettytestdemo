package commandforclient;

import io.netty.channel.Channel;
import protocol.CreateGroupRequestPacket;
import protocol.GetFileRequestPacket;
import server.GetFileRequestHandler;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @className: GetFileConsoleCommand
 * @description
 * @author: luffy
 * @date: 2020/5/22 16:06
 * @version:V1.0
 */
public class GetFileConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        GetFileRequestPacket packet = new GetFileRequestPacket();
        System.out.println("输入文件名，获取文件！");
        String fileName = scanner.next();
        packet.setFileName(fileName).setGetFileFlag(true);
        channel.writeAndFlush(packet);
    }
}
