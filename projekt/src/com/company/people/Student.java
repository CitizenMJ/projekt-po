package com.company.people;

import com.company.Location;

import static com.company.Location.LocName.*;

public class Student extends Human {
    public Student(){
        health = 14;
        mustGo = UNIVERSITY;
        canGo = new Location.LocName[]{
               UNIVERSITY,SHOP,OFFICE,PARK,CINEMA,CLUB,GYM
        };
    }
}
