import com.alibaba.fastjson.JSON;
import server.network.QueryModel;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        try {
            QueryModel queryModel = new QueryModel();
            queryModel.setOperation("ADD");
            queryModel.setInfo("meanings");
             Socket socket = new Socket("localhost",PORT);
             OutputStream os = socket.getOutputStream();
             PrintWriter pw = new PrintWriter(os);
             pw.write(JSON.toJSONString(queryModel)+"\n");
             pw.flush();
             socket.shutdownOutput();
             InputStream is = socket.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is));
             String info = null;
             while((info=br.readLine())!=null){
                 System.out.println("我是客户端,服务器说: "+info);
            }
            socket.shutdownInput();
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
         } catch (UnknownHostException e) {
               e.printStackTrace();
         } catch (IOException e) {
              e.printStackTrace();
         }
    }

    }

