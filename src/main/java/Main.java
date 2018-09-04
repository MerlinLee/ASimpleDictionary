import server.network.controllers.ServerController;

public class Main {
    public static void main(String[] args){
        //String arg = args[0];
        ServerController serverController = ServerController.getServerInstance(args[0]);
    }
}
