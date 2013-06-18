/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.distributor.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;


public class Node{
    
    public enum NodeType {
        GROUP, ARTICLE
    };
    
    public static void mergeRoot(Node root, Node source){
        while (source.getParent() != null) {
            Node parent = new Node(source.getParent());
            
        } 
    }
               
    private NodeType nodeType;
    private int id;
    private int order;
    private Node parent;
    private TreeSet<Node> children = null;

    public Node(NodeType nodeType, int id, int order, Node parent) {
        this.nodeType = nodeType;
        this.id = id;
        this.order = order;
        this.parent = parent;
    }    
    
    public Node(Node source) {
        this.nodeType = source.nodeType;
        this.id = source.id;
        this.order = source.order;
        this.parent = source.parent;
    }

    public Node addChield(NodeType nodeType, int id, int order){
        
        if( this.nodeType == NodeType.ARTICLE){
            throw new IllegalStateException("Нельзя добавить в узел наследника.");
        }
        
        if (children == null) {
            children = new TreeSet<>(new Comparator<Node>(){
                @Override
                public int compare(Node o1, Node o2) {
                    if( o1.nodeType != o2.nodeType ){
                        return o1.nodeType == NodeType.GROUP ? 1 : -1;
                    }                    
                    return o1.order - o2.order;
                }                
            });
        }
        
        Node child = new Node(nodeType, id, order, this);
        children.add(child);
        return child;
    }
    
    public Node getRoot(){
        Node root =  this;        
        while( root.getParent() != null) {root = root.getParent();}
        return root;        
    };


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

    //    public void addChild(ClassifierNode to, ClassifierNode from) {
//        ClassifierNode child = new ClassifierNode(from.getNodeType(), from.getId(), from.getOrder(), to);
//        to.addChild(child);
//        if (!from.isLeaf()) {
//            for (ClassifierNode node : from.getChildren()) {
//                addChild(child, node);
//            }
//        }
//
//    }
    
    public NodeType getNodeType() {
        return nodeType;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public Node getParent() {
        return parent;
    }

    public Collection<? extends Node> getChildren() {
        return children;
    }    
    
    @Override
    public boolean equals(Object object) {

        if (this == object) return true;
        if (object == null) return false;
        
        if (object instanceof Node) {
            Node node = (Node) object;
            return this.nodeType == node.nodeType && this.id == node.id;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.nodeType);
        hash = 29 * hash + this.id;
        return hash;
    }

}
