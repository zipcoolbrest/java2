package ru.geekbrains.java2.lesson1.obstacles;

import ru.geekbrains.java2.lesson1.competitors.Competitor;

public class Wall extends Obstacle {
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Competitor competitor) {
        competitor.jump(height);
    }
}
