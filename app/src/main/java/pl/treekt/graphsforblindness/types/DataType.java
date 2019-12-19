package pl.treekt.graphsforblindness.types;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import pl.treekt.graphsforblindness.exception.ResourceNotInitializedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DataType {

    population_in_provinces(0, "Liczba ludności w województwach w Polsce", DataSelector.populationInProvinces),
    continents_areas(1, "Powierzchnia kontynentów", DataSelector.continentsAreas),
    largest_greenhouse_gas_emitters(2, "Najwięksi emitenci gazów cieplarnianych", DataSelector.largestGreenhouseGasEmitters);

    private static String hint = "Wybierz zestaw danych";
    private Integer id;
    private String title;
    private List<DataEntry> data;

    DataType(Integer id, String title, List<DataEntry> data) {
        this.id = id;
        this.title = title;
        this.data = data;
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

    public static DataType getByTitle(String title) {
        for (DataType type : values()) {
            if (type.title.equals(title)) {
                return type;
            }
        }
        return null;
    }

    public static ArrayList<String> getDataTypesWithHint() {
        ArrayList<String> graphTypes = new ArrayList<>();
        graphTypes.add(hint);
        graphTypes.addAll(Stream.of(values())
                .map(DataType::getTitle)
                .collect(Collectors.toList())
        );

        return graphTypes;
    }

    public List<DataEntry> getData() {
        return data;
    }

    public String getStatement(String label, Integer value) {
        String statement = label + " - " + value;
        switch (this) {
            case population_in_provinces:
                statement += " ludzi";
                break;
            case continents_areas:
                statement += " kilometrów kwadratowych";
                break;
            case largest_greenhouse_gas_emitters:
                statement += " kiloton CO2";
                break;
            default:
                throw new ResourceNotInitializedException(this.toString());
        }
        return statement;
    }

    static class DataSelector {

        private static List<DataEntry> populationInProvinces = new ArrayList<DataEntry>() {
            {
                add(new ValueDataEntry("Mazowieckie", 5349114));
                add(new ValueDataEntry("Śląskie", 4570849));
                add(new ValueDataEntry("Wielkopolskie", 3475323));
                add(new ValueDataEntry("Małopolskie", 3372618));
                add(new ValueDataEntry("Dolnośląskie", 2904207));
                add(new ValueDataEntry("Łódzkie", 2493603));
                add(new ValueDataEntry("Pomorskie", 2307710));
                add(new ValueDataEntry("Lubelskie", 2139726));
                add(new ValueDataEntry("Podkarpackie", 2127657));
                add(new ValueDataEntry("Kujawsko-pomorskie", 2086210));
                add(new ValueDataEntry("Zachodniopomorskie", 1710482));
                add(new ValueDataEntry("Warmińsko-mazurskie", 1439675));
                add(new ValueDataEntry("Świętokrzyskie", 1257179));
                add(new ValueDataEntry("Podlaskie", 1188800));
                add(new ValueDataEntry("Lubuskie", 1018075));
                add(new ValueDataEntry("Opolskie", 996011));
            }
        };

        private static List<DataEntry> continentsAreas = new ArrayList<DataEntry>() {
            {
                add(new ValueDataEntry("Europa", 10500000));
                add(new ValueDataEntry("Azja", 44600000));
                add(new ValueDataEntry("Antarktyda", 13200000));
                add(new ValueDataEntry("Ameryka Północna", 24200000));
                add(new ValueDataEntry("Ameryka Południowa", 17800000));
                add(new ValueDataEntry("Afryka", 30300000));
            }
        };

        private static List<DataEntry> largestGreenhouseGasEmitters = new ArrayList<DataEntry>() {
            {
                add(new ValueDataEntry("Unia europejska", 4500000));
                add(new ValueDataEntry("Indie", 3350000));
                add(new ValueDataEntry("Rosja", 2230000));
                add(new ValueDataEntry("Stany zjednoczone", 6450000));
                add(new ValueDataEntry("Japonia", 1350000));
                add(new ValueDataEntry("Chiny", 13050000));
            }
        };
    }

}
