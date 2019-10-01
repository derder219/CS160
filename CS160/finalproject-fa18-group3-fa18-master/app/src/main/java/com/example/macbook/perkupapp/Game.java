package com.example.macbook.perkupapp;

public class Game {
    private int numPoints;
    private String location;

    public Game(int numPoints, String location) {
        this.numPoints = numPoints;
        this.location = location;

    }

    public int getNumPoints() {
        return numPoints;
    }

    public String getLocation() {
        return location;
    }
}
