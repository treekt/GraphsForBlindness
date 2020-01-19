package pl.treekt.graphsforblindness.charts;

import com.anychart.AnyChart;
import com.anychart.charts.Pie;
import pl.treekt.graphsforblindness.listener.ChartOnClickListener;

public class PieChartActivity extends GraphActivity {

    @Override
    public void prepareChart() {
        Pie pieChart = AnyChart.pie();
        pieChart.data(getDataEntries());
        pieChart.title(getDataSet().getTitle());
        pieChart.setOnClickListener(new ChartOnClickListener(getDataSet()));
        pieChart.stroke("5 black");

        chartView.setChart(pieChart);
    }


}
