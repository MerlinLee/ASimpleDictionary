package server.network.controllers;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import server.network.QueryModel;
import server.network.ServerThread;

/**
 * Model Controller
 *
 * @author MINGFENG LI
 * @date 04/09/2018
 */
public class ModelController {
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
        queryModel.setOperation("response");
        jsonData = JSON.toJSONString(queryModel);
        return jsonData;
    }
}
