package ru.geekbrains.java2.lesson1.obstacles;

import ru.geekbrains.java2.lesson1.competitors.Competitor;

public class Cross extends Obstacle {

    private int distance;

    public Cross(int distance) {

        this.distance = distance;
    }

    @Override
    public void doIt(Competitor competitor) {

        competitor.run(distance);
    }


}
