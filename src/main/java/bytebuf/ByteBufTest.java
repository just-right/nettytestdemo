package bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author luffy
 **/
public class ByteBufTest {

    private static void printMsg(String action,ByteBuf buffer){
        System.out.println("after"+action);
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("maxCapacity:"+buffer.maxCapacity());
        System.out.println("readerIndex:"+buffer.readerIndex());
        System.out.println("readableBytes:"+buffer.readableBytes());
        System.out.println("isReadable:"+buffer.isReadable());

        System.out.println("writerIndex:"+buffer.writerIndex());
        System.out.println("isWritable:"+buffer.isWritable());
        System.out.println("maxWritableBytes:"+buffer.maxWritableBytes());
        System.out.println();
    }

    public static void main(String[] args){
        /**
         * Netty 对二进制数据的抽象 ByteBuf 的结构，
         * 本质上它的原理就是，它引用了一段内存，
         * 这段内存可以是堆内也可以是堆外的。
         *
         */
        //初始化ByteBuf，可写不可读
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10,100);
        printMsg("allocator buffer(10,100)",buffer);

        //写数据，改变写指针
        buffer.writeBytes(new byte[]{1,2,3,4});
        printMsg("writeBytes(1,2,3,4)",buffer);

        //写入int，占4字节
        buffer.writeInt(12);
        printMsg("writeInt(12)",buffer);

        //写数据，直到等于capacity
        buffer.writeBytes(new byte[]{5,6});
        printMsg("writeBytes(5,6)",buffer);

        //自动扩容--64B开始，指数扩容直至能装下
        buffer.writeBytes(new byte[]{7});
        printMsg("writeBytes(7)",buffer);

        System.out.println("getByte(3):"+buffer.getByte(3));
        //Short占两个字节-0000 0100 0000 0000
        System.out.println("getShort(3):"+buffer.getShort(3));
        //int占四个字节-0000 0100 0000 0000 0000 0000 0000 0000
        System.out.println("getInt(3):"+buffer.getInt(3));
        System.out.println();

        printMsg("getByte()",buffer);
        buffer.setByte(buffer.readableBytes()+1,0);
        printMsg("setByte()",buffer);

        //读数据，改变读指针，变为不可读
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        printMsg("readBytes:"+dst.length,buffer);

    }
}
