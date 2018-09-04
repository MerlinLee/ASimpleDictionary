package server.threads;

import org.apache.log4j.Logger;
import server.network.Networking;
import server.network.controllers.ServerController;

public class NetworkingThread implements Runnable{
    private static Logger logger = Logger.getLogger(NetworkingThread.class);
    private final static String flag = "Thread Networking: ";
    private int portNumber;

    public NetworkingThread(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        threadInitialNet(portNumber);
    }
    private static void threadInitialNet(int port){
        Networking networking = Networking.getServerInstance(ServerController.getServerInstance());
        try {
            networking.tcpServerByPool(port);
            logger.info(flag+"Initial Network!");
        } catch (Exception e) {
            networking.close();
            logger.error(flag+e.toString());
        }
    }
}
