package protocol;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 15:03
 **/
public class SessionUtil {
    private static final Map<String,Channel> userIdChannelMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session,Channel channel){
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(ProtocolAttributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if (hasLogin(channel)){
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(ProtocolAttributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(ProtocolAttributes.SESSION);
    }

    public static Session getSession(Channel channel){
       return channel.attr(ProtocolAttributes.SESSION).get();
    }

    public static Channel getChannel(String userID){
       return userIdChannelMap.get(userID);
    }


}
