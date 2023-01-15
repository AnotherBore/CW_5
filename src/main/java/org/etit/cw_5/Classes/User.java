package org.etit.cw_5.Classes;

public class User {
    private int id;
    private String username;
    private String password;

    private byte privilege;

    public User(int id, String username, String password, byte privilege){
        this.id = id;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }

    public User(String username, String password, byte privilege){
        this.id = 0;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }
    public User(String username, String password){
        this.id = 0;
        this.username = username;
        this.password = password;
        this.privilege = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getPrivilege() {
        return privilege;
    }

    public void setPrivilege(byte privilege) {
        this.privilege = privilege;
    }
}
