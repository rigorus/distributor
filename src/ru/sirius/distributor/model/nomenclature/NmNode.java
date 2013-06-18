/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sirius.distributor.model.nomenclature;

import java.util.Enumeration;


public interface NmNode {
    
    enum NodeType { GROUP, ARTICLE};
    
    NodeType getType();
    
    int getId();
       
    NmNode getParent();
    
    Enumeration<NmNode> getChildren();        
}
