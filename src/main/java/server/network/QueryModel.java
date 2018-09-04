package server.network;

/**
 * Model for Query
 *
 * @author keriezhang
 * @date 2016/10/31
 */
public class QueryModel{
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String operation;
    private String info;
}
