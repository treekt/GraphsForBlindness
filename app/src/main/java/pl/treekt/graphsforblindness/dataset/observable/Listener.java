package pl.treekt.graphsforblindness.dataset.observable;

import pl.treekt.graphsforblindness.dataset.observable.event.SelectionEvent;

public class Listener {

    public interface OnSelectedListener {
        void update(SelectionEvent event);
    }

    public interface OnChangeListener {
        void update();
    }


}
