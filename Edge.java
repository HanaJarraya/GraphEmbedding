/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

import java.util.LinkedList;

/**
 *
 * @author PC
 */
class Edge {

LinkedList <G_Attribute> EdgeAttList=new LinkedList<>();
LinkedList <G_Attribute> EdgeHomogAttList= new LinkedList<>();

    public LinkedList<G_Attribute> getEdgeHomogAttList() {
        return EdgeHomogAttList;
    }

    public void setEdgeHomogAttList(LinkedList<G_Attribute> EdgeHomogAttList) {
        this.EdgeHomogAttList = EdgeHomogAttList;
    }

    public Edge(LinkedList <G_Attribute> att) {
        this.EdgeAttList=att;
    }

    public LinkedList<G_Attribute> getEdgeAttList() {
        return EdgeAttList;
    }

    public void setEdgeAttList(LinkedList<G_Attribute> EdgeAttList) {
        this.EdgeAttList = EdgeAttList;
    }
     public void addEdgeAttToList(String name,String value){
        G_Attribute a= new G_Attribute(name,value);
        this.EdgeAttList.add(a);
         //System.err.println("size attlist"+this.EdgeAttList.size());
        setEdgeAttList(EdgeAttList);
   
    }

    
    public Edge() {
        
        this.EdgeAttList=new LinkedList<G_Attribute>();
    }

    

    

    

    @Override
    public String toString() {
        String message =" ";
        
        for (int i = 0; i < EdgeAttList.size(); i++) {
                G_Attribute a = (G_Attribute) EdgeAttList.get(i);
                message= message+ a.name +"=" + a.value+ '\n';
            }
        message=message+" homogeniety attributes \n ";
        for (int i = 0; i < EdgeHomogAttList.size(); i++) {
                G_Attribute a = (G_Attribute) EdgeHomogAttList.get(i);
                message= message+ a.name +"=" + a.value+'\n';
            }
        return "Edge{" + message+ '}';
    }

    int FindAttByName(String a_homog_name, LinkedList<G_Attribute> EdgeHomogAttList) {
        int index=-1;
        for (int i = 0; i < EdgeHomogAttList.size(); i++) {
                G_Attribute get = EdgeHomogAttList.get(i);
                if (get.name.equals(a_homog_name))
                {
                   index =i;break;
                }
            }
        return index;
    }
    
    
}
