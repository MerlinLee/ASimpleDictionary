package server.network.controllers;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import server.network.QueryModel;
import server.network.WordModel;
import server.threads.ThreadIO;
import server.threads.ThreadIO_DISK;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Model Controller
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class ModelController {
    private String fileName;
    private final static String flag = "Model Controller: ";
    public ConcurrentHashMap<String, String> getDictionary() {
        return dictionary;
    }

    public void setDictionary(ConcurrentHashMap<String, String> dictionary) {
        this.dictionary = dictionary;
    }

    private ConcurrentHashMap<String,String> dictionary = new ConcurrentHashMap<>();
    private static Logger logger = Logger.getLogger(ModelController.class);
    private volatile static ModelController modelController;
    public static ModelController getInstance(){
        if ( modelController== null) {
            synchronized (ModelController.class) {
                if (modelController == null) {
                    modelController = new ModelController();
                    logger.info("Create a instance for Model Controller class, Successful.");
                }
            }
        }
        return modelController;
    }

    public String JsonProcess(String jsonData){
        QueryModel queryModel = JSON.parseObject(jsonData,QueryModel.class);
        switchController(queryModel);
        jsonData = JSON.toJSONString(queryModel);
        return jsonData;
    }

    public void initialDict(String fileName){
        this.fileName = fileName;
        Thread thread = new Thread(new ThreadIO(fileName));
        thread.start();
        try {
            thread.join();
            logger.info(flag+"Initial Dict Complete!");
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
    }

    private void switchController(QueryModel queryModel){
         synchronized (this){
             switch (queryModel.getOperation()){
                 case "REMOVE":
                     if(dictionary.get(queryModel.getInfo())!=null){
                         dictionary.remove(queryModel.getInfo());
                         queryModel.setOperation("RESPONSE_REMOVE");
                         queryModel.setInfo("SUCCESS");
                     }else {
                         queryModel.setOperation("RESPONSE_REMOVE");
                         queryModel.setInfo("NOTFOUND");
                     }
                     break;
                 case "QUERY":
                     queryModel.setOperation("RESPONSE_QUERY");
                     if(queryModel.getInfo()!=null){
                         logger.info(flag+"recv info: "+queryModel.getInfo()+" dict :"+dictionary.get(queryModel.getInfo()));
                         queryModel.setInfo(dictionary.get(queryModel.getInfo()));
                     }else {
                         queryModel.setOperation("404");
                     }

                     break;
                 case "ADD":
                     WordModel wordModel = JSON.parseObject(queryModel.getInfo(),WordModel.class);
                     if(dictionary.get(wordModel.getWord())==null){
                         dictionary.put(wordModel.getWord(),wordModel.getMeanings());
                         queryModel.setOperation("RESPONSE_ADD");
                         queryModel.setInfo("SUCCESS");
                         Thread t1 = new Thread(new ThreadIO_DISK(fileName,wordModel));
                         t1.start();
                         try {
                             t1.join();
                         } catch (InterruptedException e) {
                             logger.error(e.toString());
                         }
                     }else {
                         queryModel.setOperation("RESPONSE_ADD");
                         queryModel.setInfo("WORD_EXIST");
                     }
                     break;
                 default:
                     break;
             }
         }
    }
}
