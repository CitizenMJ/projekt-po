package com.company;

import com.company.people.Human;

public class Population {
    public static Human[] people;
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

    public void updateInfected(){
        int temp=0;
        for(Human HumanTemp : people){
            if (HumanTemp.getInfected()){
                temp=+1;
            }
        }
        this.infected=temp;
    }
    public void updateEliminated(){
        int temp=0;
        for(Human HumanTemp : people){
            if (HumanTemp.getEliminated()){
                temp=+1;
            }
        }
        this.eliminated=temp;
    }

    public int getEliminated() {
        return eliminated;
    }

    public void setEliminated(int eliminated) {
        this.eliminated = eliminated;
    }
}
