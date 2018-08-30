package server;

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
    private volatile static ServerController ServerController;
    private ServerController (){}
    public static ServerController getServerInstance() {
        if (ServerController == null) {
            synchronized (ServerController.class) {
                if (ServerController == null) {
                    ServerController = new ServerController();
                }
            }
        }
        return ServerController;
    }

    @Override
    public Message send() {
        return null;
    }

    @Override
    public void receive() {

    }

}
