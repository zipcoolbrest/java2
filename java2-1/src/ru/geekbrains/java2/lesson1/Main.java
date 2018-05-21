package ru.geekbrains.java2.lesson1;

import ru.geekbrains.java2.lesson1.competitors.*;
import ru.geekbrains.java2.lesson1.obstacles.*;


public class Main {
    public static void main(String[] args) {
        Course c = new Course(new Cross((int)(Math.random()*1000)), new Wall((int)(Math.random()*10)),new Water((int)(Math.random()*100)));
        Team team1 = new Team("romashka", new Dog("BLACK"), new Cat("SANDY"),new Human("BOB"));
        c.doIt(team1);

        team1.showTeamResult();
        team1.showTeamMembers();
    }


}
