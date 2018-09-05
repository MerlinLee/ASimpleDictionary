package server.threads;

import org.apache.log4j.Logger;
import server.network.controllers.ModelController;

import java.io.File;
import java.nio.charset.StandardCharsets;
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
            File file = new File(fileName);
            if(!file.exists()){
                logger.info(flag+"File not Found!");
            }else {
                logger.info(flag+"File Found");
            }
            Scanner read = new Scanner(file, StandardCharsets.UTF_8);
            while (read.hasNext()){
                String word = read.next().toLowerCase();
                String meanings = read.nextLine();
                if(meanings.equals("\n")||meanings.equals(" ")){
                    continue;
                }
                dictionary.put(word,meanings);
            }
            logger.info(flag+"Test "+dictionary.get("absolute"));
        } catch (java.io.IOException e) {
            logger.error(flag+e.toString());
        }
        ModelController.getInstance().setDictionary(dictionary);
    }
}
