package client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.GetFileResponsePacket;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * @className: GetFileResponseHandler
 * @description
 * @author: luffy
 * @date: 2020/5/22 15:04
 * @version:V1.0
 */
@ChannelHandler.Sharable
public class GetFileResponseHandler extends SimpleChannelInboundHandler<GetFileResponsePacket> {
    public static final GetFileResponseHandler INSTANCE = new GetFileResponseHandler();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetFileResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSendFileFlag() && responsePacket.getFileName()!=null) {
            int start = responsePacket.getStartPos();
            byte[] bytes = responsePacket.getBytes();
            String fileName = responsePacket.getFileName();
            String path = "D:\\"+ UUID.randomUUID()+fileName;
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(path), "rw");
            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
        }
    }
}
