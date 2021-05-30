package com.company;
import com.company.people.*;

import java.util.Random;

public class Engine {

    public static void main(String[] args) {

    }

    void initialize(){
    }
    void run(){
    }
    void stop(){
    }

    void printToFile(){

    }

    static private class Day{

        static int dayCount=0;

        public static int getDayCount() {
            return dayCount;
        }

        public static void increaseDayCount(){
            dayCount=dayCount+1;
        }

        public static void decreaseHealth(){
            for(int i=0;i<Population.people.length;i++){
                if(Population.people[i].getInfected() == true && Population.people[i].getEliminated()==false){
                    Population.people[i].setHealth(Population.people[i].getHealth()-1);
                }
            }
        }
        public static void eliminate(){
            for (Human HumanTemp : Population.people){
                if(HumanTemp.getHealth() <= 0){
                    HumanTemp.setEliminated(true);
                }

            }
        }
        public static void generateAllActivity(){
            for (Human HumanTemp : Population.people){
                HumanTemp.generateActivity();
            }

        }

        public static int [] countPeopleIn(Location.LocName loc,int time){ //[0] wszyscy w danej lokacji w danym czasie [1] chorzy
            int [] count = new int[2];
            for(Human HumanTemp : Population.people){
                if(HumanTemp.getActivityPlan(time) == loc){
                    count[0] =+1;
                    if(HumanTemp.getInfected()){
                        count[1] =+ 1;
                    }
                }
            }
            return count;
        }

        public static float calculateInfectionChance(Location.LocName loc, int[] count){
            float chance = (count[1]/count[0]) * Location.getInfectionModifier(loc);

            if(chance > 100.00){
                return 100;
            }else{
                return chance;
            }
        }

        public static float rollDie(){ // losowa liczba od 0 do 100
            Random random = new Random();
            return random.nextFloat() * 100;
        }

        public static void infect(Location.LocName loc, int time, float chance){
            for(Human HumanTemp :Population.people){
                if(HumanTemp.getActivityPlan(time) == loc && rollDie()<=chance){
                    HumanTemp.setInfected(true);
                }
            }
        }

        


    }
}
