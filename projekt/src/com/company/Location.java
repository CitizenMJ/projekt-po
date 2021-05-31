package com.company;

public class Location {
    static int activityCount=0;

    public static int getActivityCount() {
        return activityCount;
    }
    public static void setActivityCount(int a) {
        activityCount = a;
    }

    public static float getInfectionModifier(LocName locname){
        float mod = 1F;
        switch (locname){
            case NONE:
                break;
            case UNIVERSITY:
                mod = 1F;
                break;
            case OFFICE:
                mod = 1.2F;
                break;
            case SHOP:
                mod = 1.1F;
                break;
            case PARK:
                mod = 0.5F;
                break;
            case CINEMA:
                mod = 1F;
                break;
            case RESTAURANT:
                mod = 1.25F;
                break;
            default:
                mod = 1;
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
        RESTAURANT,
        CLUB,
        GYM,
        HOSPITAL
    }
}
