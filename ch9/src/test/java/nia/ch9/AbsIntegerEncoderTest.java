package nia.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 11:59 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class AbsIntegerEncoderTest {


    @Test
    public void testEncoded(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        //read bytes
        for (int i = 0; i < 10; i++) {
            assertEquals(i,channel.readOutbound());
        }
        assertNull(channel.readOutbound());//cxy 读取 MessageToMessageEncoder#encode 方法包含 readerIndex 的 out 参数值
    }

}