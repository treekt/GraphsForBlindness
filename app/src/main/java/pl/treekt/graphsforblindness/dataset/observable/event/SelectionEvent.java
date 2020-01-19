package pl.treekt.graphsforblindness.dataset.observable.event;

public class SelectionEvent {

    private Object data;

    public SelectionEvent(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
