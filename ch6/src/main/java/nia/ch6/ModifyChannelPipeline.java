package nia.ch6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DummyChannelPipeline;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/7/4 0:09 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ModifyChannelPipeline {

    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DummyChannelPipeline.DUMMY_INSTANCE;

    public void modifyPipeline() {
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE;
        FirstHandler firstHandler = new FirstHandler();
        pipeline.addLast("handler_1", firstHandler);
        SecondHandler secondHandler = new SecondHandler();
        pipeline.addFirst("handler_2", secondHandler);
        //...
        ThirdHandler thirdHandler = new ThirdHandler();
        pipeline.addLast("handler_3", thirdHandler);
        //remove
        pipeline.remove("handler_2");
        //pipeline.remove(SecondHandler.class);
        //replace
        FourthHandler fourthHandler = new FourthHandler();
        pipeline.replace("handler_3", "handler_4", fourthHandler);

        pipeline.names();
    }

    private static final class FirstHandler
            extends ChannelHandlerAdapter {

    }

    private static final class SecondHandler
            extends ChannelHandlerAdapter {

    }

    private static final class ThirdHandler
            extends ChannelHandlerAdapter {

    }

    private static final class FourthHandler
            extends ChannelHandlerAdapter {

    }

}
