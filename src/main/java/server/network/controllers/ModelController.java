package server.network.controllers;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import server.network.QueryModel;
import server.threads.ThreadIO;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Model Controller
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class ModelController {
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
        Thread thread = new Thread(new ThreadIO(fileName));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.error(e.toString());
        }
    }

    private void switchController(QueryModel queryModel){
        switch (queryModel.getOperation()){
                case "QUERY":
                     queryModel.setOperation("RESPONSE");
                     if(!queryModel.getInfo().equals("")){
                         queryModel.setInfo(dictionary.get(queryModel.getInfo()));
                     }else {
                         queryModel.setOperation("404");
                     }

                    break;
                case "ADD":
                    break;
                default:
                    break;
        }
    }
}
