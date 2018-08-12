package nia.ch12;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Function: 处理 FullHttpRequest 消息：请求路径包含 "file:" 则返回 index.html 否则按照原请求返回对应文件 <br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/7 22:39 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final String PATH_CONTAINS = "file:";

    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain()
                .getCodeSource().getLocation();
        try {
            String path = location.toURI() + "index.html";
            path = !path.contains(PATH_CONTAINS) ? path : path.substring(5);
            INDEX = new File(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(
                    "Unable to locate index.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //1、如果请求了 /ws 的 URI，增加引用并传递给下一个 ChannelInboundHandler
        if (wsUri.equalsIgnoreCase(request.uri())) {
            //cxy retain() 在 channelRead0() 方法完成后，将进行 request 对象资源的释放（release()方法）
            ctx.fireChannelRead(request.retain());
        } else {
            //2、处理 100 Continue 请求以符合 Http1.1 规范
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            //3、读取 index.html
            RandomAccessFile file = new RandomAccessFile(INDEX, "r");
            HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            //4、如果请求了 keep-alive，添加所需要的 HTTP 头信息
            boolean isKeepAlive = HttpUtil.isKeepAlive(request);
            if (isKeepAlive) {
                headers.set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            //5、将 HttpResponse 写到客户端
            ctx.write(response);
            //6、将 index.html 写到客户端
            if (null == ctx.pipeline().get(SslHandler.class)) {
                //cxy 如果不需要加密和压缩，利用零拷贝特性进行文件内容传输
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            //7、写 LastHttpContent 到客户端，作为标记响应结束
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            //8、如果没有请求 keep-alive，在写操作完成后关闭 Channel
            if (!isKeepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
