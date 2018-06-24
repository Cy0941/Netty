package nia.ch4.without_netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Function: 未使用Netty的阻塞网络编程
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/24 22:03 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class PlainOioServer {

    public void serve(int port) throws IOException{
        //服务器绑定到固定端口
        final ServerSocket serverSocket = new ServerSocket(port);
        try {
            for (;;){
                //接受连接 cxy 阻塞
                final Socket socket = serverSocket.accept();
                System.out.println("Accept connection from "+socket);
                //创建新线程处理连接
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = socket.getOutputStream();
                            //将消息写到客户端
                            out.write("Hi!\r\n".getBytes(Charset.forName("utf-8")));
                            out.flush();
                            socket.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();//启动线程
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
