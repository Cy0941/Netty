package nia.ch13.encoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import nia.ch13.LogEvent;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/12 15:18 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class LogEventBroadcaster {

    /**
     * 文件中的内容将会通过 UDP 广播到该端口
     */
    private static final int PORT = 8888;
    private static final String FILE_PATH = "";

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }


    public static void main(String[] args) throws Exception {
        File file = new File(FILE_PATH);
        InetSocketAddress address = new InetSocketAddress("255.255.255.255", PORT);
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(address, file);
        try {
            broadcaster.run();
        } finally {
            broadcaster.stop();
        }
    }

    public void run() throws Exception {
        //fixme 绑定端口为0？？？
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        while (true) {
            long length = file.length();
            if (length < pointer) {
                //file was reset
                pointer = length;
            } else {
                //content was added
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                //设置当前文件的指针，以确保没有旧的日志文件被发送
                raf.seek(pointer);
                String line;
                //fixme readLine 方法会移动指针？？
                while ((line = raf.readLine()) != null) {
                    channel.writeAndFlush(new LogEvent(null, file.getAbsolutePath(), line, -1));
                }
                pointer = raf.getFilePointer();
                raf.close();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

}
