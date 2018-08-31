import client.ClientController;
import org.apache.log4j.Logger;
import org.junit.Test;
import server.ServerController;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public class LogTest {
    private static Logger logger = Logger.getLogger(LogTest.class);
    public static void main(String[] args) {
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
    @Test
    public void serverControllerTest(){
        ServerController serverController = ServerController.getServerInstance();
        serverController.eventProcess("initialNetwork");
    }

    @Test
    public void clientInitial() throws IOException {
        ClientController clientController = ClientController.getServerInstance();
        clientController.client();
    }
}
