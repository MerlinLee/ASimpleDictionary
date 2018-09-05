import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;
import server.network.controllers.ServerController;

import java.net.URI;
import java.security.PrivateKey;

public class Main {
    public static void main(String[] args){
        //String arg = args[0];
        Main main = new Main();
        PropertyConfigurator.configure(Main.class.getResourceAsStream("log4j.properties"));
        ServerController serverController = ServerController.getServerInstance(args[1]);
        serverController.eventProcess("initialNetwork",args[0]);
    }
}
