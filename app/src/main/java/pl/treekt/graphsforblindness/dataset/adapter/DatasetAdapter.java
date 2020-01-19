package pl.treekt.graphsforblindness.dataset.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.database.dao.DataSetDao;
import pl.treekt.graphsforblindness.database.dao.DataSetElementDao;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.dataset.observable.Observable;
import pl.treekt.graphsforblindness.dataset.observable.Listener;
import pl.treekt.graphsforblindness.dataset.observable.event.SelectionEvent;

import java.util.ArrayList;

public class DatasetAdapter extends BaseAdapter implements Observable {

    private ArrayList<Listener.OnSelectedListener> onSelectedListeners;
    private ArrayList<Listener.OnChangeListener> onChangeListeners;

    private ArrayList<DataSet> dataSets;
    private LayoutInflater layoutInflater;

    private DataSetDao dataSetDao;
    private DataSetElementDao dataSetElementDao;

    private DataSet selectedDataSet;

    public DatasetAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;

        onSelectedListeners = new ArrayList<>();
        onChangeListeners = new ArrayList<>();

        this.dataSetDao = new DataSetDao();
        this.dataSetElementDao = new DataSetElementDao();
        reloadData();
    }

    public void reloadData() {
        dataSets = dataSetDao.readAll();
        if(getCount() > 0) {
            setSelectedDataSetAtPosition(0);
        }else {
            this.selectedDataSet = null;
        }
        notifyDataSetChanged();
        notifyOnChangeListeners();
    }

    @Override
    public int getCount() {
        return dataSets.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataSets.get(position).getId();
    }

    @Override
    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.layout_dataset_list_item, null);

        DataSet dataset = dataSets.get(position);

        TextView datasetTitle = view.findViewById(R.id.dataset_title_text);
        TextView datasetPrefix = view.findViewById(R.id.dataset_prefix_text);
        Button editButton = view.findViewById(R.id.dataset_update_button);
        Button deleteButton = view.findViewById(R.id.dataset_delete_button);

        datasetTitle.setText(dataset.getTitle());
        datasetPrefix.setText(dataset.getPrefix());
        deleteButton.setOnClickListener(v -> deleteDataSet(position));

        setSelectedDataSetAtPosition(0);

        return view;
    }

    private void deleteDataSet(int position){
        DataSet dataSet = dataSets.get(position);
        dataSetElementDao.deleteAllByDataSetId(dataSet.getId());
        dataSetDao.delete(dataSet.getId());
        reloadData();
    }

    public void setSelectedDataSetAtPosition(int position) {
        if(getCount() == 0) return;

        this.selectedDataSet = dataSets.get(position);
        notifyOnSelectedListeners();
    }

    public DataSet getSelectedDataSet() {
        return selectedDataSet;
    }

    @Override
    public void setOnSelectedListener(Listener.OnSelectedListener listener) {
        onSelectedListeners.add(listener);
    }

    public void setOnChangeListener(Listener.OnChangeListener listener) {
        onChangeListeners.add(listener);
    }

    @Override
    public void detachOnSelectedListener(Listener.OnSelectedListener listener) {
        onSelectedListeners.remove(listener);
    }

    @Override
    public void detachOnChangeListener(Listener.OnChangeListener listener) {
        onSelectedListeners.remove(listener);
    }

    @Override
    public void notifyOnSelectedListeners() {
        onSelectedListeners.forEach(listener -> listener.update(new SelectionEvent(selectedDataSet)));
    }

    @Override
    public void notifyOnChangeListeners() {
        onChangeListeners.forEach(Listener.OnChangeListener::update);
    }


}
