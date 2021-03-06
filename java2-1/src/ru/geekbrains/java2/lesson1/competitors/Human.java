package ru.geekbrains.java2.lesson1.competitors;

public class Human implements Competitor {
    private String type;
    private String name;
    private int maxRunDistance;
    private int maxJumpHeight;
    private int maxSwimDistance;
    private boolean onDistance;

    public Human(String name) {
        this.type = "Human";
        this.name = name;
        this.maxRunDistance = (int) (Math.random()*2000);
        this.maxJumpHeight = (int) (Math.random()*15);
        this.maxSwimDistance = (int) (Math.random()*300);
        this.onDistance = true;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getMaxRunDistance() {
        return maxRunDistance;
    }

    public int getMaxJumpHeight() {
        return maxJumpHeight;
    }

    public int getMaxSwimDistance() {
        return maxSwimDistance;
    }

    public boolean isOnDistance(){
        return onDistance;
    }

    public void run(int distance){
        if (distance <= maxRunDistance){
            System.out.println(type + " " + name + ": distance "+ distance + " run(" + maxRunDistance + ") Ok!");
        }else {
            System.out.println(type + " " + name + ": distance "+ distance + " run(" + maxRunDistance + ") BAD!");
            onDistance = false;
        }
    }

    public void jump(int height){
        if (height <= maxJumpHeight){
            System.out.println(type + " " + name + ": height "+ height + " jump(" + maxJumpHeight + ") Ok!");
        }else {
            System.out.println(type + " " + name + ": height "+ height + " jump(" + maxJumpHeight + ") BAD!");
            onDistance = false;
        }
    }

    public void swim(int distance){
        if(maxSwimDistance == 0) {
            System.out.println(type + " " + name + " Can't swim!(one hand)");
            onDistance = false;
            return;
        }


        if (distance <= maxSwimDistance){
            System.out.println(type + " " + name + ": distance "+ distance + " swim(" + maxSwimDistance + ") Ok!");
        }else {
            System.out.println(type + " " + name + ": distance "+ distance + " swim(" + maxSwimDistance + ") BAD!");
            onDistance = false;
        }
    }

    public void showResult(){
        System.out.println(type + " " + name + ": " + (onDistance ? "successful complete!" : "knock out!"));
    }
}
