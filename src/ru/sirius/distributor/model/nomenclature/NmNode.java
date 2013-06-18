/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sirius.distributor.model.nomenclature;

import javax.swing.tree.TreeNode;


public interface NmNode extends TreeNode {
    
    enum NodeType { GROUP, ARTICLE};
    
    NodeType getType();
    
    int getId();           
}
