package com.company;

public class Location {
    public float getInfectionModifier(LocName locname){
        float mod = 0;
        switch (locname){
            case NONE: break;
            case UNIVERSITY:
                mod = 1;
                break;
            case OFFICE:
                mod = 1;
                break;
            case SHOP:
                mod = 1;
                break;
            case PARK:
                mod = 1;
                break;
            case CINEMA:
                mod = 1;
                break;
            case PUB:
                mod = 1;
                break;
            default:
                throw new IllegalStateException("nie ma danych dla " + locname);
        }

        return mod;
    }

    public enum LocName {
        NONE,
        UNIVERSITY,
        OFFICE,
        SHOP,
        PARK,
        CINEMA,
        PUB
    }
}
