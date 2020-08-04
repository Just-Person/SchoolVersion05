package com.example.version05.Models;

public class Number {
    public Day Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;

    public Number() {
    }

    public Number(Day Monday, Day Tuesday, Day Wednesday, Day Thursday, Day Friday, Day Saturday) {
        this.Monday = Monday;
        this.Tuesday = Tuesday;
        this.Wednesday = Wednesday;
        this.Thursday = Thursday;
        this.Friday = Friday;
        this.Saturday = Saturday;
    }

    public Day getMonday() {
        return Monday;
    }

    public void setMonday(Day monday) {
        Monday = monday;
    }

    public Day getTuesday() {
        return Tuesday;
    }

    public void setTuesday(Day tuesday) {
        Tuesday = tuesday;
    }

    public Day getWednesday() {
        return Wednesday;
    }

    public void setWednesday(Day wednesday) {
        Wednesday = wednesday;
    }

    public Day getThursday() {
        return Thursday;
    }

    public void setThursday(Day thursday) {
        Thursday = thursday;
    }

    public Day getFriday() {
        return Friday;
    }

    public void setFriday(Day friday) {
        Friday = friday;
    }

    public Day getSaturday() {
        return Saturday;
    }

    public void setSaturday(Day saturday) {
        Saturday = saturday;
    }
}
