package protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luffy
 **/
public class PacketCode {
    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerMethod(), serializer);
    }

    /**
     * 编码----
     * @param packet
     * @return ByteBuf
     */
    public ByteBuf encode(Packet packet){

        ByteBuf buffer = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serializer(packet);

        //魔数-4Byte
        buffer.writeInt(MAGIC_NUMBER);
        //版本号-1Byte
        buffer.writeByte(packet.getVersion());
        //序列化算法-1Byte
        buffer.writeByte(Serializer.DEFAULT.getSerializerMethod());
        //指令-1Byte
        buffer.writeByte(packet.getCommand());
        //数据长度-4Byte
        buffer.writeInt(bytes.length);
        //数据
        buffer.writeBytes(bytes);

        return buffer;

    }


    public Packet decode(ByteBuf buffer){
        //跳过魔数、版本号-
        buffer.skipBytes(4);
        buffer.skipBytes(1);

        //序列化方法
        byte serializeMethod = buffer.readByte();
        //指令
        byte command = buffer.readByte();
        //数据长度
        int len = buffer.readInt();
        byte[] bytes = new byte[len];
        buffer.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeMethod);

        if (requestType !=null && serializer!=null) {
            return serializer.deserializer(requestType, bytes);
        }
        return null;

    }


    public  Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }

    public Serializer getSerializer(byte serializeMethod){
        return serializerMap.get(serializeMethod);
    }

}
