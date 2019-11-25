package com.zetcode;

import java.util.ArrayList;

public class Asteroid extends Sprite {
    private final int COORDINATE = -1;
    public static ArrayList<Asteroid> asteroids;
    private static final int[][] ASTEROID_COORDINATE = {
            {800, 30}, {1027, 60}, {1110, 50},
            {800, 180}, {1007, 160}, {1200, 150},
            {400, 30}, {627, 60}, {810, 50},
            {300, 180}, {507, 160}, {700, 150},
            {450, 280}, {690, 260}, {800, 250},
            {220, 410}, {467, 430}, {650, 420},
            {420, 600}, {657, 600}, {850, 600},
    };

    public Asteroid(int x, int y) {
        super(x, y);

        initAsteroid();
    }

    public static void setAsteroidCoordinate() {
        asteroids = new ArrayList<>();

        for (int[] position : ASTEROID_COORDINATE) {
            asteroids.add(new Asteroid(position[0], position[1]));
        }
    }

    private void initAsteroid() {
        loadImage("src/resources/asteroid.png");
        getImageDimensions();
    }

    public void moveAsteroid() {
        x += COORDINATE;
        if (x < -100) x = 1505;
    }

}
