package pl.treekt.graphsforblindness.observable;

import pl.treekt.graphsforblindness.observable.event.DataChangeEvent;

public interface Observer {

    void update(DataChangeEvent event);
}
