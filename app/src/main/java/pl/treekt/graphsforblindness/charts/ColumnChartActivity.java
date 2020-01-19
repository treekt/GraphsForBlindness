package pl.treekt.graphsforblindness.charts;

import com.anychart.AnyChart;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import pl.treekt.graphsforblindness.listener.ChartOnClickListener;

public class ColumnChartActivity extends GraphActivity {

    @Override
    public void prepareChart() {
        Cartesian columnChart = AnyChart.column();
        columnChart.title(getDataSet().getTitle());
        columnChart.setOnClickListener(new ChartOnClickListener(getDataSet()));

        Column series = columnChart.column(getDataEntries());
        series.stroke("5 black");

        chartView.setChart(columnChart);
    }
}
