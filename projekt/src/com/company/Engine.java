package com.company;
import com.company.people.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static com.company.Location.LocName.*;

public class Engine {

    static Population popu = new Population();
    static String fileName = "wyniki.txt";
    static float globalInfectionModifier=0F;
    static float incubationTimeModifier=0F;

    public static void main(String[] args) {
        initialize();
        System.out.println("Rozpoczęcie symulacji:");



        printAtStart();
        run();
        System.out.println("Koniec");

    }


    static void initialize(){
        Scanner scan = new Scanner(System.in);
        String input = "a";
        String inputMenu = "a";


        int infected;
        int popSize = 0;

        System.out.printf("Podaj ilość aktywności w ciągu jednego dnia (2-10): ");
            while(Location.getActivityCount()<1 || Location.getActivityCount()>10){
                Location.setActivityCount(scan.nextInt());
            }

        System.out.println("Podaj modyfikator szansy na zarażenie (float)(>0)(domyślnie 1): ");
        while(globalInfectionModifier<=0){
            globalInfectionModifier = scan.nextFloat();
        }

        System.out.println("Podaj modyfikator czasu życia chorego (float)(>0)(domyślnie 1): ");
        while(incubationTimeModifier<=0){
            incubationTimeModifier = scan.nextFloat();
        }
        scan.nextLine();


        System.out.println("Wybierz sposób tworzenia populacji:\r\n[1]Użyj domyślnych proporcji\r\n[2]Stwórz własną populacje");
        while(inputMenu.charAt(0) != '1' && inputMenu.charAt(0) != '2') {
            System.out.println(':');
            inputMenu = scan.nextLine();
        }

        if (inputMenu.charAt(0) == '1'){

            System.out.println("Podaj liczebnosc populacji (min 100): ");//popsize
            while(true){
                input = scan.nextLine();
                try{
                    popSize = Integer.parseInt(input);
                    if(popSize < 100){
                        System.out.println("Liczba poza zakresem");
                        continue;
                    }
                    break;

                } catch (Exception e) {
                    System.out.println("\""+input+"\" "+"to niepoprawna liczba");
                }
            }
            createPopulationDefault(popSize);
        }

        if (inputMenu.charAt(0) == '2'){
            //todo
            int aw,cas,mp,ow,stu; //nauczyciel, pracownik sklepu, pracownik medyczny, pracownik biurowy, uczeń/student

            aw = initializationType2lazy("Podaj liczbę nauczyceli: ",scan);
            cas = initializationType2lazy("Podaj liczbę pracowników sklepowych: ",scan);
            mp = initializationType2lazy("Podaj liczbę pracowników medycznych: ",scan);
            ow = initializationType2lazy("Podaj liczbę pracowników biurowych: ",scan);
            stu = initializationType2lazy("Podaj liczbę uczniów/studentów: ",scan);


            popSize = aw+cas+mp+ow+stu;
            createPopulation(aw,cas,mp,ow,stu);
        }

        System.out.println("Podaj początkową ilość zarażonych");
        while(true){
            input = scan.nextLine();
            try{
                infected = Integer.parseInt(input);
                if(infected < 0 || infected>popSize){
                    System.out.println("Liczba poza zakresem");
                    continue;
                }
                break;

            } catch (Exception e) {
                System.out.println("\""+input+"\" "+"to niepoprawna liczba");
            }
        }

        initialInfection(infected);
        for(Human HumanTemp : popu.people){
            HumanTemp.setHealth((int) (HumanTemp.getHealth()*incubationTimeModifier));
        }
    }

    static private int initializationType2lazy(String str,Scanner scan){
        System.out.println(str);
        int temp;
        String input;
        while(true){
            input = scan.nextLine();
            try{
                temp = Integer.parseInt(input);
                if(temp<0){
                    System.out.println("Liczba poza zakresem");
                    continue;
                }else{
                    break;
                }
            }catch (Exception e) {
                System.out.println("\""+input+"\" "+"to niepoprawna liczba");
            }
        }
        return temp;

    }


    static void createPopulationDefault(int popSize){
        popu.setCount(popSize);
        popu.people = new Human[popSize];
        int j=0;
        for(int i=0;i<(int)(popSize*0.15)-1;i++){
            popu.people[j] = new MedicalPersonel();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.15)-1;i++){
            popu.people[j] = new Cashier();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.10)-1;i++){
            popu.people[j] = new AcademicWorker();
            j=j+1;
        }
        for(int i=0;i<(int)(popSize*0.40)-1;i++){
            popu.people[j] = new OfficeWorker();
            j=j+1;
        }
        for(;j<popSize;){
            popu.people[j] = new Student();
            j=j+1;
        }
    }

    static void createPopulation(int aw, int cas, int mp, int ow, int stu){

        int popSize = aw+cas+mp+ow+stu;
        popu.setCount(popSize);
        popu.people = new Human[popSize];

        int j=0;

        for(int i=0;i<aw;i++){
            popu.people[j] = new AcademicWorker();
            j=j+1;
        }
        for(int i=0;i<cas;i++){
            popu.people[j] = new Cashier();
            j=j+1;
        }
        for(int i=0;i<mp;i++){
            popu.people[j] = new MedicalPersonel();
            j=j+1;
        }
        for(int i=0;i<ow;i++){
            popu.people[j] = new OfficeWorker();
            j=j+1;
        }
        for(int i=0;i<stu;i++){
            popu.people[j] = new Student();
            j=j+1;
        }
    }

    static void initialInfection(int infected){
        popu.setInfected(infected);
        popu.people=shuffleArray(popu.people);
        for(int i=0;i<infected;i++)
            popu.people[i].setInfected(true);
    }

    static void run(){
        while(true) {
            Day.decreaseHealth();
            Day.eliminate();
            Day.generateAllActivity();
            runDailyPlan();
            Day.increaseDayCount();
            popu.updateInfected();
            popu.updateEliminated();

            if (popu.getCount() == popu.getInfectedCount()) {
                System.out.println("RIP");
                stop("Cała populacja została zarażona.");
                break;
            }
            if (!(popu.uneliminatedInfeted())) {
                System.out.println("Wirus pokonany");
                stop("Wirus został wyeliminowany.");
                break;
            }
            if (Day.getDayCount() == 999999) {
                System.out.println("Coś się zepsuło chyba");
                stop("Przkroczono limit dni");
                break;
            }

            if (Day.getDayCount() % 7 == 0) {
                printToFile();
            }
        }
    }

    static void runDailyPlan(){
        for(int time=0;time<Location.getActivityCount();time++){
             for(Location.LocName loc : Location.LocName.values()){
                 if(loc==NONE){
                     continue;
                 }else{
                     Day.infect(loc,time,Day.calculateInfectionChance(loc,Day.countPeopleIn(loc,time))); //mateusz prosze nie zapomnij co to robi
                 }
             }
        }
    }

    static void stop(String str){
        try{
            FileWriter writer = new FileWriter(fileName,true);
            writer.append("\r\nSymulacja została zatrzymana. Powód: "+str+"\r\n");
            writer.close();
            printToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void printAtStart(){
        try{
            File file = new File(fileName);
            if(file.createNewFile()){
                System.out.println("Utworzono nowy plik");
            }else{
                System.out.println("Nadpisano istniejący plik");
            }
            FileWriter writer = new FileWriter(fileName);
            writer.write("Rozpoczęcie symulacji\r\n");
            writer.close();
            printToFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void printToFile(){
        try{
            System.out.println("Zapisywanie wyników dnia: "+Day.getDayCount());
            FileWriter writer = new FileWriter(fileName, true);
            writer.append("\r\nDzień: "+Day.getDayCount()+"\r\n" +
                    "Populacja: "+ (popu.getCount() - popu.getEliminatedCount()) +"\r\n" +
                    "Ilość zdrowych: "+ (popu.getCount() - popu.getInfectedCount()) + "\r\n" +
                    "Ilość chorych: "+ (popu.getInfectedCount() - popu.getEliminatedCount()) +"\r\n" +
                    "Ilość wyeliminowanych: "+ popu.getEliminatedCount()+"\r\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static Human[] shuffleArray(Human[] array){
        Random random = new Random();

        for (int i=0; i<array.length; i++) {
            int randomIndex = random.nextInt(array.length);
            Human temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }

        return array;
    }

    static private class Day{

        static int dayCount=0;

        public static int getDayCount() {
            return dayCount;
        }

        public static void increaseDayCount(){
            dayCount=dayCount+1;
        }

        //public static void decreaseHealth(){
          //  for(int i=0;i<pop.people.length;i++){
            //    if(!pop.people[i].getEliminated() && pop.people[i].getInfected()){
              //      pop.people[i].setHealth(pop.people[i].getHealth()-1);
                //}
            //}
        //}

        public static void decreaseHealth(){
            for(Human HumanTemp : popu.people){
                if(HumanTemp.getInfected() && !HumanTemp.getEliminated()){
                    HumanTemp.setHealth(HumanTemp.getHealth()-1);
                }
            }
        }

        public static void eliminate(){
            for (Human HumanTemp : popu.people){
                if(HumanTemp.getHealth() <= 0 && !HumanTemp.getEliminated()){
                    System.out.println("Eliminacja");
                    HumanTemp.setEliminated(true);
                }

            }
        }
        public static void generateAllActivity(){
            for (Human HumanTemp : popu.people){
                HumanTemp.generateActivity();
            }

        }

        public static int [] countPeopleIn(Location.LocName loc,int time){ //[0] wszyscy w danej lokacji w danym czasie [1] chorzy
            int [] count = new int[2];
            for(Human HumanTemp : popu.people){
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
            float chance = ((float) (count[1])/ (float) (count[0])) * Location.getInfectionModifier(loc) * globalInfectionModifier;

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
            for(Human HumanTemp : popu.people){
                if(!HumanTemp.getInfected() && HumanTemp.getActivityPlan(time) == loc && rollDie()<=chance){
                    System.out.println("Nowa infekcja");
                    HumanTemp.setInfected(true);
                }
            }
        }

        


    }
}
