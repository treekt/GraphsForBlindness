package pl.treekt.graphsforblindness.observable.event;

public class DataChangeEvent {

    private Object data;

    public DataChangeEvent(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
