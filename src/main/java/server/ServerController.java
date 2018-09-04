package server;

import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
*
*  GUI for Server
 *  *
 *  * @author MINGFENG LI
 *  * @date 30/08/2018
* Design Pattern: Singleton
*
*/
public class ServerController implements MessageCentre{
    private static Logger logger = Logger.getLogger(ServerController.class);
    private volatile static ServerController serverController;
    private ServerController (String fileName){
        eventProcess("initialDict",fileName);
    }
    public static ServerController getServerInstance(String flieName) {
        if (serverController == null) {
            synchronized (ServerController.class) {
                if (serverController == null) {
                    serverController = new ServerController(flieName);
                    logger.info("Create a instance for ServerController class, Successful.");
                }
            }
        }
        return serverController;
    }
    private int initialSocket(){
        int status = 0;

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
