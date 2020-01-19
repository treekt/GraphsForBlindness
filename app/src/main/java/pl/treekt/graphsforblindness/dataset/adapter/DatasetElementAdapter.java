package pl.treekt.graphsforblindness.dataset.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.database.dao.DataSetElementDao;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.database.entity.DataSetElement;
import pl.treekt.graphsforblindness.observable.Observer;
import pl.treekt.graphsforblindness.observable.event.DataChangeEvent;

import java.util.ArrayList;


public class DatasetElementAdapter extends BaseAdapter implements Observer {

    private ArrayList<DataSetElement> dataSetElements;
    private LayoutInflater layoutInflater;

    private DataSetElementDao dataSetElementDao;

    private DataSet selectedDataSet;

    public DatasetElementAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;

        dataSetElementDao = new DataSetElementDao();
        dataSetElements = new ArrayList<>();
    }

    public void reloadData() {
        dataSetElements = dataSetElementDao.readAllByDataSetId(selectedDataSet.getId());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataSetElements.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return dataSetElements.get(position).getId();
    }

    @Override
    @SuppressLint({"ViewHolder", "InflateParams"})
    public View getView(int position, View view, ViewGroup parent) {
        view = layoutInflater.inflate(R.layout.dataset_elements_list_item, null);

        DataSetElement datasetElement = dataSetElements.get(position);

        TextView datasetElementTitle = view.findViewById(R.id.dataset_element_title_text);
        TextView datasetElementValue = view.findViewById(R.id.dataset_element_value_text);
        Button editButton = view.findViewById(R.id.dataset_element_update_button);
        Button deleteButton = view.findViewById(R.id.dataset_element_delete_button);

        datasetElementTitle.setText(datasetElement.getTitle());
        datasetElementValue.setText(datasetElement.getValue());
        deleteButton.setOnClickListener(v -> deleteDataSetElement(position));

        return view;
    }

    private void deleteDataSetElement(int position) {
        DataSetElement dataSetElement = dataSetElements.get(position);
        dataSetElementDao.delete(dataSetElement.getId());
        reloadData();
    }

    public void setSelectedDataSet(DataSet dataSet) {
        this.selectedDataSet = dataSet;
        reloadData();
    }

    @Override
    public void update(DataChangeEvent event) {
        setSelectedDataSet((DataSet) event.getData());
    }
}
