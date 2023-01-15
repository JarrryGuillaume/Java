package server;

import course.*;

public class Bidder {
    public User user;
    public float bidRatio;
    public boolean failedToEnroll;
    public Course course;

    public Bidder(User user, float bidRato, Course course) {
        this.user = user;
        this.bidRatio = bidRatio;
        this.failedToEnroll = false;
        this.course = course;
    }

    public void setEnrollement(boolean failedToEnroll){
        this.failedToEnroll = failedToEnroll;
    }
}

