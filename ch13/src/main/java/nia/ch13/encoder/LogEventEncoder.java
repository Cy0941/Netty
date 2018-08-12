package nia.ch13.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import nia.ch13.LogEvent;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 15:07 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    /**
     * 创建即将被发送到指定 address 的 DatagramPacket
     */
    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        byte[] file = logEvent.getLogFile().getBytes(CharsetUtil.UTF_8);
        byte[] msg = logEvent.getMsg().getBytes(CharsetUtil.UTF_8);
        int length = file.length + msg.length;
        ByteBuf buffer = ctx.alloc().buffer(length);
        buffer.writeBytes(file);
        //写入一个分隔符
        buffer.writeByte(LogEvent.SEPARATOR);
        buffer.writeBytes(msg);
        //TODO 将一个拥有数据和地址的新 DatagramPacket 添加到出站消息列表中
        //fixme 消息会被发送到 address 配置的端口？？？
        out.add(new DatagramPacket(buffer, remoteAddress));
    }
}
