package protocol;
/**
 * @author luffy
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerMethod();

    byte[] serializer(Object object);

    <T> T deserializer(Class<T> clazz,byte[] bytes);
}
