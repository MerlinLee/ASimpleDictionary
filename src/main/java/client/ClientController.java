package client;

import client.UI.ClientUI;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.Logger;
import server.network.QueryModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.*;

public class ClientController {
    private final static String flag = "Client Controller";
    private static Logger logger = Logger.getLogger(ClientController.class);
    private volatile static ClientController clientController;
    private ThreadFactory threadFactory;

    public ClientUI getClientUI() {
        if (clientUI!=null){
            return clientUI;
        }else {
            return ClientUI.getServerInstance();
        }
    }

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    private ClientUI clientUI;
    private ExecutorService pool;
    private QueryModel queryModel;
    private int portNum;
    private String ipAddr;

    public ClientController(int portNum,String ipAddr) {
        threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("Client-pool-%d").build();
        pool = new ThreadPoolExecutor(1,2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024),threadFactory,new ThreadPoolExecutor.AbortPolicy());
        this.portNum = portNum;
        this.ipAddr = ipAddr;
        clientUI = ClientUI.getServerInstance();
    }

    public ClientController() {
    }

    public static ClientController getServerInstance(int portNum,String ipAddr) {
        if (clientController == null) {
            synchronized (ClientController.class) {
                if (clientController == null) {
                    clientController = new ClientController(portNum,ipAddr);
                    logger.info("Create a instance for Client Controller class, Successful.");
                }
            }
        }
        return clientController;
    }

    public static ClientController getServerInstance() {
        if (clientController == null) {
            synchronized (ClientController.class) {
                if (clientController == null) {
                    clientController = new ClientController();
                    logger.info("Create a instance for Client Controller class, Successful.");
                }
            }
        }
        return clientController;
    }
    public void createQuery(){
        pool.execute(new ClientNetwork(portNum,ipAddr,queryModel));
    }

    public void createUI(){
        ClientUI.initialUI();
    }

    public void createModel(String operation, String info){
        queryModel = new QueryModel();
        queryModel.setOperation(operation);
        queryModel.setInfo(info);
        createQuery();
    }
}
