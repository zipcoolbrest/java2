package ru.geekbrains.java2.lesson1.competitors;


public class Team {

    private String name;
    private Competitor[] competitors;

    public Team(String name, Competitor ... args) {
        this.name = name;
        this.competitors = args;

    }

    public Competitor[] getCompetitors() {
        return competitors;
    }

    public void showTeamResult(){
        System.out.println("\nTeam " + this.name + " results: ");
        for (Competitor competitor: this.competitors) {
            competitor.showResult();
        }
        System.out.println("====== done ======\n");
    }

    public void showTeamMembers(){
        System.out.println("\nTeam " + this.name + " members: ");
        for (Competitor c: this.competitors) {
            System.out.println((c.getType()) + " " + c.getName() + ": run "+ c.getMaxRunDistance()
                    + "; jump " + c.getMaxJumpHeight() + "; swim " + c.getMaxSwimDistance());
        }
        System.out.println("====== done ======\n");
    }
}
