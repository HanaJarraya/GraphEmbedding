/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/**
 *
 * @author utilisateur
 */
public class Discretize {
    String path_database;
    String path_discretized_database;
    LinkedList <LinkedList <FuzzyInterval>> listAttributesIntervals = new LinkedList<>();

    public void GenerateDiscretized_bins(String path_database, String path_discretized_database) throws FileNotFoundException{
    BufferedReader reader = new BufferedReader(
                              new FileReader(path_database));
        Instances data = null;
        try {
            data = new Instances(reader);
        } catch (IOException ex) {
            Logger.getLogger(Discretize.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] Options=new String[5];
        Options[0]="-B";
        Options[1]="3";
        Options[2]="-F";
        Options[3]="-R";
        Options[4]="first-last";
        weka.filters.unsupervised.attribute.Discretize discretize=new weka.filters.unsupervised.attribute.Discretize();
        try {
            discretize.setOptions(Options);
        } catch (Exception ex) {
            Logger.getLogger(Discretize.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            discretize.setInputFormat(data);
        } catch (Exception ex) {
            Logger.getLogger(Discretize.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Instances newData = null;
        
        try {
            newData = weka.filters.unsupervised.attribute.Discretize.useFilter(data,discretize);
            ArffSaver saver=new ArffSaver();
        saver.setInstances(newData);
        saver.setFile(new File (path_discretized_database));
         saver.writeBatch();
        } catch (Exception ex) {
            Logger.getLogger(Discretize.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(newData);
        int numAtt=newData.numAttributes();
        int nmbcutpoints=discretize.getCutPoints(0).length;
        int ni=nmbcutpoints+1;
        for (int i = 0; i < numAtt; i++) {
            LinkedList <FuzzyInterval> intervals=new LinkedList<>();
             double[] cutpoints=discretize.getCutPoints(i);
             intervals=GetFuzzyTrapezIntervals(cutpoints);
             this.listAttributesIntervals.add(intervals);
        }
        
    }
     
     public LinkedList <CrispInterval> GetCrispIntervals (double[] L){
        CrispInterval c = new CrispInterval();
        LinkedList <CrispInterval> crisps=new LinkedList<>();
        c.start=Double.NEGATIVE_INFINITY;
        c.end=L[0];
        crisps.add(c);
        System.out.println(crisps.getLast().toString());
        for( int i=0;i<L.length;i++){
            c=new CrispInterval();
            c.start=L[i]; 
            int j=i;
            if (i==L.length-1) {                
               c.end=Double.POSITIVE_INFINITY;     
                } else{c.end=L[j+1];}
            crisps.add(c);
        System.out.println(crisps.getLast().toString());
        }
        return crisps;
    }
     public LinkedList<FuzzyInterval> GetFuzzyTrapezIntervals(double [] cutpoints) {
        LinkedList <CrispInterval>  crispIntervals = new LinkedList<>();
        crispIntervals=GetCrispIntervals(cutpoints);
         LinkedList <FuzzyInterval> fuzzyIntervals;
         FuzzyInterval init_Finterval =new FuzzyInterval();
        fuzzyIntervals=new LinkedList<>();
        CrispInterval first_Cinterval=new CrispInterval();
        first_Cinterval=crispIntervals.getFirst();
        init_Finterval.a=Double.NEGATIVE_INFINITY;
        init_Finterval.b=Double.NEGATIVE_INFINITY;
        init_Finterval.c=first_Cinterval.getEnd();
        first_Cinterval=new CrispInterval();
        first_Cinterval=crispIntervals.get(1);
        init_Finterval.d=first_Cinterval.getEnd();
        fuzzyIntervals.add(init_Finterval);
        System.out.println(fuzzyIntervals.get(0).toString());
              
        for (int i=2;i<crispIntervals.size();i++)  {   
            FuzzyInterval unit_Finterval = new FuzzyInterval();
            FuzzyInterval prev=new FuzzyInterval();
             prev=fuzzyIntervals.getLast();
             unit_Finterval.a=prev.c;
             unit_Finterval.b=prev.d;
             unit_Finterval.c=crispIntervals.get(i).end;
             i=i+1;
             if (i<crispIntervals.size()){
                unit_Finterval.d=crispIntervals.get(i).end;}
             else { unit_Finterval.d=Double.POSITIVE_INFINITY;}
             fuzzyIntervals.add(unit_Finterval);
             System.out.println(fuzzyIntervals.getLast().toString());
             i=i+1;
            }
    return fuzzyIntervals;
    }
   public void GenerateFuzzyIntervalsFile(String path_FuzzyIntervals_File){
        ARRF_DataBase TrapzInterfile =new ARRF_DataBase();
        TrapzInterfile.GenerateInterAttCSVFile(path_FuzzyIntervals_File,listAttributesIntervals);
   }
}
