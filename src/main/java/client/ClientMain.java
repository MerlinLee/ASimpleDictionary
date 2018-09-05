package client;

public class ClientMain {
    public static void main(String[] args){
        ;ClientController clientController = ClientController.getServerInstance(Integer.parseInt(args[0]),args[1]);
        clientController.createUI();

    }
}
