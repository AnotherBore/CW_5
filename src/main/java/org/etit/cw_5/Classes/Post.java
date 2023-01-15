package org.etit.cw_5.Classes;

public class Post {
    private int id;
    private int salary;
    private String name;

    public Post(int id, int salary, String name) {
        this.id = id;
        this.salary = salary;
        this.name = name;
    }

    public Post(int salary, String name) {
        this.id = 0;
        this.salary = salary;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
