package pl.treekt.graphsforblindness.charts;

import com.anychart.AnyChart;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import pl.treekt.graphsforblindness.listener.ChartOnClickListener;

public class PieChartActivity extends GraphActivity {

    @Override
    public void prepareChart() {
        Pie pieChart = AnyChart.pie();
        pieChart.data(getDataType().getData());
        pieChart.title(getDataType().getTitle());
        pieChart.setOnClickListener(new ChartOnClickListener(getDataType()));
        pieChart.stroke("5 black");

        chartView.setChart(pieChart);
    }


}
