package server.network;

import java.net.Socket;
/**
 * Server Thread
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class ServerThread implements Runnable {
    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        addClient(client);
    }
    public static void addClient(Socket client){
        //operation here
    }
}
