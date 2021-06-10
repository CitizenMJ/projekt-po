package com.company.people;

import com.company.Location;

import java.util.Random;

import static com.company.Location.LocName.*;

public class Human {
    public boolean infected;
    boolean eliminated;
    int health;
    //boolean vaccinated;
    Location.LocName mustGo;
    Location.LocName [] canGo;
    Location.LocName [] activityPlan;

    Human(){
        this.infected = false;
        this.eliminated = false;
        //vaccinated = false;
        this.activityPlan = new Location.LocName[Location.getActivityCount()];
    }

    //generuje plan dnia
    public void generateActivity(){
        if(getEliminated()){//umieszcza wyeliminowanych w NONE
            for(Location.LocName loc : activityPlan){
                loc = NONE;
            }
        }else{ //losuje aktywność z listy dostępnych aktywnośći, sprawdza czy aktywność obowiązkowa została wylosowana przypadkiem
            boolean requirements=false;
            Random random = new Random();
            for(int i=0;i<activityPlan.length-1;i++){
                activityPlan[i]=canGo[random.nextInt(canGo.length)];
                if(activityPlan[i]==mustGo){
                    requirements=true;
                }
            }
            if(mustGo==NONE){//jeżeli brakuje aktywności losowej, zachowaj się jakby była już wylosowana
                requirements=true;
            }

            if(requirements){//uzupełnij listę aktywności o aktywność obowiązkową jeżeli
                activityPlan[activityPlan.length-1]=canGo[random.nextInt(canGo.length)];
            }else{
                activityPlan[activityPlan.length-1]=mustGo;
            }
        }
    }


    public Location.LocName[] getActivityPlan(){ //zwraca plan dnia
        return activityPlan;
    }

    public Location.LocName getActivityPlan(int a){ //zwraca plan dla określonej części dnia
        return activityPlan[a];
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
