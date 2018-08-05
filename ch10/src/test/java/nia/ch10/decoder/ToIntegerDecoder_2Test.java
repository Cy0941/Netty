package nia.ch10.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.ch10.decoder.ToIntegerDecoder_2;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 15:38 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ToIntegerDecoder_2Test {

    @Test
    public void testDecode(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeInt(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder_2());
        channel.writeInbound(input);
        assertNotNull(channel.readInbound());
    }

}