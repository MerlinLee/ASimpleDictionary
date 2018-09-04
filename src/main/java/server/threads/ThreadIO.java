package server.threads;

import org.apache.log4j.Logger;
import server.network.controllers.ModelController;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadIO implements Runnable{
    private final static String flag = "Thread IO: ";
    private String fileName;
    private static Logger logger = Logger.getLogger(ThreadIO.class);
    private static ConcurrentHashMap<String,String> dictionary = new ConcurrentHashMap<>();
    public ThreadIO(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        initialDict(fileName);
    }
    private static void initialDict(String fileName){
        try {
            Scanner read = new Scanner(new File(fileName));
            while (read.hasNext()){
                String word = read.next().toLowerCase();
                String meanings = read.nextLine();
                if(meanings.equals("\n")||meanings.equals(" ")){
                    continue;
                }
                dictionary.put(word,meanings);
            }
        } catch (java.io.IOException e) {
            logger.error(flag+e.toString());
        }
        ModelController.getInstance().setDictionary(dictionary);
    }
}
