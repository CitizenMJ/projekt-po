package com.company;
import com.company.people.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

import static com.company.Location.LocName.*;

public class Engine {

    static Population popu = new Population();
    static String fileName = "wyniki.txt";
    static float globalInfectionModifier=0F;
    static float incubationTimeModifier=0F;

    /**
     * głowna funkcja engine
     */
    public static void simulation() {//główna funckja engine
        initialize();
        System.out.println("Rozpoczęcie symulacji:");



        printAtStart();
        run();
        System.out.println("Koniec");

    }

    /**
     * pobiera wartosci poczatkowe
     */
    static void initialize(){ //zbieranie wartości początkowych
        Scanner scan = new Scanner(System.in);
        String input;
        String inputMenu = "a";


        int infected;
        int popSize = 0;

        System.out.println("Podaj ilość aktywności w ciągu jednego dnia (2-10): ");
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
            //nauczyciel, pracownik sklepu, pracownik medyczny, pracownik biurowy, uczeń/student
            int[] peopleTypes = new int[5];

            peopleTypes[0] = howManyPeople("Podaj liczbę nauczyceli: ",scan);
            peopleTypes[1] = howManyPeople("Podaj liczbę pracowników sklepowych: ",scan);
            peopleTypes[2] = howManyPeople("Podaj liczbę pracowników medycznych: ",scan);
            peopleTypes[3] = howManyPeople("Podaj liczbę pracowników biurowych: ",scan);
            peopleTypes[4] = howManyPeople("Podaj liczbę uczniów/studentów: ",scan);

            for (int i=0;i<peopleTypes.length;i++){
                popSize=popSize+peopleTypes[i];
            }
            createPopulation(peopleTypes,popSize);
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

    /**
     * kod zajmujący się pobieraniem od uzytwkonika ilosci okreslonego typu ludzi
     * @param str wiadomosc do wyswietlenia
     * @return input uzytkownika
     */
    static private int howManyPeople(String str,Scanner scan){//kod zajmujący się drugim typem tworzenia populacji
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

    /**
     * tworzy populacje z domyslnymi proporcjami
     * @param popSize rozmiar populacji
     */
    static void createPopulationDefault(int popSize){ //tworzy populacje z domyślnymi proporcjami
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

    /**
     * tworzy populacje dostosowana przez uzytkownika
     * @param types array z iloscia poszczegolnych ludzi
     * @param popSize laczny rozmiar populacji
     */
    static void createPopulation(int [] types,int popSize){ // tworzy populacje dostosowaną przez użytkownika


        popu.setCount(popSize);
        popu.people = new Human[popSize];

        int j=0;

        for(int i=0;i<types[0];i++){
            popu.people[j] = new AcademicWorker();
            j=j+1;
        }
        for(int i=0;i<types[1];i++){
            popu.people[j] = new Cashier();
            j=j+1;
        }
        for(int i=0;i<types[2];i++){
            popu.people[j] = new MedicalPersonel();
            j=j+1;
        }
        for(int i=0;i<types[3];i++){
            popu.people[j] = new OfficeWorker();
            j=j+1;
        }
        for(int i=0;i<types[4];i++){
            popu.people[j] = new Student();
            j=j+1;
        }
    }

    /**
     * zmienia stan zarazania ludzi w startowej populacji
     * @param infected liczba ludzi zarazonych
     */
    static void initialInfection(int infected){ //pierwsi zarażeni
        popu.setInfectedCount(infected);
        popu.people=shuffleArray(popu.people);
        for(int i=0;i<infected;i++)
            popu.people[i].setInfected(true);
    }

    /**
     * glowna petla symulacji
     */
    static void run(){//główna pętla
        while(true) {
            Day.decreaseHealth();
            Day.eliminate();
            Day.generateAllActivity();
            Day.runDailyPlan();
            Day.increaseDayCount();
            popu.updateInfected();
            popu.updateEliminated();

            if (popu.getCount() == popu.getInfectedCount()) {
                System.out.println("Wszyscy zostali zarażeni");
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


    /**
     * Zostaje wywyolana gdy warunek konczacy symulacje zostanie wypelniony. Wypisuje dane końcowe do pliku.
     * @param str Powod zatrzymania symulacji
     */
    static void stop(String str){//wypisuje dane kończące
        try{
            FileWriter writer = new FileWriter(fileName,true);
            writer.append("\r\nSymulacja została zatrzymana. Powód: "+str+"\r\n");
            writer.close();
            printToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * wypisuje poczatkowe dane
     */
    static void printAtStart(){//wypisuje dane początkowe
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

    /**
     * metoda zajmujaca sie wypisywaniem statystyk sumulacji do pliku
     */
    static void printToFile(){ //wypisuje dane do pliku
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

    /**
     * tasuje array
     * @param array niepotasowany array
     * @return potasowany array
     */
    static Human[] shuffleArray(Human[] array){//tasuje tablicę
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

        /**
         * przeprowadza dzienny plan
         */
        public static void runDailyPlan(){// przeprowadza dzienny plan
            for(int time=0;time<Location.getActivityCount();time++){
                for(Location.LocName loc : Location.LocName.values()){
                    if(loc==NONE){
                        continue;
                    }else{
                        Day.infect(loc,time);
                    }
                }
            }
        }

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

        /**
         * zmniejsza punkty zdrowia u chorych
         */
        public static void decreaseHealth(){
            for(Human HumanTemp : popu.people){
                if(HumanTemp.getInfected() && !HumanTemp.getEliminated()){
                    HumanTemp.setHealth(HumanTemp.getHealth()-1);
                }
            }
        }

        /**
         * eliminuje chorych u ktorych punkty zdrowia wynosza 0 z populacji.
         */
        public static void eliminate(){
            for (Human HumanTemp : popu.people){
                if(HumanTemp.getHealth() <= 0 && !HumanTemp.getEliminated()){
                    System.out.println("Eliminacja");
                    HumanTemp.setEliminated(true);
                }

            }
        }

        /**
         * generuje plan aktywnosci dla wszystkich ludzi
         */
        public static void generateAllActivity(){
            for (Human HumanTemp : popu.people){
                HumanTemp.generateActivity();
            }

        }

        //public static int [] countPeopleIn(Location.LocName loc,int time){ //[0] wszyscy w danej lokacji w danym czasie [1] chorzy
            //int [] count = new int[2];
            //for(Human HumanTemp : popu.people){
                //if(HumanTemp.getActivityPlan(time) == loc){
                    //count[0] =+1;
                    //if(HumanTemp.getInfected()){
                        //count[1] =+ 1;
                    //}
                //}
            //}
            //return count;
        //}

        /**
         * liczy stosunek chorych do ogolu w danej lokacji w danym czasie
         * @param loc lokacja
         * @param time czas okreslony numerem indeksu aktywnosci
         * @return stosunek
         */
        static float CountRatioIn(Location.LocName loc, int time){ //zlicza procent chorych w danej lokacji
            float people=0;
            float sick=0;
            for(Human HumanTemp : popu.people){
                if(HumanTemp.getActivityPlan(time)==loc){
                    people=people+1;
                    if(HumanTemp.getInfected()){
                        sick=sick+1;
                    }
                }
            }
            return sick/people;

        }

        /**
         * Oblicza szanse na zarazenie w danej lokacji
         * @param loc lokacja
         * @param ratio stosunek ludzi w danej lokacji w danym czasie
         * @return
         */
        public static float calculateInfectionChance(Location.LocName loc,float ratio){ //szansa na zarażenie w lokacji
            float chance = ratio * 25 * Location.getInfectionModifier(loc) * globalInfectionModifier;
            //System.out.println("chance:" + chance); //debug
            if(chance > 100.00){
                return 100;
            }else{
                return chance;
            }
        }

        /**
         * rzut "kostka"
         * @return float pomiedzy 0, a 100
         */
        public static float rollDie(){ // losowa liczba od 0 do 100
            Random random = new Random();
            return random.nextFloat() * 100;
        }

        /**
         * zaraza ludzi w danej lokacji i czasie
         * @param loc lokacja
         * @param time czas
         */
        public static void infect(Location.LocName loc, int time){ //zaraża ludzi
            float chance = Day.calculateInfectionChance(loc,CountRatioIn(loc,time));
            for(Human HumanTemp : popu.people){
                if(!HumanTemp.getInfected() && HumanTemp.getActivityPlan(time) == loc && rollDie()<=chance){
                    System.out.println("Nowa infekcja");
                    HumanTemp.setInfected(true);
                }
            }
        }

        


    }
}
