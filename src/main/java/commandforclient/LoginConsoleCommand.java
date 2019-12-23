package commandforclient;

import io.netty.channel.Channel;
import protocol.LoginRequestPacket;

import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:15
 **/
public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        System.out.println("请输入用户名：");
        String userName = scanner.nextLine();

        requestPacket.setUserName(userName);
        requestPacket.setPassWord("pwd");

        channel.writeAndFlush(requestPacket);
        waitForLoginResponse();
    }

    public static void waitForLoginResponse(){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
    }
}
