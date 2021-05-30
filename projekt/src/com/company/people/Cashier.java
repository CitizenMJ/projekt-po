package com.company.people;

import com.company.Location;

import static com.company.Location.LocName.*;

public class Cashier extends Human {
    public Cashier(){
        health = 10;
        mustGo = SHOP;
        canGo = new Location.LocName[]{
                SHOP,PARK,CLUB,CINEMA
        };
    }
}
