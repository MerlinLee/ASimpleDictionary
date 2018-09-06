package client;

import org.apache.log4j.PropertyConfigurator;

public class ClientMain {
    public static void main(String[] args){
        ClientMain clientMain = new ClientMain();
        PropertyConfigurator.configure(ClientMain.class.getResourceAsStream("/log4j.properties"));
        ClientController clientController = ClientController.getServerInstance(Integer.parseInt(args[0]),args[1]);
        clientController.createUI();

    }
}
