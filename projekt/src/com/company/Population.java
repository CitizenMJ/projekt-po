package com.company;

import com.company.people.Human;

public class Population {
    public Human[] people;
    int count;
    int infected;
    int eliminated;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getInfected() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public int getEliminated() {
        return eliminated;
    }

    public void setEliminated(int eliminated) {
        this.eliminated = eliminated;
    }
}
