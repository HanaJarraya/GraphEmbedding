/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

public class CrispInterval {
    double start,end;

    public CrispInterval() {
        this.start=0;
        this.end=0;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "CrispInterval{" + "start=" + start + ", end=" + end + '}';
    }
    
}