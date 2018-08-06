package nia.ch11;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * Function: Netty 服务端实现支持 WebSocket<br/>
 * Reason: TODO 客户端和服务端通过HTTP(S)建立连接，确认后将连接协议升级为 WebSocket；对应的消息类型称为数据帧|控制帧<br/>
 * Date: 2018/8/5 23:06 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    private final SslContext context;

    public WebSocketServerInitializer(SslContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.newEngine(ch.alloc());
        pipeline.addLast(
                //cxy 保护 WebSocket，将 SslHandler 作为第一个 ChannelHandler
                new SslHandler(engine),
                new HttpServerCodec(),
                //为握手提供聚合的 Handler
                new HttpObjectAggregator(65536),
                //cxy 如果请求是 /websocket 则升级请求
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler());
    }


    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            //Handle text frame
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
            //Handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            //Handle continuation frame
        }
    }
}
