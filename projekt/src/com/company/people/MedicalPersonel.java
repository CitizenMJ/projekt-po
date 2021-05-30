package com.company.people;

import com.company.Location;

import static com.company.Location.LocName.*;

public class MedicalPersonel extends Human {
    public MedicalPersonel(){
        health = 7;
        mustGo = HOSPITAL;
        canGo = new Location.LocName[]{
                SHOP,PARK,RESTAURANT,CLUB,HOSPITAL
        };
    }
}
