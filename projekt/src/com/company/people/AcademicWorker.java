package com.company.people;

import com.company.Location;

import static com.company.Location.LocName.*;

public class AcademicWorker extends Human {
    public AcademicWorker(){
        health = 7;
        mustGo = UNIVERSITY;
        canGo = new Location.LocName[]{
                UNIVERSITY,RESTAURANT,CINEMA,OFFICE,
        };
    }
}
