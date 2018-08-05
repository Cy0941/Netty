package nia.ch9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Function: fixme readSlice 不应该是分片段读取？测试结果为依然读取所有内容<br/>
 * Reason: TODO ADD REASON(可选).<br/>
 * Date: 2018/8/5 10:29 <br/>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class FixedLengthFrameDecoderTest {

    @Test
    public void testFramesDecoded_2(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(channel.writeInbound(input.readBytes(2)));//没有一个完整的帧可供读取的帧
        assertTrue(channel.writeInbound(input.readBytes(7)));

        //read message
        ByteBuf read = channel.readInbound();
        ByteBuf readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---1" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        read = channel.readInbound();
        readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---2" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        read = channel.readInbound();
        readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---3" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFramesDecoded_1() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();
        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(channel.writeInbound(input.retain()));
        //标记 Channel 为已完成状态
        assertTrue(channel.finish());

        //read message
        ByteBuf read = channel.readInbound();
        ByteBuf readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---1" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        read = channel.readInbound();
        readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---2" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        read = channel.readInbound();
        readSlice = buf.readSlice(3);
        assertEquals(readSlice, read);
        System.out.println("---3" + Arrays.toString(readSlice.array()) + "====" + buf.readerIndex());
        read.release();

        assertNull(channel.readInbound());
        buf.release();
    }

}