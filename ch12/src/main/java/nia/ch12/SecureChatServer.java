package nia.ch12;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import java.net.InetSocketAddress;

/**
 * Function: ChatServer 加密<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 12:00 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class SecureChatServer extends ChatServer {

    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group,context);
    }

    public static void main(String[] args) throws Exception {
        Integer port = 9999;
        SelfSignedCertificate cert = new SelfSignedCertificate();
        SslContextBuilder contextBuilder = SslContextBuilder.forServer(
                cert.certificate(), cert.privateKey()
        );
        SslContext context = contextBuilder.build();
        final SecureChatServer endpoint = new SecureChatServer(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
