package commandforclient;

import io.netty.channel.Channel;
import protocol.SessionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:20
 **/
public class ConsoleCommandManager implements ConsoleCommand{

    private Map<String,ConsoleCommand> map = new HashMap<>();

    public ConsoleCommandManager(){
        map.put("sendToUser",new SendToUserConsoleCommand());
        map.put("logout",new LogoutConsoleCommand());
        map.put("createGroup",new CreateGroupConsoleCommand());
        map.put("joinGroup",new JoinGroupConsoleCommand());
        map.put("quitGroup", new QuitGroupConsoleCommand());
        map.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        map.put("sendToGroup", new SendToGroupConsoleCommand());
        map.put("getFile", new GetFileConsoleCommand());

    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String command = scanner.next();
        if (!SessionUtil.hasLogin(channel)){
            return;
        }
        ConsoleCommand consoleCommand = map.get(command);

        if (consoleCommand!=null){
            consoleCommand.exec(scanner,channel);
        }else {
            System.out.println("无法识别"+command+"指令，请重新输入！");
        }

    }
}
