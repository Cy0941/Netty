package nia.ch13.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import nia.ch13.LogEvent;

/**
 * Function: 处理经 LogEventDecoder 节码得到 LogEvent 对象<br/>
 * Reason: TODO 简单处理为打印到控制台<br/>
 * Date: 2018/8/12 16:00 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(msg.getReceived());
        builder.append("[");
        builder.append(msg.getSource().toString());
        builder.append("] [");
        builder.append(msg.getLogFile());
        builder.append("] : ");
        builder.append(msg.getMsg());
        System.out.println(builder.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
