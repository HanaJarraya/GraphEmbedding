/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author PC
 */
class Graph {
    String id;
    String edgeids;
    String edgemode;
    LinkedList <Edge> edgesList =new LinkedList<Edge>();
    LinkedList <Vertex> vertexList =new LinkedList<Vertex>();
    String PathFile=null;
    LinkedList <String> symAttEdge= new LinkedList<>();
    LinkedList <String> symAttVertex=new LinkedList<>();
    int MorganIndexLevel;
    LinkedList <Histogram> histograms=new LinkedList<Histogram>();
    Graph graph;
String pathCSVFuzzyIntervalsAttributesEdgeFile;
String pathCSVFuzzyIntervalsAttributesVertexFile;

   

    public String getPathFile() {
        return PathFile;
    }
    public void setPathFile(String PathFile) {
        this.PathFile = PathFile;
    }
    public Graph getGraph() {
        return graph;
    }
    public void setGraph(Graph g) {
        this.graph = g;
    }
    public Graph(String PathFile,int l) {
        this.PathFile = PathFile;
        this.MorganIndexLevel=l;
    }
     public Graph(String PathFile,int MorganIndexLevel, String pathCSVFuzzyIntervalsAttributesEdgeFile, String pathCSVFuzzyIntervalsAttributesVertexFile) {
       this.PathFile=PathFile;
         this.MorganIndexLevel = MorganIndexLevel;
        this.pathCSVFuzzyIntervalsAttributesEdgeFile = pathCSVFuzzyIntervalsAttributesEdgeFile;
        this.pathCSVFuzzyIntervalsAttributesVertexFile = pathCSVFuzzyIntervalsAttributesVertexFile;
    }
    public Graph() {
        this.id=null;
        this.edgeids=null;
        this.edgemode = null;
        this.edgesList = new LinkedList<Edge>();
        this.vertexList = new LinkedList<Vertex>();
       
    }
    public Graph(String id, String edgeids, String edgemode, LinkedList<Edge> edgesList, LinkedList<Vertex> vertexList) {
        
        this.id = id;
        this.edgeids = edgeids;
        this.edgemode = edgemode;
        setEdgesList(edgesList);
        setVertexList(vertexList);
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEdgeids() {
        return edgeids;
    }
    public void setEdgeids(String edgeids) {
        this.edgeids = edgeids;
    }
    public String getEdgemode() {
        return edgemode;
    }
    public void setEdgemode(String edgemode) {
        this.edgemode = edgemode;
    }
    public LinkedList<Edge> getEdgesList() {
        return edgesList;
    }
    public void setEdgesList(LinkedList<Edge> edgesList) {
        this.edgesList = edgesList;
    }
    public LinkedList<Vertex> getVertexList() {
        return vertexList;
    }
    public void setVertexList(LinkedList<Vertex> vertexList) {
        this.vertexList = vertexList;
    }
    
    
    public void addVertex(Vertex v){
        
        vertexList.add(v);
        setVertexList(vertexList);
   
    }
    /*public void addEdge(Edge e){
        if (this.vertexList.contains(e)){
            this.edgesList.add(e);
            setEdgesList(edgesList);
           
        }else System.err.println("vertex not created");
        
    }*/
    
    public void addEdge(Edge e){
        
        if (this.vertexList.contains(findVertexById(e.EdgeAttList.get(0).value))){
            if (this.vertexList.contains(findVertexById(e.EdgeAttList.get(1).value)))
            {//Edge e=new Edge(id1,id2,angle,orient);
            //Edge e=new Edge(param);
                this.edgesList.add(e);
               
            setEdgesList(edgesList);}else System.err.println("vertex to "+e.EdgeAttList.get(1).value+" not created");
           
        }else System.err.println("vertex from "+e.EdgeAttList.get(0).value+" not created");
        
    }
    
    public Vertex findVertexById(String id){
        boolean found=false;
        int i=0;
        Vertex get =new Vertex();
        while (!found &&  i< this.vertexList.size()) {
             get=this.vertexList.get(i);
             if (get.getVertexAttList().get(0).value.equals(id) )
            found=true; 
            i++; 
        }
        if (found)
                return get;
            else return null;
    }
    
    public int vertexDegree (String id){
    int vertexdegree= 0;
    for (int i = 0; i < edgesList.size(); i++) {
        //if (edgesList.get(i).getFrom().equals(id))
        if (edgesList.get(i).getEdgeAttList().get(0).value.equals(id))   
              vertexdegree++;
        if (edgesList.get(i).getEdgeAttList().get(1).value.equals(id))
            vertexdegree++;
    }
    return vertexdegree;
}
    public void graphVertexDegree(){
        int degree=0;
         for (int i = 0; i < vertexList.size(); i++) {
             degree=vertexDegree(vertexList.get(i).getVertexAttList().get(0).value);
                vertexList.get(i).setDegree(degree);
                G_Attribute a=new G_Attribute();
                a.name="degree";
                a.value=String.valueOf(degree);
                vertexList.get(i).VertexAttList.add(a);
            }
   
    
}
    
    public void findNeighborVertex(){
        Vertex from,to;
         for (int i = 0; i < edgesList.size(); i++) {
                from=new Vertex();
                to=new Vertex();
                from=findVertexById(edgesList.get(i).getEdgeAttList().get(0).value);
                to=findVertexById(edgesList.get(i).getEdgeAttList().get(1).value);
                from.addNeighbor(to);
                to.addNeighbor(from);
            }
    }
    
    public void CalculateAttributesGraph(){
    graphVertexDegree();
    findNeighborVertex();
    }
    
    public void CalHomogenietyEdgeAtt(){
        for (int i = 0; i < this.edgesList.size(); i++) {
                Edge e = this.edgesList.get(i);
                Vertex from=findVertexById(e.EdgeAttList.get(0).value);
                Vertex to=findVertexById(e.EdgeAttList.get(1).value);
                //System.err.println(from.VertexAttList.size());
                for (int j = 1; j < from.VertexAttList.size(); j++) {
                    G_Attribute a_v_from=from.VertexAttList.get(j);
                    String a_v_to_value=to.VertexAttList.get(j).value;
                    String a_homog_name=a_v_from.name + "Homog";
                    String a_homog_value=CalHomog(a_v_from.value,a_v_to_value,a_v_from.isSymbolic);
                    int a_index=e.FindAttByName(a_homog_name,e.EdgeHomogAttList);
                    //System.err.println(a_index);
                    if (a_index!=-1)
                    {
                        
                        String a_index_value=e.EdgeHomogAttList.get(a_index).value;
                        e.EdgeHomogAttList.get(a_index).value=UpdateHomog(a_homog_value,a_index_value,a_v_from.isSymbolic);
                    }else
                    {
                        G_Attribute a_homog=new G_Attribute(a_homog_name,a_homog_value,a_v_from.isSymbolic);
                        e.EdgeHomogAttList.add(a_homog);
                    }
            }
                
            }
    }
    private String CalHomog(String value, String value0, boolean symbolic) {
   float result ;
        if (symbolic)
   {
       if (value.equals(value0))result=1;else result=0;
   }else
        {
            float v0=new Float(value0.trim());
            float v=new Float(value.trim());
            //System.err.println(v0+"   "+v);
            result=Math.min(abs(v),abs(v0))/Math.max(abs(v),abs(v0));
        }
        String out=String.valueOf(result);
        //System.err.println(out);
    return out;
    }

    private String UpdateHomog(String a_homog_value, String a_index_value,boolean b) {
        int result;
        if (b)
        { if (a_homog_value.equals(a_index_value))result=1;else  result=0;}
        else  {result=(Integer.parseInt(a_homog_value) +Integer.parseInt(a_index_value))/2;}
        String out=String.valueOf(result);
        return out;
    }
    public void CalHomogenietyVertexAtt(){
        for (int i = 0; i < vertexList.size(); i++) {
                Vertex get = vertexList.get(i);
                LinkedList<Edge> connectedEdges =new LinkedList<>();
                float value = 0;
                //System.err.println("Vertex"+get.VertexAttList.get(0).value+"connected to edges :\n");
                connectedEdges= get.findConnectedEdges(this.edgesList);
                int e_numb_att ;
                if (connectedEdges.size()>0)
                e_numb_att = connectedEdges.get(0).EdgeAttList.size()-2; 
                else e_numb_att=0;
                //System.err.println("attribute number to generate for vertex from edges"+e_numb_att);
                    for (int m = 0; m < e_numb_att; m++) {
                        G_Attribute a=connectedEdges.get(0).EdgeAttList.get(m+2);
                        for (int j = 0; j < (connectedEdges.size()-1); j++) {
                        String get1 = connectedEdges.get(j).EdgeAttList.get(m+2).value;
                        for (int k = j; k < connectedEdges.size(); k++) {
                                String get2 = connectedEdges.get(k).EdgeAttList.get(m+2).value;
                                String a_value_per_pair=CalHomog(get2, get2, connectedEdges.get(k).EdgeAttList.get(m+2).isSymbolic);
                                float a_value_per_pair_f=new Float(a_value_per_pair);
                                value=(value +a_value_per_pair_f)/2;
                                
                            }
                        }  
                        String a_homog_name=a.name + "Homog";
                        int a_i=get.FindAttByName(a_homog_name,get.VertexHomogAttList);
                        String parse_v=String.valueOf(value);
                        //System.err.println(a_index);
                    if (a_i!=-1)
                        {String a_index_value=get.VertexHomogAttList.get(a_i).value;
                        get.VertexHomogAttList.get(a_i).value=UpdateHomog(parse_v,a_index_value,a.isSymbolic);
                        }else
                        {G_Attribute a_homog=new G_Attribute(a_homog_name,parse_v,a.isSymbolic);
                         get.VertexHomogAttList.add(a_homog);}
                    }
                
                    
            }
    }
    public void CalculateSI(int l){
    //initialisation
        Graph g=this;//getGraph();
    for (int i = 0; i < g.getVertexList().size(); i++) {
        Vertex v = g.getVertexList().get(i);
        LinkedList <Integer> si=new LinkedList<Integer>();
        si=v.getSI();
        si.add(v.getDegree());
        v.setSI(si);
    }
    //loop for level L
    for (int it = 0; it < l; it++) {
         for (int k = 0; k < g.getVertexList().size(); k++) {
            Vertex v = g.getVertexList().get(k);
            int sum=0;
            LinkedList <Integer> siOfV=new LinkedList<Integer>();
             //System.err.println("sum of neighbors degree at level "+ it +" of"+v.getId());
            for (int j = 0; j < v.getNeighbors().size(); j++) {
                LinkedList <Integer> siNeighborOfV=new LinkedList<Integer>();
                siNeighborOfV=v.getNeighbors().get(j).getSI();
                //System.err.println("+"+siNeighborOfV.getLast());
                sum=sum + siNeighborOfV.get(it);
            }
            siOfV=v.getSI();
            //System.err.println(" = "+sum);
            siOfV.add(sum);
            //v.setSI(siOfV);
            //if (it>0){
            G_Attribute a=new G_Attribute();
            
            a.name="MorganIndexLevel_"+it;
            a.value=String.valueOf(sum);
            v.VertexAttList.add(a);
            //}
        }   
    }
}
    
    public void SymbolicAttributeParsing(){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SymbolicAttributes.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    SymbolicAttributes symbolicAttributes = (SymbolicAttributes) jaxbUnmarshaller.unmarshal(
                                new File("C:\\Users\\utilisateur\\Documents\\NetBeansProjects\\graph_create_test\\src\\graph_create_test\\SymbolicAttribute.xml"));
		/*Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(symbolicAttributes, System.out);*/
		//System.err.println(symbolicAttributes.getNode().size());
                for (int i = 0; i < symbolicAttributes.getEdge().size(); i++) {
                    symAttEdge.add(symbolicAttributes.getEdge().get(i));}
                for (int i = 0; i < symbolicAttributes.getNode().size(); i++) {
                    symAttVertex.add(symbolicAttributes.getNode().get(i)); }
                //System.out.println(symAttEdge.size());
   	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
}
       
    private static Boolean CheckSymAttList(String name, LinkedList<String> symAttList) {
        boolean compared=false;
        //System.out.println("value "+name);
        for (int i = 0; i < symAttList.size(); i++) {
            String get = symAttList.get(i);
            get=get.trim();name=name.trim();
            //System.out.println(get+"  "+name.equals(get));
            if(name.equals(get))
            {  compared=true; //System.out.println(" equal to "+get);
            break;}
            
        }
       
        return compared;
    }
    
    public void createGraph(){
        Graph g= new Graph();
        String id_graph = null;
        String ids_edge = null;
        String mode_edge = null;
        
        SymbolicAttributeParsing();
        
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder =factory.newDocumentBuilder();
            try {
                System.err.println(PathFile);
                Document doc= builder.parse(PathFile);
             
                NodeList Param=doc.getElementsByTagName("graph");
                Node nodeParam=Param.item(0);
                if (nodeParam.getNodeType()==Node.ELEMENT_NODE){
                    Element graphParam = (Element) nodeParam;
                    id_graph=graphParam.getAttribute("id");
                    ids_edge=graphParam.getAttribute("edgeids");
                    mode_edge=graphParam.getAttribute("edgemode");
                }
                g.setId(id_graph);
                g.setEdgeids(ids_edge);
                g.setEdgemode(mode_edge);
                
                NodeList nodeList=doc.getElementsByTagName("node");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Vertex v=new Vertex();
                    Node nodeList_i=nodeList.item(i);
                    if (nodeList_i.getNodeType()==Node.ELEMENT_NODE){
                        Element node_element=(Element) nodeList_i;
                        G_Attribute a=new G_Attribute();
                        a.name=node_element.getAttributes().item(0).getNodeName();
                        a.value= node_element.getAttributes().item(0).getNodeValue();
                        v.VertexAttList.add(a);
                        NodeList node_element_attribute=node_element.getChildNodes();
                        for (int j = 0; j <node_element_attribute.getLength(); j++) {
                            G_Attribute a_i=new G_Attribute();
                            Node attribute=node_element_attribute.item(j);
                            if (attribute.getNodeType()==Node.ELEMENT_NODE){                                    
                                    Element attribute_element= (Element) attribute;
                                    a_i.name=attribute_element.getAttributes().item(0).getNodeValue();
                                    // Set up vertex and edge display properties for jgraph
                                    NodeList attList=attribute_element.getChildNodes();
                                    Node att=attList.item(0);
                                    if (att.getNodeType()==Node.ELEMENT_NODE){
                                        a_i.value=att.getTextContent();
                                        a_i.isSymbolic=CheckSymAttList(a_i.name,symAttVertex);
                                        //System.err.println("att="+ a_i.toString());
                                        v.VertexAttList.add(a_i);
                                    }
                            } 
                        }
                        
                   
                    }
                    g.addVertex(v);
                }   
                        NodeList edgeList=doc.getElementsByTagName("edge");
                for (int i = 0; i < edgeList.getLength(); i++) {
                    Edge e=new Edge();
                    Node edgeList_i=edgeList.item(i);
                    if (edgeList_i.getNodeType()==Node.ELEMENT_NODE){
                        Element edge_element=(Element) edgeList_i;
                        G_Attribute from=new G_Attribute();
                        from.name=edge_element.getAttributes().item(0).getNodeName();
                        from.value= edge_element.getAttributes().item(0).getNodeValue();
                        //System.err.println(edge_element.getAttributeNode("from").toString());
                        e.EdgeAttList.add(from);
                        G_Attribute to=new G_Attribute();
                        to.name=edge_element.getAttributes().item(1).getNodeName();
                        to.value= edge_element.getAttributes().item(1).getNodeValue();
                        e.EdgeAttList.add(to);
                        NodeList edge_element_attribute=edge_element.getChildNodes();
                        for (int j = 0; j <edge_element_attribute.getLength(); j++) {
                            G_Attribute a_i=new G_Attribute();
                            Node attribute=edge_element_attribute.item(j);
                            if (attribute.getNodeType()==Node.ELEMENT_NODE){                                    
                                    Element attribute_element= (Element) attribute;
                                    a_i.name=attribute_element.getAttributes().item(0).getNodeValue();
                                    // Set up vertex and edge display properties for jgraph
                                    NodeList attList=attribute_element.getChildNodes();
                                    Node att=attList.item(0);
                                    if (att.getNodeType()==Node.ELEMENT_NODE){
                                        a_i.value=att.getTextContent();
                                        a_i.isSymbolic=CheckSymAttList(a_i.name,symAttEdge);
                                        //System.out.println("att="+ a_i.toString());
                                        e.EdgeAttList.add(a_i);
                                    }
                            } 
                        }
                        
                   
                    }
                    //System.err.println(e.toString());
                    g.addEdge(e);
                }
            } catch (SAXException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.CalculateAttributesGraph();
        g.CalculateSI(MorganIndexLevel);
        g.CalHomogenietyEdgeAtt();
        g.CalHomogenietyVertexAtt();
        this.setGraph(g);
        
    }
   
    public void CreateHistograms(){
        //first create edges ATtributes Histograms
        LinkedList <LinkedList<FuzzyInterval>> fuzzyIntAttributes= CSVFileparser("Edge");
        int numAtt=this.edgesList.get(0).EdgeAttList.size();
        Histogram histAtt=new Histogram();
        //begin att reding from 2 because 0 and 1 reserved for fromnodeid and tonodeid
        for (int i = 2; i < numAtt; i++) {
                String nameAtt=edgesList.get(0).EdgeAttList.get(i).name;
                LinkedList listValueAtt_i=new LinkedList();
                for (int j = 0; j < edgesList.size(); i++) {
                    G_Attribute att_i = edgesList.get(j).EdgeAttList.get(i);
                    if (!att_i.isSymbolic)
                        listValueAtt_i.add(Double.parseDouble(att_i.value.trim()));
                    else System.err.println("need treatment of symbolic attribute not done yet");
                }
                LinkedList <FuzzyInterval> fuzzyIntervalsAtt_i=fuzzyIntAttributes.get(i-2);
                histAtt.GenerateHistogramAttribute(nameAtt, listValueAtt_i,fuzzyIntervalsAtt_i );
                histograms.add(histAtt);
        }
    
    }
    //object can be either Node or Edge
  public LinkedList <LinkedList<FuzzyInterval>>  CSVFileparser(String VertexOrEdge){
     String pathCSVfile;
     //Check which file to parse Trapezoidal EdgeIntervals or NodeIntervals 
      if (VertexOrEdge.equalsIgnoreCase("Vertex")) 
          pathCSVfile=this.pathCSVFuzzyIntervalsAttributesVertexFile;
      else pathCSVfile=this.pathCSVFuzzyIntervalsAttributesEdgeFile;
      
      LinkedList <LinkedList<FuzzyInterval>> fuzzyBinsAttributes=new LinkedList<>();  
      LinkedList<FuzzyInterval> fuzzyBinsAtt_i = new LinkedList<>();
      try {
          //openfileand read
      String StringfuzzyBinsAtt_i;//=readline from file
            fuzzyBinsAtt_i =ParseStringToFuzzyBins(StringfuzzyBinsAtt_i);
            fuzzyBinsAttributes.add(fuzzyBinsAtt_i);
           }catch(IOException e){
	     e.printStackTrace();}  
                      
                      
         return fuzzyBinsAttributes;
          }

    public String ParseStringToFuzzyBins(String StringfuzzyBinsAtt_i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String toString() {
        Graph g=getGraph();
        String messageEdge =" ", messageVertex = " ";
        
        for (int i = 0; i < g.edgesList.size(); i++) {
                Edge o = (Edge) g.edgesList.get(i);
                messageEdge= messageEdge + o.toString()+'\n';
            }
        for (int j = 0; j < g.vertexList.size(); j++) {
                Vertex v = (Vertex) g.vertexList.get(j);
                messageVertex= messageVertex +v.toString()+'}'+'\n';
            }
        return "GRAPH{"+ "ID=" + g.getId() + ", EdgeIds=" + g.getEdgeids() + ", EdgeMode=" + g.getEdgemode() + '\n'+" EDGES : \n" 
        + messageEdge 
               +'\n'+ "VERTEX : \n"+ messageVertex+'}';
    }

    

  

}
