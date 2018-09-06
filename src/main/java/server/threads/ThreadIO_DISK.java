package server.threads;

import server.network.WordModel;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadIO_DISK implements Runnable{
    private String fileName;
    private WordModel wordModel;

    public ThreadIO_DISK(String fileName,WordModel wordModel) {
        this.fileName = fileName;
        this.wordModel = wordModel;
    }

    @Override
    public void run() {
        inputDisk(fileName,wordModel);
    }
    public static void inputDisk(String fileName,WordModel wordModel){
        try {
            FileWriter fw = new FileWriter(fileName, true);
            fw.write(wordModel.getWord()+" "+wordModel.getMeanings()+"\n");
            fw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
