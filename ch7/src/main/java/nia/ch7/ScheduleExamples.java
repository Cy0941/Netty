package nia.ch7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/7/30 22:46 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ScheduleExamples {

    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void cancelingTaskUsingScheduledFuture(){
        ScheduledFuture<?> future = CHANNEL_FROM_SOMEWHERE.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("....");
            }
        }, 60, 60, TimeUnit.SECONDS);
        //cxy 取消该任务，防止再次运行
        future.cancel(false);
    }

    public static void scheduleViaEventLoop() {
        CHANNEL_FROM_SOMEWHERE.eventLoop().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("....");
            }
        }, 60, 60, TimeUnit.SECONDS);


        ScheduledFuture<?> future1 = CHANNEL_FROM_SOMEWHERE.eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);
    }

    /**
     * 作为线程池管理的一部分，将会有额外的线程创建
     *
     * @throws Exception
     */
    public static void schedule() throws Exception {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        pool.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("60 seconds later");
            }
        }, 60, TimeUnit.SECONDS);//延迟60s执行
        //释放资源
        pool.shutdown();
    }

}
