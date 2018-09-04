package server.network.controllers;

import org.apache.log4j.Logger;
import server.Message;
import server.MessageCentre;
import server.network.Networking;

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
    private static Logger logger = Logger.getLogger(ServerController.class);
    private volatile static ServerController serverController;
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
    private int initialSocket(){
        int status = 0;
        networkThread = new Thread(()->{
            String flag = "Network Thread: ";
            Networking networking = Networking.getServerInstance(ServerController.getServerInstance());
            try {
                networking.tcpServerByPool(9999);
                logger.info(flag+"Initial Network!");
            } catch (Exception e) {
                networking.close();
                logger.error(flag+e.toString());
            }
        });

        return status;
    }

    private void initialDict(String fileName){
        if(fileName.equals("")){
            logger.error("Cannot find the dictionary file!");
        }
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
            case "initialNetwork": initialSocket();
            case "initialDict": initialDict(value);
            default:
                break;
        }
    }
}
