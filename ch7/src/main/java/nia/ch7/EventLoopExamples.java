package nia.ch7;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/7/17 0:02 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class EventLoopExamples {

    public void executeTaskInEventLoop(){
        boolean terminated = false;
        //...
        while (!terminated){
        List<Runnable> readyEvents = blockUntilEventsReady();
            for (Runnable readyEvent : readyEvents) {
                //cxy 直接调用 run 方法
                readyEvent.run();
            }
        }
    }

    private List<Runnable> blockUntilEventsReady() {
        //cxy Collections.singletonList<T> 泛型使用
        return Collections.<Runnable>singletonList(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
