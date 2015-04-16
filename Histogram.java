/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;



/**
 *
 * @author utilisateur
 */
class Histogram {
    String label;
    LinkedList <FuzzyInterval> fuzzyIntervals;//define from csv file
    LinkedList <LinkedList > membershipAtt;

    public Histogram() {
        this.label = null;
        this.fuzzyIntervals = new LinkedList<>();
        this.membershipAtt = new LinkedList<>();
    }
    
    public void GenerateHistogramAttribute(String name, LinkedList attValues, LinkedList <FuzzyInterval> fuzzyBins){
        this.label=name;
        this.fuzzyIntervals=fuzzyBins;
         for (int i = 0; i < attValues.size(); i++) {
                double get = (double) attValues.get(i);
                LinkedList membershipAttValue_i=new LinkedList();
                membershipAttValue_i=CalculateMembership(get);
                membershipAtt.add(membershipAttValue_i);
            }
         SaveHistogram();
    }

    private LinkedList CalculateMembership(double value) {
        LinkedList membershipOfValue= new LinkedList();
         for (int i = 0; i < fuzzyIntervals.size(); i++) {
                FuzzyInterval bin = fuzzyIntervals.get(i);
                double memberShipPerBin=0;
                if (bin.a<=value&& value<bin.b)
                memberShipPerBin=(value-bin.a)/(bin.b-bin.a);else
                if (bin.b<=value&& value<bin.c)
                memberShipPerBin=1;else
                if (bin.c<=value&& value<bin.d)
                memberShipPerBin=(value-bin.c)/(bin.c-bin.d);
                membershipOfValue.add(memberShipPerBin);
            }
         return membershipOfValue;
    }

    private void SaveHistogram() {
        try
	{
	    FileWriter writer = new FileWriter("histogramAttribute"+label+".csv");
  for (int i = 0; i <  membershipAtt.size(); i++) {
         LinkedList  membershipPerValue=  membershipAtt.get(i);
               writer.append("MembershipPerInterval "+i+": ");
         for (int j = 0; j < membershipPerValue.size(); j++) {
                 double membershipPerBin = (double) membershipPerValue.get(j);
                writer.append("  ");
	 }
         writer.append('\n');
  }
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	}
        }
            
}
