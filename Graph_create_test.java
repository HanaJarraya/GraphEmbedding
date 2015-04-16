/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;


public class Graph_create_test {
 


    public static void main(String[] args) {
       File dir = new File("C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\Fingerprint\\data");
       String pathVertexARFF_DataBase="C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_DataBase_Vertex_ARFF.arff";
       String pathEdgesARFF_DataBase="C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_DataBase_Edges_ARFF.arff";
       String path_discretized_EdgesDatabase="C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_DataBase_Discretized_Edges.arff";
       String path_discretized_VertexDatabase="C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_DataBase_Discretized_Vertex.arff";
      String path_FuzzyInterEdges_CSVfile = "C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_FuzzyInterEdges.csv";
      String path_FuzzyInterVertex_CSVfile="C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\Finger_Print_FuzzyInterVertex.csv";
       LinkedList <Vertex> DataBaseVertices = new LinkedList<>();
       LinkedList <Edge> DataBaseEdges = new LinkedList<>();
       FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile() && file.getName().endsWith(".gxl");
            }
        };
        File[] paths = dir.listFiles(filter);
       // paths.length
        for (int i = 0; i <paths.length ; i++) {
            File path = paths[i];
            //System.err.println(path.getPath());
            String  PathFile= path.getPath();
            int MorganIndexLevel=3;
        
        Graph g= new Graph(PathFile,MorganIndexLevel);
        g.createGraph() ;
        g.CalculateSI(MorganIndexLevel);
        Collection <Vertex> VertexColl= g.getGraph().vertexList;
        Collection <Edge> EdgeColl=g.getGraph().edgesList;
        if (i==0){
        DataBaseVertices.addAll(0,VertexColl);
        DataBaseEdges.addAll(0,EdgeColl);
        }else{
        DataBaseVertices.addAll(DataBaseVertices.size()-1,VertexColl);
        DataBaseEdges.addAll(DataBaseEdges.size()- 1,EdgeColl);
        }
        //System.out.println(g.toString());
  
 }
        ARRF_DataBase arrfile=new ARRF_DataBase();
        arrfile.GenerateVertexARFF_file(pathVertexARFF_DataBase,  DataBaseVertices);
        arrfile.GenerateEdgesARFF_file(pathEdgesARFF_DataBase,  DataBaseEdges);
        Discretize discretize_bins=new Discretize();
        try {
            discretize_bins.GenerateDiscretized_bins(pathVertexARFF_DataBase, path_discretized_VertexDatabase);
            discretize_bins.GenerateFuzzyIntervalsFile(path_FuzzyInterEdges_CSVfile);
            discretize_bins.GenerateDiscretized_bins(pathEdgesARFF_DataBase, path_discretized_EdgesDatabase);
            discretize_bins.GenerateFuzzyIntervalsFile(path_FuzzyInterVertex_CSVfile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graph_create_test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
