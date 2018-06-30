package nia.ch5;

import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

/**
 * Function: ByteBuf - Netty 的数据容器；维护了两个不同的索引（readerIndex & writerIndex）<br>
 * Reason: TODO 名称以 read|write 开头的 ByteBuf 方法都将会推进其对应的索引；而名称以 set|get 开头的操作则不会.</br>
 * Date: 2018/6/30 22:07 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class ByteBufExamples {

    private final static Random random = new Random();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    //private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;

    public static void main(String[] args){
        byte[] arr = {0x68,0x04,0x07,0x00,0x00,0x00};
        ByteBuf byteBuf = Unpooled.copiedBuffer(arr);
        //String hexDump = ByteBufUtil.hexDump(byteBuf);
        //System.err.println(hexDump);
        while (byteBuf.isReadable()){
            for (int i = 0; i < byteBuf.capacity(); i++) {
                System.out.println(byteBuf.readByte());
            }
        }


        //byteBufWriteRead();
    }

    /*public void obtainingByteBufAllocatorReference(){
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        ByteBufAllocator allocator = channel.alloc();
        //...
        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE;
        ByteBufAllocator alloc = ctx.alloc();
    }*/

    /**
     * writerIndex 指向最后的可写入的位置
     */
    public static void byteBufWriteRead(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) byteBuf.getByte(0));
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        System.out.println("readerIndex: "+readerIndex+" ,writerIndex: "+writerIndex);
        byteBuf.writeByte('?');
        System.err.println("readerIndex: "+byteBuf.readerIndex()+" ,writerIndex: "+byteBuf.writerIndex());
        System.out.println(byteBuf.toString(utf8));
        System.err.println(byteBuf.maxCapacity());
    }

    public static void byteBufSetGet(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) byteBuf.getByte(0));
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        System.out.println("readerIndex: "+readerIndex+" ,writerIndex: "+writerIndex);
        byteBuf.setByte(0, 'B');
        System.err.println((char) byteBuf.getByte(0));
        assert readerIndex == byteBuf.readerIndex();
        assert writerIndex == byteBuf.writerIndex();
    }

    /**
     * copy() 数据不共享
     */
    public static void byteBufCopy(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        /**
         * Returns a copy of this buffer's sub-region. Modifying the content of the returned buffer or this buffer does not affect each other at all.
         * This method does not modify readerIndex or writerIndex of this buffer
         */
        ByteBuf copied = byteBuf.copy(0, 15);
        System.out.println(copied.toString(utf8)+" : "+byteBuf.toString(utf8));
        byteBuf.setByte(0,'J');
        System.err.println(copied.toString(utf8)+" : "+byteBuf.toString(utf8));
    }

    /**
     * 对 ByteBuf 进行切片<br>
     * sliced() 与原 ByteBuf 共享内容，仅维护自身的 index & mark
     */
    public static void byteBufSlice(){
        Charset utf8 = CharsetUtil.UTF_8;
        ByteBuf byteBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        /**
         * Returns a slice of this buffer's sub-region.
         * Modifying the content of the returned buffer or this buffer affects each other's content while they maintain separate indexes and marks.
         * This method does not modify readerIndex or writerIndex of this buffer.
         */
        ByteBuf sliced = byteBuf.slice(0, 15);
        System.out.println(sliced.toString(utf8));
        byteBuf.setByte(0,'J');
        System.err.println(sliced.toString(utf8));
    }

    public void write(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        while (byteBuf.writableBytes() > 0){
            byteBuf.writeInt(random.nextInt());
        }
    }

    /**
     * 读取所有数据<br>
     */
    public void readAllData(){
        ByteBuf byteBuf = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf temp = null;
        byteBuf.readBytes(temp);
        byteBuf.writeBytes(temp);
        while (byteBuf.isReadable()){
            System.out.println(byteBuf.readableBytes());
        }
    }

    /**
     * 随机访问索引：ByteBuf 的索引从 0 开始；最后一个字节的索引总是 capacity() -1
     */
    public void byteBufRelativeAccess(){
        ByteBuf buf = BYTE_BUF_FROM_SOMEWHERE;
        for (int i = 0; i < buf.capacity(); i++) {
            byte bufByte = buf.getByte(i);
            System.out.println((char) bufByte);
        }
    }

    /**
     * CompositeByteBuf 可能不支持直接访问其支撑数组 - 访问其中的数据类似于（访问）直接缓冲区的模式
     */
    public void byteBufCompositeArray() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        int length = compositeByteBuf.readableBytes();
        byte[] array = new byte[length];
        compositeByteBuf.getBytes(compositeByteBuf.readerIndex(), array);
        handleArray(array, 0, length);
    }

    /**
     * 符合缓冲区 - 为多个 ByteBuf 提供一个聚合视图
     */
    public void byteBufComposite() {
        CompositeByteBuf messageBuf = Unpooled.compositeBuffer();
        ByteBuf header = BYTE_BUF_FROM_SOMEWHERE;
        ByteBuf body = BYTE_BUF_FROM_SOMEWHERE;
        //将 ByteBuf 实例追加到 CompositeByteBuf
        messageBuf.addComponents(header, body);
        //cxy 删除位于索引位置为 0 的  ByteBuf ???
        //TODO 任何以 read|skip 开头的操作都将检索或者跳过位于当前 readerIndex 的数据，并且将它增加已读字节数
        messageBuf.removeComponent(0);
        for (ByteBuf buf : messageBuf) {
            System.out.println(buf.toString());
            if (buf.hasArray()) {
                //TODO
            }
        }
    }

    /**
     * 符合缓冲区 - 为多个 ByteBuf 提供一个聚合视图
     * 使用 ByteBuffer 的符合缓冲区实现
     */
    public void byteBufferComposite(ByteBuffer header, ByteBuffer body) {
        //use an array to hold the message parts
        ByteBuffer[] message = new ByteBuffer[]{header, body};
        //create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer msg = ByteBuffer.allocate(header.remaining() + body.remaining());
        msg.put(header);
        msg.put(body);
        msg.flip();
    }

    /**
     * 直接缓冲区 - JDK1.4后 ByteBuf 类允许 JVM实现通过本地调用来分配内存
     * cxy 直接缓冲区的内容将驻留在常规的会被垃圾回收的堆之外；相较于堆的缓冲区，分配和释放都较为昂贵
     */
    public void directBuffer() {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE;
        //cxy 如果 ByteBuf 不是一个支撑数组 -- 是一个直接缓冲区
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            handleArray(array, 0, length);
        }
    }

    /**
     * 支撑数组 - ByteBuf 将数据存放在 JVM 堆中；非常适合于有遗留数据需要处理的情况
     */
    public void heapBuffer() {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE;
        //检查ByteBuf是否有一个支撑数组
        if (heapBuf.hasArray()) {
            //获取数组引用
            byte[] array = heapBuf.array();
            //cxy 计算第一个直接的偏移量
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            //获取可读字节数
            int length = heapBuf.readableBytes();
            //数据处理
            handleArray(array, offset, length);
        }
    }

    private static void handleArray(byte[] array, int offset, int len) {
    }

}
