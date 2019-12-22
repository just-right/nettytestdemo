package protocol;

import io.netty.util.AttributeKey;

public interface ProtocolAttributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}