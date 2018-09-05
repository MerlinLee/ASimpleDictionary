package server.network.controllers;

import org.apache.log4j.Logger;
import server.Message;
import server.MessageCentre;
import server.network.Networking;
import server.threads.NetworkingThread;

/**
*
*  GUI for Server
 *  *
 *  * @author MINGFENG LI
 *  * @date 30/08/2018
* Design Pattern: Singleton
*
*/
public class ServerController implements MessageCentre {
    private final static String flag = "Server Controller: ";
    private static Logger logger = Logger.getLogger(ServerController.class);
    private volatile static ServerController serverController;
    private ModelController modelController;
    private Thread networkThread;
    private ServerController (String fileName){
        eventProcess("initialDict",fileName);
    }

    public ServerController() {}

    public static ServerController getServerInstance(String fileName) {
        if (serverController == null) {
            synchronized (ServerController.class) {
                if (serverController == null) {
                    serverController = new ServerController(fileName);
                    logger.info("Create a instance for ServerController class, Successful.");
                }
            }
        }
        return serverController;
    }

    public static ServerController getServerInstance() {
        if (serverController == null) {
            synchronized (ServerController.class) {
                if (serverController == null) {
                    serverController = new ServerController();
                    logger.info("Create a instance for ServerController class, Successful.");
                }
            }
        }
        return serverController;
    }
    private int initialSocket(int portNumber){
        int status = 0;
        networkThread = new Thread(new NetworkingThread(portNumber));
        networkThread.start();
        return status;
    }

    private int initialDict(String fileName){
        if(fileName.equals("")){
            logger.error("Cannot find the dictionary file!");
            return -1;
            //FALSE
        }
        Thread ioThreadInitial = new Thread(()->{
            modelController = ModelController.getInstance();
            modelController.initialDict(fileName);
        });
        ioThreadInitial.start();
        try {
            ioThreadInitial.join();
        } catch (InterruptedException e) {
            logger.error(flag+e.toString());
        }
        try {
            ioThreadInitial.join();
        } catch (InterruptedException e) {
            logger.error(flag+e.toString());
        }
        return 0;
        //SUCCESS
    }
    @Override
    public Message send() {
        return null;
    }

    @Override
    public void receive() {

    }

    public void eventProcess(String key,String value){
        switch (key){
            case "initialNetwork":
                initialSocket(Integer.parseInt(value));
                break;
            case "initialDict":
                int code = initialDict(value);
                if(code==-1){
                    logger.error(flag+"Initial Dictionary Fail!");
                }
                break;
            default:
                break;
        }
    }
}
