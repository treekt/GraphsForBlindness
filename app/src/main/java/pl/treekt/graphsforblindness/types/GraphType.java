package pl.treekt.graphsforblindness.types;

import pl.treekt.graphsforblindness.charts.ColumnChartActivity;
import pl.treekt.graphsforblindness.charts.PieChartActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum GraphType {
    pie_chart(0, "Wykres kołowy", PieChartActivity.class),
    bar_chart(1, "Wykres słupkowy", ColumnChartActivity.class);

    private static String hint = "Wybierz rodzaj wykresu";
    private Integer id;
    private String title;
    private Class activityClass;

    GraphType(Integer id, String title, Class activityClass) {
        this.id = id;
        this.title = title;
        this.activityClass = activityClass;
    }

    public String getHint() {
        return hint;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static GraphType getByTitle(String title) {
        for (GraphType type : values()) {
            if (type.title.equals(title)) {
                return type;
            }
        }
        return null;
    }

    public static ArrayList<String> getGraphTypesWithHint() {
        ArrayList<String> graphTypes = new ArrayList<>();
        graphTypes.add(hint);
        graphTypes.addAll(Stream.of(values())
                .map(GraphType::getTitle)
                .collect(Collectors.toList())
        );

        return graphTypes;
    }

    public Class getActivityClass() {
        return activityClass;
    }


}
