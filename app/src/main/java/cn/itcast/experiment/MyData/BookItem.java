package cn.itcast.experiment.MyData;

import java.io.Serializable;

public class BookItem implements Serializable {
    private String name;
    private int cover_resource_id;

    public BookItem( String name, int cover_resource_id ) {
        this.name = name;
        this.cover_resource_id = cover_resource_id;
    }

    public String getTitle() {
        return name;
    }

    public int getCoverResourceId() {
        return cover_resource_id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
