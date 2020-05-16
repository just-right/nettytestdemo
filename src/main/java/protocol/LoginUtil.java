package protocol;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.jar.Attributes;


/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 14:16
 **/
public class LoginUtil {

    /**
     * 设置登录标志
     * channel.attr - AttributeKey
     * @param channel
     */
    public static void setLogin(Channel channel){
      channel.attr(AttributeKey.newInstance("login")).set(true);
    }

    /**
     * 判断是否登录
     * channel.attr - AttributeKey
     * @param channel
     */
    public static boolean hasLogin(Channel channel){

       Attribute<Boolean> loginAttr = channel.attr(ProtocolAttributes.LOGIN);
       return loginAttr.get()!=null;

    }
}
