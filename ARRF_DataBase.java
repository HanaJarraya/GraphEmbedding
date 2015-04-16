/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/**
 *
 * @author utilisateur
 */
public class ARRF_DataBase {
     public  FastVector att_File;
public  Instances trainingSet; 

    public ARRF_DataBase() {
        this.att_File=new FastVector();
        this.trainingSet=null;
    }
 public void  GenerateEdgesARFF_file(String pathARFF_DataBase,  LinkedList<Edge> L_a_edges){
     FastVector attributes;
    attributes = new FastVector();
        //create attributes
     LinkedList<G_Attribute> node_init=L_a_edges.get(0).EdgeAttList;
     for (int i = 2; i <node_init.size(); i++) {
        G_Attribute o = node_init.get(i);
         attributes.addElement(new Attribute( o.name));    
    }
     //Create Instances to full data in it
    trainingSet=new Instances("graph", attributes, 0);
    trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
    Instance instance=new DenseInstance(trainingSet.numAttributes()); 
     for (int i = 0; i < L_a_edges.size(); i++) {
             
       Edge edge=L_a_edges.get(i);
          
            for (int j = 2; j < edge.EdgeAttList.size(); j++) {
            G_Attribute v = edge.EdgeAttList.get(j);
            Float s= new Float (v.value);
             instance.setValue(j-2, s);
            }
            
       
            trainingSet.add(instance);
        }
     System.err.println("data instances :\n"+trainingSet.toString());
        try {
        trainingSet=trainingSet;
        ArffSaver arffSaverInstance = new ArffSaver();
        arffSaverInstance.setInstances(trainingSet);
        arffSaverInstance.setFile(new File(pathARFF_DataBase));
        arffSaverInstance.writeBatch();
    } catch (IOException ex) {
        Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
    }

 
 }
    public void  GenerateVertexARFF_file(String pathARFF_DataBase, LinkedList <Vertex> L_a_nodes){
        FastVector attributes;
    attributes = new FastVector();
        //create attributes
     LinkedList<G_Attribute> node_init=L_a_nodes.get(0).VertexAttList;
     for (int i = 1; i <node_init.size(); i++) {
        G_Attribute o = node_init.get(i);
         attributes.addElement(new Attribute( o.name));    
    }
     //Create Instances to full data in it
    trainingSet=new Instances("graph", attributes, 0);
    trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
    Instance instance=new DenseInstance(trainingSet.numAttributes()); 
     for (int i = 0; i < L_a_nodes.size(); i++) {
             
         Vertex node=L_a_nodes.get(i);
          
            for (int j = 1; j < node.VertexAttList.size(); j++) {
            G_Attribute v = node.VertexAttList.get(j);
            Float s= new Float (v.value);
             instance.setValue(j-1, s);
            }
            
       
            trainingSet.add(instance);
        }
     System.err.println("data instances :\n"+trainingSet.toString());
        try {
        trainingSet=trainingSet;
        ArffSaver arffSaverInstance = new ArffSaver();
        arffSaverInstance.setInstances(trainingSet);
        arffSaverInstance.setFile(new File(pathARFF_DataBase));
        arffSaverInstance.writeBatch();
    } catch (IOException ex) {
        Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    /*void GenerateInterAttArffFile(String path_FuzzyIntervals_File, LinkedList<LinkedList<FuzzyInterval>> attsIntervals) {
    FastVector attributes = new FastVector();
        //create attributes
    FastVector attValsRel = new FastVector();
    Instances       relattdata;
    Instance       tinstance;
       
      attValsRel.addElement("a");
     attValsRel.addElement("b");
      attValsRel.addElement("c");
       attValsRel.addElement("d");
     for (int i = 1; i <attsIntervals.size(); i++) {
        attributes.addElement(new Attribute("Attribute_"+i+"_FuzzyBins",attValsRel));    
    }
     //Create Instances to full data in it
    trainingSet=new Instances("Fuzzy Intervals", attributes, 0);
    trainingSet.setClassIndex(trainingSet.numAttributes() - 1);
    Instance instance=new DenseInstance(trainingSet.numAttributes()); 
    for (int j=0;j<attsIntervals.get(0).size();j++){
     tinstance = new DenseInstance(trainingSet.numAttributes());
        for (int i = 0; i < attsIntervals.size(); i++) {
         FuzzyInterval interval_i=attsIntervals.get(i).get(j);
         relattdata=new Instances(trainingSet.attribute(0).relation(),0);
         Instance relattInst=new DenseInstance(1);
         relattInst.setDataset(relattdata);
         relattInst.setValue(0, interval_i.a);
         relattInst.setValue(1, interval_i.b);
         relattInst.setValue(2, interval_i.c);
         relattInst.setValue(3, interval_i.d);
         relattdata.add(relattInst);
         tinstance.setValue(i,trainingSet.attribute(0).addRelation(relattdata));
            }
            trainingSet.add(tinstance);
        }
     System.err.println("data instances :\n"+trainingSet.toString());
        try {
        trainingSet=trainingSet;
        ArffSaver arffSaverInstance = new ArffSaver();
        arffSaverInstance.setInstances(trainingSet);
        arffSaverInstance.setFile(new File(path_FuzzyIntervals_File));
        arffSaverInstance.writeBatch();
    } catch (IOException ex) {
        Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/
    void GenerateInterAttCSVFile(String path_FuzzyIntervals_File, LinkedList<LinkedList<FuzzyInterval>> attsIntervals){
        try
	{
	    FileWriter writer = new FileWriter(path_FuzzyIntervals_File);
  for (int i = 0; i < attsIntervals.size(); i++) {
         LinkedList<FuzzyInterval> BinsAtt_i = attsIntervals.get(i);
         
                 writer.append("Attribute"+i+": ");
         for (int j = 0; j < BinsAtt_i.size(); j++) {
                FuzzyInterval bin = BinsAtt_i.get(j);
                writer.append('{');
                writer.append(new Double(bin.a).toString());
                writer.append(',');
                writer.append(new Double(bin.b).toString());
                writer.append(',');
                writer.append(new Double(bin.c).toString());
                writer.append(',');
                writer.append(new Double(bin.d).toString());
                writer.append("} ");
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
