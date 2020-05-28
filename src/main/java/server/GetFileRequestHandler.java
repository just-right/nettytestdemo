package server;

import io.netty.channel.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import protocol.GetFileRequestPacket;
import protocol.GetFileResponsePacket;
import protocol.SessionUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @className: GetFileRequestHandler
 * @description
 * @author: luffy
 * @date: 2020/5/22 14:28
 * @version:V1.0
 */
public class GetFileRequestHandler extends SimpleChannelInboundHandler<GetFileRequestPacket> {
    public static final GetFileRequestHandler INSTANCE = new GetFileRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GetFileRequestPacket requestPacket) throws Exception {
        GetFileResponsePacket packet = new GetFileResponsePacket();
        String fileName = requestPacket.getFileName();
        fileName = "D:\\test.txt";
        RandomAccessFile file = null;
        long len = -1;
        try {
            file = new RandomAccessFile(new File(fileName), "rw");
            len = file.length();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (len <= 0) {
                assert file != null;
                file.close();
            }
        }
        file.seek(0L);
        byte[] bytes = new byte[(int) len];
        if (file.read(bytes) != -1) {
            packet.setFileName(requestPacket.getFileName()).setStartPos(0).setSendFileFlag(true).setEndPos((int) len).setBytes(bytes);
        }
        if (ctx.pipeline().get(SslHandler.class) == null) {
            ctx.writeAndFlush(packet);
        } else {
            ctx.writeAndFlush(new ChunkedFile(file));
        }
    }
}
