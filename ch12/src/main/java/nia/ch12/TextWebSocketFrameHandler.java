package nia.ch12;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import static io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler.ServerHandshakeStateEvent;

/**
 * Function: 仅处理握手完成后，否则交给下一个 Handler 处理<br/>
 * Reason: TODO 握手完成后添加及通知新入 Channel；发送消息<br/>
 * Date: 2018/8/7 23:26 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 保存所有已连接的 WebSocket Channel
     */
    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    /**
     * 处理自定义事件
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //cxy 如果握手完成，从当前 Pipeline 中移除 HttpRequestHandler -- 不再接收任何 HTTP 消息
        if (evt == ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            ctx.pipeline().remove(HttpRequestHandler.class);
            //通知所有已连接的 WebSocket 有新的客户端加入
            group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
            //将新的 WebSocket Channel 添加到 ChannelGroup 中以接收消息
            group.add(ctx.channel());
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //cxy 当 channelRead0 方法返回时，TextWebSocketFrame 的引用计数将会被减少；由于所有操作都是异步的，writeAndFlush 可能会在
        //cxy channelRead0 方法返回之后完成，而且它绝不能访问一个已经失效的引用
        group.writeAndFlush(msg.retain());
    }
}
