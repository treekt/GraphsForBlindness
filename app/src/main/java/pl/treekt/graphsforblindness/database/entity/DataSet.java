package pl.treekt.graphsforblindness.database.entity;

import java.io.Serializable;

public class DataSet implements Serializable {

    private Integer id;
    private String title;
    private String prefix;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return title;
    }
}
