package nia.ch13.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import nia.ch13.LogEvent;

import java.util.List;

/**
 * Function: 解码器：负责将传入的 DatagramPacket 解码为 LogEvent 消息<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 15:51 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf data = msg.content();
        int idx = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        //文件名 文件绝对路径
        String fileName = data.slice(0, idx).toString(CharsetUtil.UTF_8);
        String logMsg = data.slice(idx + 1, data.readableBytes()).toString(CharsetUtil.UTF_8);
        LogEvent event = new LogEvent(msg.sender(), fileName, logMsg, System.currentTimeMillis());
        out.add(event);
    }
}
