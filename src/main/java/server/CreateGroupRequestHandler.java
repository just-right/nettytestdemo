package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocol.CreateGroupRequestPacket;
import protocol.CreateGroupResponsePacket;
import protocol.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author luffy
 * @version 1.0
 * @date 2019/12/22 21:31
 **/
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket){
        List<String> userIdList = createGroupRequestPacket.getUserIdList();


        List<String> userNameList = new ArrayList<>();
        ChannelGroup group  = new DefaultChannelGroup(channelHandlerContext.executor());

        for (String userId:userIdList){
            Channel channel =SessionUtil.getChannel(userId);
            if (channel!=null){
                group.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(randomGroupId());
        responsePacket.setUserNameList(userNameList);

        group.writeAndFlush(responsePacket);
        System.out.println("群创建成功！群ID："+responsePacket.getGroupId());
        System.out.println("群成员包括："+responsePacket.getUserNameList());






    }
    private static String randomGroupId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
