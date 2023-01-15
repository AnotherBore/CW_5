package org.etit.cw_5.Classes;

import java.sql.Date;
import java.time.LocalDate;

public class Exhibit {
    private int id;
    private String title;
    private String author;
    private int eow;
    private Date inPos;
    private String type;
    private String hall;

    public Exhibit(int id, String title, String author, int eow, Date inPos, String type, String hall) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.eow = eow;
        this.inPos = inPos;
        this.type = type;
        this.hall = hall;
    }

    public Exhibit() {
        this.id = 0;
        this.title = "title";
        this.author = "Неизвестен";
        this.eow = 0;
        this.inPos = Date.valueOf(LocalDate.now());;
        this.type = "type";
        this.hall = "";
    }

    public Exhibit(String title, String author, int eow, Date inPos, String type, String hall) {
        this.id = 0;
        this.title = title;
        this.author = author;
        this.eow = eow;
        this.inPos = inPos;
        this.type = type;
        this.hall = hall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getEow() {
        return eow;
    }

    public void setEow(int eow) {
        this.eow = eow;
    }

    public Date getInPos() {
        return inPos;
    }

    public void setInPos(Date inPos) {
        this.inPos = inPos;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }
}
