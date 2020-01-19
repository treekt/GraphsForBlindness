package pl.treekt.graphsforblindness.dataset.observable;

public interface Observable {

    void setOnSelectedListener(Listener.OnSelectedListener  listener);

    void setOnChangeListener(Listener.OnChangeListener  listener);

    void detachOnSelectedListener(Listener.OnSelectedListener listener);

    void detachOnChangeListener(Listener.OnChangeListener listener);

    void notifyOnSelectedListeners();

    void notifyOnChangeListeners();
}
