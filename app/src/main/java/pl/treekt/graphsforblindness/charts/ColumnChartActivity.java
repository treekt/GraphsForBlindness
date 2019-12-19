package pl.treekt.graphsforblindness.charts;

import com.anychart.AnyChart;
import com.anychart.charts.Cartesian;
import pl.treekt.graphsforblindness.listener.ChartOnClickListener;

public class ColumnChartActivity extends GraphActivity {

    @Override
    public void prepareChart() {
        Cartesian columnChart = AnyChart.column();
        columnChart.data(getDataType().getData());
        columnChart.title(getDataType().getTitle());
        columnChart.setOnClickListener(new ChartOnClickListener(getDataType()));

        chartView.setChart(columnChart);
    }
}
