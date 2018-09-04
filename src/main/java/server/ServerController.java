package server;

import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

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
    private static Logger logger = Logger.getLogger(ServerController.class);
    private volatile static ServerController ServerController;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ServerController (){}
    public static ServerController getServerInstance() {
        if (ServerController == null) {
            synchronized (ServerController.class) {
                if (ServerController == null) {
                    ServerController = new ServerController();
                    logger.info("Create a instance for ServerController class, Successful.");
                }
            }
        }
        return ServerController;
    }
    private int initialSocket(){
        int status = 0;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(9898));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while(selector.select() > 0){
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while(it.hasNext()){
                    SelectionKey sk = it.next();
                    if(sk.isAcceptable()){
                        SocketChannel sChannel = serverSocketChannel.accept();
                        serverSocketChannel.configureBlocking(false);
                        sChannel.register(selector,SelectionKey.OP_WRITE);
                    }else if(sk.isReadable()){
                        SocketChannel sChannel = (SocketChannel) sk.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        int len = 0;
                        while((len = sChannel.read(buf)) > 0 ){
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    }
                    it.remove();
                }
            }
        }catch (Exception e){
            status = -1;
            logger.error(e.toString());
        }

        return status;
    }
    @Override
    public Message send() {
        return null;
    }

    @Override
    public void receive() {

    }

    public void eventProcess(String key){
        switch (key){
            case "initialNetwork": initialSocket();
            default:
                break;
        }
    }
}
