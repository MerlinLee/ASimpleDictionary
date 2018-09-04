package server.network;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.Logger;
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
    private volatile static Networking networking;

    public static Networking getServerInstance() {
        if ( networking== null) {
            synchronized (Networking.class) {
                if (networking == null) {
                    networking = new Networking();
                    logger.info("Create a instance for Networking class, Successful.");
                }
            }
        }
        return networking;
    }
    private static ServerSocket server;
    private static Logger logger = Logger.getLogger(Networking.class);
    private static final int THREAD_POOL_SIZE = 3;
    public static void tcpServerByPool(int serverPort)throws Exception{
        server = new ServerSocket(8883);
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
