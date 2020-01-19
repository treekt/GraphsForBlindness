package pl.treekt.graphsforblindness.dataset;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import pl.treekt.graphsforblindness.R;
import pl.treekt.graphsforblindness.database.dao.DataSetDao;
import pl.treekt.graphsforblindness.database.dao.DataSetElementDao;
import pl.treekt.graphsforblindness.database.entity.DataSet;
import pl.treekt.graphsforblindness.database.entity.DataSetElement;
import pl.treekt.graphsforblindness.dataset.adapter.DatasetAdapter;
import pl.treekt.graphsforblindness.dataset.adapter.DatasetElementAdapter;

public class DatasetActivity extends AppCompatActivity {

    private DataSetDao dataSetDao;
    private DataSetElementDao dataSetElementDao;

    private DatasetAdapter datasetAdapter;
    private DatasetElementAdapter datasetElementAdapter;

    private ListView dataSetListView;
    private ListView dataSetElementsListView;

    private LinearLayout dataSetElementsSectionLayout;

    private DataSet editedDataSet;
    private DataSetElement editedDataSetElement;

    private Dialog editDataSetDialog;
    private Dialog editDataSetElementDialog;

    @Override
    @SuppressLint("InflateParams")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_dataset);

        dataSetDao = new DataSetDao();
        dataSetElementDao = new DataSetElementDao();

        dataSetElementsSectionLayout = findViewById(R.id.data_set_elements_list_section);

        dataSetElementsListView = findViewById(R.id.dataset_elements_list);
        dataSetElementsListView.setClickable(true);
        datasetElementAdapter = new DatasetElementAdapter(getLayoutInflater());
        datasetElementAdapter.setOnClickListener((event) -> {
            editedDataSetElement = (DataSetElement) event.getData();
            editDataSetElementDialog.show();
        });
        dataSetElementsListView.setAdapter(datasetElementAdapter);

        dataSetListView = findViewById(R.id.dataset_list);
        dataSetListView.setClickable(true);
        datasetAdapter = new DatasetAdapter(getLayoutInflater());
        datasetAdapter.setOnSelectedListener(datasetElementAdapter);
        datasetAdapter.setOnChangeListener(this::validateDataSets);
        datasetAdapter.setOnClickListener((event) -> {
            editedDataSet = (DataSet) event.getData();
            editDataSetDialog.show();
        });
        dataSetListView.setAdapter(datasetAdapter);


        dataSetListView.setOnItemClickListener((parent, view, position, id) -> {
            datasetAdapter.setSelectedDataSetAtPosition(position);
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        prepareNewDataSetDialog(builder);
        prepareEditDataSetDialog(builder);
        prepareNewDataSetElementDialog(builder);
        prepareEditDataSetElementDialog(builder);


        validateDataSets();
    }

    @SuppressLint("InflateParams")
    private void prepareNewDataSetDialog(AlertDialog.Builder builder) {
        View dataSetDialogView = getLayoutInflater().inflate(R.layout.dialog_data_set, null);
        EditText titleEditText = dataSetDialogView.findViewById(R.id.data_set_title_edit_text);
        EditText prefixEditText = dataSetDialogView.findViewById(R.id.data_set_prefix_edit_text);
        AlertDialog newDataSetDialog = builder
                .setTitle(getString(R.string.new_data_set_dialog_title))
                .setView(dataSetDialogView)
                .setPositiveButton(getString(R.string.add_button_label), (dialog, which) -> onNewDataSet(
                        titleEditText,
                        prefixEditText
                ))
                .setNegativeButton(getString(R.string.cancel_button_label),
                        (dialog, which) -> {
                            clearDataSetDialog(titleEditText, prefixEditText);
                            dialog.cancel();
                        })
                .create();

        Button newDataSetButton = findViewById(R.id.new_dataset_button);
        newDataSetButton.setOnClickListener(v -> newDataSetDialog.show());
    }

    private void onNewDataSet(EditText titleEditText, EditText prefixEditText) {
        DataSet dataSet = new DataSet();
        dataSet.setTitle(titleEditText.getText().toString());
        dataSet.setPrefix(prefixEditText.getText().toString());
        dataSetDao.save(dataSet);
        datasetAdapter.reloadData();
        clearDataSetDialog(titleEditText, prefixEditText);
    }

    @SuppressLint("InflateParams")
    private void prepareEditDataSetDialog(AlertDialog.Builder builder) {
        View dataSetDialogView = getLayoutInflater().inflate(R.layout.dialog_data_set, null);

        EditText titleEditText = dataSetDialogView.findViewById(R.id.data_set_title_edit_text);
        EditText prefixEditText = dataSetDialogView.findViewById(R.id.data_set_prefix_edit_text);
        editDataSetDialog = builder
                .setTitle(getString(R.string.edit_data_set_dialog_title))
                .setView(dataSetDialogView)
                .setPositiveButton(getString(R.string.edit_button_label), (dialog, which) -> onEditDataSet(
                        titleEditText,
                        prefixEditText
                ))
                .setNegativeButton(getString(R.string.cancel_button_label),
                        (dialog, which) -> {
                            clearDataSetDialog(titleEditText, prefixEditText);
                            dialog.cancel();
                        })
                .create();

    }

    private void onEditDataSet(EditText titleEditText, EditText prefixEditText) {
        editedDataSet.setTitle(titleEditText.getText().toString());
        editedDataSet.setPrefix(prefixEditText.getText().toString());
        dataSetDao.update(editedDataSet);
        datasetAdapter.reloadData();
        clearDataSetDialog(titleEditText, prefixEditText);
    }


    private void clearDataSetDialog(EditText titleEditText, EditText prefixEditText) {
        titleEditText.setText("");
        prefixEditText.setText("");
    }


    @SuppressLint("InflateParams")
    private void prepareNewDataSetElementDialog(AlertDialog.Builder builder) {
        View dataSetElementDialogView = getLayoutInflater().inflate(R.layout.dialog_data_set_element, null);
        EditText titleEditText = dataSetElementDialogView.findViewById(R.id.data_set_element_title_edit_text);
        EditText valueEditText = dataSetElementDialogView.findViewById(R.id.data_set_element_value_edit_text);
        AlertDialog newDataSetElementDialog = builder
                .setTitle(getString(R.string.new_data_set_element_dialog_title))
                .setView(dataSetElementDialogView)
                .setPositiveButton(getString(R.string.add_button_label), (dialog, which) -> onNewDataSetElement(
                        titleEditText,
                        valueEditText
                ))
                .setNegativeButton(getString(R.string.cancel_button_label), (dialog, which) -> {
                    clearDataSetElementDialog(titleEditText, valueEditText);
                    dialog.cancel();
                })
                .create();

        Button newDataSetElementButton = findViewById(R.id.new_data_element_button);
        newDataSetElementButton.setOnClickListener(v -> newDataSetElementDialog.show());
    }

    private void onNewDataSetElement(EditText titleEditText, EditText valueEditText) {
        DataSetElement dataSetElement = new DataSetElement();
        dataSetElement.setTitle(titleEditText.getText().toString());
        dataSetElement.setValue(Integer.parseInt(valueEditText.getText().toString()));
        dataSetElement.setDataTypeId(datasetAdapter.getSelectedDataSet().getId());
        dataSetElementDao.save(dataSetElement);
        datasetElementAdapter.reloadData();
        clearDataSetElementDialog(titleEditText, valueEditText);
    }

    @SuppressLint("InflateParams")
    private void prepareEditDataSetElementDialog(AlertDialog.Builder builder) {
        View dataSetElementDialogView = getLayoutInflater().inflate(R.layout.dialog_data_set_element, null);

        EditText titleEditText = dataSetElementDialogView.findViewById(R.id.data_set_element_title_edit_text);
        EditText valueEditText = dataSetElementDialogView.findViewById(R.id.data_set_element_value_edit_text);
        editDataSetElementDialog = builder
                .setTitle(getString(R.string.edit_data_set_element_dialog_title))
                .setView(dataSetElementDialogView)
                .setPositiveButton(getString(R.string.edit_button_label), (dialog, which) -> onEditDataSetElement(
                        titleEditText,
                        valueEditText
                ))
                .setNegativeButton(getString(R.string.cancel_button_label), (dialog, which) -> {
                    clearDataSetElementDialog(titleEditText, valueEditText);
                    dialog.cancel();
                })
                .create();
    }

    private void onEditDataSetElement(EditText titleEditText, EditText valueEditText) {
        editedDataSetElement.setTitle(titleEditText.getText().toString());
        editedDataSetElement.setValue(Integer.parseInt(valueEditText.getText().toString()));
        editedDataSetElement.setDataTypeId(datasetAdapter.getSelectedDataSet().getId());
        dataSetElementDao.update(editedDataSetElement);
        datasetElementAdapter.reloadData();
        clearDataSetElementDialog(titleEditText, valueEditText);
    }

    private void clearDataSetElementDialog(EditText titleEditText, EditText valueEditText) {
        titleEditText.setText("");
        valueEditText.setText("");
    }

    private void validateDataSets() {
        if(datasetAdapter.getCount() > 0) {
            dataSetElementsSectionLayout.setVisibility(View.VISIBLE);
        } else {
            dataSetElementsSectionLayout.setVisibility(View.GONE);
        }

    }
}
