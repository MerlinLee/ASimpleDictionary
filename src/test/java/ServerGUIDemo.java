import org.apache.log4j.Logger;
import server.network.controllers.ServerController;

public class ServerGUIDemo {
    private ServerController serverController;
    private volatile static ServerGUIDemo serverGUIDemo;
    private ServerGUIDemo (){}
    private static Logger logger = Logger.getLogger(ServerGUIDemo.class);
    public static ServerGUIDemo getServerInstance() {
        if (serverGUIDemo == null) {
            synchronized (ServerGUIDemo.class) {
                if (serverGUIDemo == null) {
                    serverGUIDemo = new ServerGUIDemo();
                    logger.info("Create a instance for Server GUI class, Successful.");
                }
            }
        }
        return serverGUIDemo;
    }

    public void show(){

    }
}
