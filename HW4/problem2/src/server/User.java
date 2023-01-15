package server;

import java.io.*;
import java.util.*;

public class User {
    public String major;
    public String degree;
    public int year;
    public String id;
    public int mileage;
    public String failedEnrollement;

    public User(String id, String major, String degree, int year) {
        this.id = id;
        this.major = major;
        this.degree = degree;
        this.year = year;
        this.mileage = 0;
        this.failedEnrollement = "";
    }

    public void setMileage(int mileage){
        this.mileage = mileage;
    }
}

