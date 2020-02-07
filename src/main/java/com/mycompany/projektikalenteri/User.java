package main.java.com.mycompany.projektikalenteri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {

    private int id;

    private String userName;

    private String displayName;

    public User(int id, String userName, String displayName) {
        this.id = id;
        this.userName = userName;
        this.displayName = displayName;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.id + " " + this.displayName;
    }

}