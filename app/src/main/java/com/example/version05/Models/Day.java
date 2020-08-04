package com.example.version05.Models;
public class Day {
    public String A, B, C;
    public Day(){}
    public Day (String A, String B, String C)
    {
        this.A = A;
        this.B = B;
        this.C = C;
    }

    public String getA() {
        return A;
    }

    public void setA(String A) {
        this.A = A;
    }

    public String getB() {
        return B;
    }

    public void setB(String B) {
        this.B = B;
    }

    public String getC() {
        return C;
    }

    public void setC(String C) {
        this.C = C;
    }
}
