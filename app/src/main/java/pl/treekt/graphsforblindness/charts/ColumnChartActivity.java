package pl.treekt.graphsforblindness.charts;

import com.anychart.AnyChart;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import pl.treekt.graphsforblindness.listener.ChartOnClickListener;

public class ColumnChartActivity extends GraphActivity {

    @Override
    public void prepareChart() {
        Cartesian columnChart = AnyChart.column();
        columnChart.title(getDataType().getTitle());
        columnChart.setOnClickListener(new ChartOnClickListener(getDataType()));

        Column series = columnChart.column(getDataType().getData());
        series.stroke("5 black");

        chartView.setChart(columnChart);
    }
}
