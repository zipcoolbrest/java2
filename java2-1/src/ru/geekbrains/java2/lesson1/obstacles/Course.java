package ru.geekbrains.java2.lesson1.obstacles;

import ru.geekbrains.java2.lesson1.competitors.*;

public class Course {
    private Obstacle[] courses;

    public Course(Obstacle ... args){
        this.courses = args;
    }

    public void doIt(Team team){

        for (Competitor teamate: team.getCompetitors()){
            for (Obstacle course: this.courses){
                course.doIt(teamate);
                if(!teamate.isOnDistance()) break;
            }
        }
    }
}