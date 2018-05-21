package ru.geekbrains.java2.lesson1.obstacles;

import ru.geekbrains.java2.lesson1.competitors.Competitor;

public class Water extends Obstacle {
    private int distance;

    public Water(int distance) {
        this.distance = distance;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.swim(distance);
    }
}
