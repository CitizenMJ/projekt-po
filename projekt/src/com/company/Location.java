package com.company;

public class Location {
    static int activityCount;

    public static int getActivityCount() {
        return activityCount;
    }
    public void setActivityCount(int activityCount) {
        this.activityCount = activityCount;
    }

    public static float getInfectionModifier(LocName locname){
        float mod = 0;
        switch (locname){
            case NONE:
                break;
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
            case RESTAURANT:
                mod = 1;
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
