package com.company;
import com.company.people.*;

import java.util.Random;

import static com.company.Location.LocName.*;

public class Engine {

    static Population pop;

    public static void main(String[] args) {

    }

    void initialize(){
    }
    void createPopulationDefault(int popSize){
        pop.people = new Human[popSize];
        int j=0;
        for(int i=0;i<(int)(popSize*0.15)-1;i++){
            pop.people[j] = new MedicalPersonel();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.15)-1;i++){
            pop.people[j] = new Cashier();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.10)-1;i++){
            pop.people[j] = new AcademicWorker();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.40)-1;i++){
            pop.people[j] = new OfficeWorker();
            j=j+1;
        }
        for(;j<popSize;j++){
            pop.people[j] = new Student();
            j=j+1;
        }
    }

    void createPopulation(int aw, int cas, int mp, int ow, int stu){
        int popSize = aw+cas+mp+ow+stu;
        pop.people = new Human[popSize];

        int j=0;

        for(int i=0;i<aw;i++){
            pop.people[j] = new AcademicWorker();
            j=j+1;
        }
        for(int i=0;i<cas;i++){
            pop.people[j] = new Cashier();
            j=j+1;
        }
        for(int i=0;i<mp;i++){
            pop.people[j] = new MedicalPersonel();
            j=j+1;
        }
        for(int i=0;i<ow;i++){
            pop.people[j] = new OfficeWorker();
            j=j+1;
        }
        for(int i=0;i<stu;i++){
            pop.people[j] = new Student();
            j=j+1;
        }
    }

    void initialInfection(int infected){
        for(int i=0;i<infected;i++)
            pop.people[i].setInfected(true);
    }

    void run(){
        while(true) {
            Day.decreaseHealth();
            Day.eliminate();
            Day.generateAllActivity();
            runDailyPlan();
            Day.increaseDayCount();
            pop.updateInfected();
            pop.updateEliminated();
            if (!(pop.uneliminatedInfeted())) {
                stop("Wirus został wyeliminowany.");
                break;
            }
            if (pop.getCount() == pop.getInfected()) {
                stop("Cała populacja została zarażona.");
                break;
            }
            if (Day.getDayCount() == 99999) {
                stop("Przkroczono limit");
                break;
            }

            if (Day.getDayCount() % 7 == 0) {
                printToFile();
            }
        }
    }

    void runDailyPlan(){
        float chance;
        for(int time=0;time<Location.getActivityCount();time++){
             for(Location.LocName loc : Location.LocName.values()){
                 if(loc==NONE){
                     continue;
                 }else{
                     Day.infect(loc,time,Day.calculateInfectionChance(loc,Day.countPeopleIn(loc,time))); //mateusz prosze niezapomnij co to robi
                 }
             }
        }
    }

    void stop(String str){
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
            for(int i=0;i<pop.people.length;i++){
                if(pop.people[i].getInfected() == true && pop.people[i].getEliminated()==false){
                    pop.people[i].setHealth(pop.people[i].getHealth()-1);
                }
            }
        }
        public static void eliminate(){
            for (Human HumanTemp : pop.people){
                if(HumanTemp.getHealth() <= 0){
                    HumanTemp.setEliminated(true);
                }

            }
        }
        public static void generateAllActivity(){
            for (Human HumanTemp : pop.people){
                HumanTemp.generateActivity();
            }

        }

        public static int [] countPeopleIn(Location.LocName loc,int time){ //[0] wszyscy w danej lokacji w danym czasie [1] chorzy
            int [] count = new int[2];
            for(Human HumanTemp : pop.people){
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
            float chance = ((float) (count[1])/ (float) (count[0])) * Location.getInfectionModifier(loc);

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
            for(Human HumanTemp :pop.people){
                if(HumanTemp.getActivityPlan(time) == loc && rollDie()<=chance){
                    HumanTemp.setInfected(true);
                }
            }
        }

        


    }
}
