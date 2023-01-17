package org.etit.cw_5.Classes;
import java.sql.Date;
import java.time.LocalDate;

public class Tour {
    private int id;
    private String guide;
    private Date date;
    private int people;
    private String  type;

    public Tour(int id, String guide, Date date, int people, String type) {
        this.id = id;
        this.guide = guide;
        this.date = date;
        this.people = people;
        this.type = type;
    }
    public Tour(String guide, Date date, int people, String type) {
        this.id = 0;
        this.guide = guide;
        this.date = date;
        this.people = people;
        this.type = type;
    }
    public Tour() {
        this.id = 0;
        this.guide = "guide";
        this.date = Date.valueOf(LocalDate.now());
        this.people = 0;
        this.type = "type";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
