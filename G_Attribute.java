/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph_create_test;

/**
 *
 * @author PC
 */
public class G_Attribute {
    String name;
    String value;
    boolean isSymbolic=false;
    public G_Attribute() {
    }

    public G_Attribute(String name, String value) {
        this.name = name;
        this.value = value;
        this.isSymbolic=false;
    }

    public G_Attribute(String n, String v, boolean i) {
        this.name = n;
        this.value = v;
        this.isSymbolic=i;
    }

    

  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getIsSymbolic() {
        return isSymbolic;
    }

    public void setIsSymbolic(Boolean isSymbolic) {
        this.isSymbolic = isSymbolic;
    }

    @Override
    public String toString() {
        return "Attribute{" + "name=" + name + ", value=" + value + "isSymbolic"+isSymbolic+ '}';
    }
    
}
