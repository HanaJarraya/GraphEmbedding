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
class Vertex {
    int degree;
    LinkedList <Vertex> neighbors;
    LinkedList <Integer> SI;
    LinkedList <G_Attribute> VertexHomogAttList=new LinkedList<>();
    LinkedList <G_Attribute> VertexAttList=new LinkedList<>();

    public Vertex(LinkedList<G_Attribute> VertexAttList) {
        this.VertexAttList = VertexAttList;
        
    }

    public LinkedList<G_Attribute> getVertexAttList() {
        return VertexAttList;
    }

    public void setVertexAttList(LinkedList<G_Attribute> VertexAttList) {
        this.VertexAttList = VertexAttList;
    }
      public void addVertexAttToList(String name,String value){
        G_Attribute a= new G_Attribute(name,value);
        this.VertexAttList.add(a);
        setVertexAttList(VertexAttList);
   
    }
    
    public LinkedList<Integer> getSI() {
        return SI;
    }

    public void setSI(LinkedList<Integer> SI) {
        this.SI = SI;
    }
    
    

   

    public Vertex() {
        this.degree=0;
        this.neighbors=new LinkedList<Vertex>();
        this.SI=new LinkedList<Integer>();
        this.VertexHomogAttList=new LinkedList<G_Attribute>();
         this.VertexAttList=new LinkedList<G_Attribute>();
    }

    
public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public LinkedList<Vertex> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<Vertex> neighbors) {
        this.neighbors = neighbors;
    }
    public void addNeighbor(Vertex v){
        if (!this.neighbors.contains(v)){
            this.neighbors.add(v);
            setNeighbors(neighbors);
           
        }else System.err.println(" ");
        
    }
    public String afficheNeighbor(){
        String listeNeighbors = "\n neighbors :[ ";
         for (int i = 0; i < neighbors.size(); i++) {
                Vertex v = neighbors.get(i);
                listeNeighbors=listeNeighbors + v.getVertexAttList().get(0).value+" , ";
            }
        return listeNeighbors +']';
    }
    public String afficheSI(){
    String listeSI = " \n SummationIndex or Morgan Index = {";
        for (int i = 0; i < this.SI.size(); i++) {
            listeSI=listeSI+"   "+this.SI.get(i).toString();
        }
    return listeSI+"}**";
}
    int FindAttByName(String a_homog_name, LinkedList<G_Attribute> List) {
        int index=-1;
        for (int i = 0; i < List.size(); i++) {
                G_Attribute get = List.get(i);
                if (get.name.equals(a_homog_name))
                {
                   index =i;break;
                }
            }
        return index;
    }
    @Override
    public String toString() {
        String message =" ";
        
        for (int i = 0; i < VertexAttList.size(); i++) {
                G_Attribute a = (G_Attribute) VertexAttList.get(i);
                message= message+ a.name +"=" + a.value+'\n';
            }
        message=message+" homogeniety attributes " ;
        for (int i = 0; i < VertexHomogAttList.size(); i++) {
                G_Attribute a = (G_Attribute) VertexHomogAttList.get(i);
                message= message+ a.name +"=" + a.value+'\n';
            }
        return "Vertex{" +message+afficheNeighbor()+ afficheSI() ;
        //return "Vertex{" + "id=" + id + ", attx=" + attx + ", atty=" + atty
         //       +", degree= "+getDegree()+afficheNeighbor()+ afficheSI() ;
        
    }

    LinkedList<Edge> findConnectedEdges(LinkedList<Edge> L) {
        LinkedList<Edge> connectedEdges= new LinkedList<Edge>();
        for (int i = 0; i < L.size(); i++) {
                Edge get = L.get(i);
                if( get.EdgeAttList.get(0).value.equals(this.VertexAttList.get(0).value)||
                         get.EdgeAttList.get(1).value.equals(this.VertexAttList.get(0).value))
                {connectedEdges.add(get);}
            }
        /* for (int i = 0; i < connectedEdges.size(); i++) {
                Edge get = connectedEdges.get(i);
                System.err.println("edge from"+get.EdgeAttList.get(0).value+"to"+get.EdgeAttList.get(1).value);
            }*/
        return connectedEdges;
    }
    
    
}
