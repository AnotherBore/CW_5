package org.etit.cw_5.Classes;

import java.sql.Date;
import java.time.LocalDate;

public class Staff {
    private int id;
    private long inn;
    private String post;
    private String surname;
    private String name;
    private String patr;
    private Date bd;
    private String sex;
    private String phone;

    public Staff(int id, long inn, String post, String surname, String name, String patr, Date bd, String sex, String phone) {
        this.id = id;
        this.inn = inn;
        this.post = post;
        this.surname = surname;
        this.name = name;
        this.patr = patr;
        this.bd = bd;
        this.sex = sex;
        this.phone = phone;
    }

    public Staff(long inn, String post, String surname, String name, String patr, Date bd, String sex, String phone) {
        this.id = 0;
        this.inn = inn;
        this.post = post;
        this.surname = surname;
        this.name = name;
        this.patr = patr;
        this.bd = bd;
        this.sex = sex;
        this.phone = phone;
    }

    public Staff() {
        this.id = 0;
        this.inn = 0;
        this.post = "0";
        this.surname = "surname";
        this.name = "name";
        this.patr = "patr";
        this.bd = Date.valueOf(LocalDate.now());
        this.sex = "М";
        this.phone = "phone";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getInn() {
        return inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatr() {
        return patr;
    }

    public void setPatr(String patr) {
        this.patr = patr;
    }

    public Date getBd() {
        return bd;
    }

    public void setBd(Date bd) {
        this.bd = bd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
