package client;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

public class ClientController {
    private static Logger logger = Logger.getLogger(ClientController.class);
    private volatile static ClientController clientController;

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
}
