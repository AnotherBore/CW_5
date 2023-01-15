package org.etit.cw_5.Classes;

public class Hall {
    private int id;
    private String name;


    public Hall(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hall() {
        this.id = 0;
        this.name = "name";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
