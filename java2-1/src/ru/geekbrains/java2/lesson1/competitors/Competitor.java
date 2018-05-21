package ru.geekbrains.java2.lesson1.competitors;

public interface Competitor {
    void run (int distance);
    void jump (int height);
    void swim (int distance);
    boolean isOnDistance();
    void showResult();

    String getType();
    String getName();
    int getMaxRunDistance();
    int getMaxJumpHeight();
    int getMaxSwimDistance();
}
