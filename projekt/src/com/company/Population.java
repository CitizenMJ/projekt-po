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

    public boolean uneliminatedInfeted(){ //kod sprawdza czy ktoś dalej jest w stanie rozprzestrzeniać wirusa (warunek głównej pętli symulacji)
        for(Human HumanTemp : people){
            if(!HumanTemp.getEliminated() && HumanTemp.getInfected()){
                return true;
            }
        }
        return false;
    }

    public int getInfectedCount() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public void updateInfected(){
        int temp=0;
        for(Human HumanTemp : people){
            if (HumanTemp.getInfected()){
                temp=temp+1;
            }
        }
        this.infected=temp;
    }



    public void updateEliminated(){
        int temp=0;
        for(Human HumanTemp : people){
            if (HumanTemp.getEliminated()){
                temp=temp+1;
            }
        }
        this.eliminated=temp;
    }

    public int getEliminatedCount() {
        return eliminated;
    }

    public void setEliminated(int eliminated) {
        this.eliminated = eliminated;
    }
}
