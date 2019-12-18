package protocol;

import com.alibaba.fastjson.JSON;

/**
 * @author luffy
 **/
public class JSONSerializer implements Serializer{
    private byte jsonId = 1;
    public byte getSerializerMethod() {
        return jsonId;
    }

    public byte[] serializer(Object object) {
       return JSON.toJSONBytes(object);
    }

    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
