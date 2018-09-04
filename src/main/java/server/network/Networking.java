package server.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.Logger;
import server.network.controllers.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
/**
 * Networking class
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class Networking {
    private ServerController serverController;
    private volatile static Networking networking;

    public Networking(ServerController serverController) {
        this.serverController = serverController;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    private int serverPort;

    public static Networking getServerInstance(ServerController serverController) {
        if ( networking== null) {
            synchronized (Networking.class) {
                if (networking == null) {
                    networking = new Networking(serverController);
                    logger.info("Create a instance for Networking class, Successful.");
                }
            }
        }
        return networking;
    }
    private static ServerSocket server;
    private static Logger logger = Logger.getLogger(Networking.class);
    public  void tcpServerByPool(int serverPort)throws Exception{
        if(serverPort<=0){
            serverPort = 8883;
            logger.debug("NONE PORT NUMBER, SET DEFAULT PORT 8883");
        }
        server = new ServerSocket(serverPort);
        Socket client = null;
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("client-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5,20,
                0L,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),threadFactory,new ThreadPoolExecutor.AbortPolicy());
        boolean f = true;
        while (f){
            client = server.accept();
            logger.info("Successful to connect with a client!");
            pool.execute(new ServerThread(client));
        }
    }
    public void close(){
        try {
            server.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }
}
