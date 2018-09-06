package client;

import client.UI.ClientUI;
import com.alibaba.fastjson.JSON;
import server.network.QueryModel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ClientNetwork implements Runnable {
    private int portNumber;
    private String ipAddr;
    private QueryModel queryModel;

    public ClientNetwork(int portNumber, String ipAddr,QueryModel queryModel) {
        this.portNumber = portNumber;
        this.ipAddr = ipAddr;
        this.queryModel = queryModel;
    }

    @Override
    public void run() {
        initialClient(portNumber,ipAddr,queryModel);
    }

    private static void initialClient(int portNumber, String ipAddr, QueryModel queryModel){
        try {
            QueryModel query = queryModel;
            Socket socket = new Socket(ipAddr,portNumber);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write(JSON.toJSONString(queryModel)+"\n");
            pw.flush();
            socket.shutdownOutput();
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while((info=br.readLine())!=null){
                try {
                    ClientController.getServerInstance().getClientUI()
                            .getjTextArea()
                            .setText(JSON.parseObject(info,QueryModel.class).getInfo());
                    ClientController.getServerInstance().getClientUI()
                            .getjTextArea_add_meanings()
                            .setText(JSON.parseObject(info,QueryModel.class).getInfo());
                }catch (Exception e){
                    System.out.println(e.toString());
                }
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
