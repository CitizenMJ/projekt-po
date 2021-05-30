package com.company.people;

import com.company.Location;

import static com.company.Location.LocName.*;

public class OfficeWorker extends Human {
    public OfficeWorker(){
        health = 5;
        mustGo = OFFICE;
        canGo = new Location.LocName[]{
                SHOP,PARK,RESTAURANT,CLUB,CINEMA,HOSPITAL,OFFICE
        };
    }
}
