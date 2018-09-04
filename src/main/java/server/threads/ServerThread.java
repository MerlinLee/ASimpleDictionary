package server.threads;

import org.apache.log4j.Logger;
import server.network.controllers.ModelController;

import java.io.*;
import java.net.Socket;
/**
 * Server Thread
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class ServerThread implements Runnable {
    private Socket client;
    private final static String flag = "Server Thread: ";
    private static Logger logger = Logger.getLogger(ServerThread.class);
    public static String getJsonData() {
        return jsonData;
    }

    public static void setJsonData(String jsonData) {
        ServerThread.jsonData = jsonData;
    }

    private static String jsonData;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        addClient(client);
    }
    public static void addClient(Socket client){
        //operation here
        InputStream is = null;
        OutputStream os = null;
        PrintWriter pw = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            is = client.getInputStream();
            isr=new InputStreamReader(is);
            br=new BufferedReader(isr);
            String info = null;
            while((info=br.readLine())!=null){
                //communication
                logger.info(flag+info);
                jsonData = ModelController.getInstance().JsonProcess(info);
            }
            client.shutdownInput();
            os = client.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(jsonData+"\n");
            pw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try {
                if(pw!=null) {
                    pw.close();
                }
                if(os!=null){
                    os.close();
                }
                if(br!=null) {
                    br.close();
                }
                if(isr!=null) {
                    isr.close();
                }
                if(is!=null) {
                    is.close();
                }
                if(client!=null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        }

    public void receiveData(String json){
        setJsonData(json);
    }
}
