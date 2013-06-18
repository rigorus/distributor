/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.distributor.model;

import java.util.Map;


public class TreeUtils {
    
    private Node root;
    private Map<Integer,Node> nodes;
    
    
    public void mergeNodeToTree(Node node, Node root){
        
        while(node.getParent() != null){
            Node parent = new Node(node.getParent());
            parent.addChield(node);
            
        }
    }
    
    
    //    public void mergeNode(Node source) {
//
//        while (source.getParent() != null) {
//            Node 
//            source = addChild(node.getParent());
//        }
//
//        mergeNode(root, node);
//        
//        if (from.getChildCount() == 0) {
//            return;
//        }
//
//        for (ClassifierNode node : from.getChildren()) {
//            ClassifierNode eq = node.getChild(node.getNodeType(), node.getId());
//            if (eq != null) {
//                mergeNode(eq, node);
//            } else {
//                to.addChild(node);
//            }
//        }
//    }
    public ClassifierNode findEqualNode(ClassifierNode root, ClassifierNode sought) {

        if (root.equals(sought)) {
            return root;
        }
        if (root.getChildCount() == 0) {
            return null;
        }

        ClassifierNode equal = root.getChild(sought.getNodeType(), sought.getId());
        if (equal != null) {
            return equal;
        }

        for (ClassifierNode node : root.getChildren()) {
            equal = findEqualNode(node, sought);
            if (equal != null) {
                return equal;
            }
        }
        return null;
    }

    public void mergeNode(ClassifierNode to, ClassifierNode from) {

        if (from.getChildCount() == 0) {
            return;
        }

        for (ClassifierNode node : from.getChildren()) {
            ClassifierNode eq = node.getChild(node.getNodeType(), node.getId());
            if (eq != null) {
                mergeNode(eq, node);
            } else {
                to.addChild(node);
            }
        }
    }
    
        public void addChild(ClassifierNode to, ClassifierNode from) {
        ClassifierNode child = new ClassifierNode(from.getNodeType(), from.getId(), from.getOrder(), to);
        to.addChild(child);
        if (!from.isLeaf()) {
            for (ClassifierNode node : from.getChildren()) {
                addChild(child, node);
            }
        }

    }

}
