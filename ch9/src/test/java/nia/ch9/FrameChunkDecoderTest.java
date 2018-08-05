package nia.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Function: TODO<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 12:27 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class FrameChunkDecoderTest {

    @Test
    public void testFramesDecoded(){
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf input = buffer.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));
        assertTrue(channel.writeInbound(input.readBytes(2)));

        //写入4个字节的帧，捕获预期的异常
        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e) {

        }

        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        //read frames
        ByteBuf read = channel.readInbound();
        assertEquals(buffer.readSlice(2),read);
        read.release();

        read = channel.readInbound();
        System.out.println(buffer.readerIndex());
        assertEquals(buffer.skipBytes(4).readSlice(3),read);
        read.release();
        buffer.release();
    }

}