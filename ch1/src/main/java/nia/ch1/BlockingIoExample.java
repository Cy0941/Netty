package nia.ch1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Function: 阻塞IO实例
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2018/6/13 22:36 </br>
 *
 * @author: cx.yang
 * @since: yangcx.xin
 */
public class BlockingIoExample {

    public static void main(String[] args){

    }

    public void serve(int portNumber){
        try {
            //创建一个新的 ServerSocket 用以监听指定端口的连接请求
            ServerSocket serverSocket = new ServerSocket(portNumber);
            //cxy accept() 方法将一直阻塞，直到连接建立
            Socket clientSocket = serverSocket.accept();
            //这些流对象都派生于该套接字的流对象
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            String request,response;
            //循环处理
            while ((request = in.readLine()) != null){//cxy readLine() 方法同样会阻塞，直到一个换行符或回车符结尾的字符串被读取
                if ("Done".equals(request)){
                    break;
                }
                response = processRequest(request);
                //服务器的响应被发送给客户端
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String processRequest(String request) {
        return "Processed";
    }

}
