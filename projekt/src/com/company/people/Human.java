package com.company.people;

import com.company.Location;

public class Human {
    boolean infected;
    boolean eliminated;
    byte health;
    Location.LocName mustGo;
    Location.LocName [] canGo;
    Location.LocName [] activityPlan = new Location.LocName[2];

    //todo
    public void generateActivity(){
    }


    public Location.LocName[] getActivityPlan(){
        return activityPlan;
    }

    public boolean getInfected(){
        return infected;
    }
    public void setInfected(boolean infected){
        this.infected = infected;
    }
    public boolean getEliminated(){
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    public byte getHealth() {
        return health;
    }

    public void setHealth(byte health) {
        this.health = health;
    }
}
