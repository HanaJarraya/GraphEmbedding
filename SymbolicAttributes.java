package graph_create_test;

import java.util.LinkedList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="SymbolicAttributes")
@XmlAccessorType(XmlAccessType.FIELD)
public class SymbolicAttributes {
    
    LinkedList<String> Edge;
    
    LinkedList<String> Node;
//@XmlElementWrapper(name="Edge")
    @XmlElement(name="Edge")
    public void setEdge(LinkedList<String> Edge) {
        this.Edge = Edge;
    }
//@XmlElementWrapper(name="Node")
    @XmlElement(name="Node")
    public void setNode(LinkedList<String> Node) {
        this.Node = Node;
    }

    public LinkedList<String> getEdge() {
        return Edge;
    }

  

    public LinkedList<String> getNode() {
        return Node;
    }

  
}