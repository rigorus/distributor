/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.distributor.model.nomenclature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TreeSet;


public class NmSimpleNode implements NmNode{
    
    static public final Enumeration<NmNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
    
    protected NodeType type;
    protected int id;
    protected NmSimpleNode parent;
    protected TreeSet<NmSimpleNode> children;

    public NmSimpleNode(NodeType type, int id) {
        this.type = type;
        this.id = id;
    }

    public NmSimpleNode(NodeType type, int id, NmSimpleNode parent, TreeSet<NmSimpleNode> children) {
        this.type = type;
        this.id = id;
        this.parent = parent;
        this.children = children;
    }


    @Override
    public NmSimpleNode getParent() {
        return parent;
    }

    @Override
    public NodeType getType() {
        return type;
    }

    @Override
    public int getId() {
        return id;
    }
    
    //TODO завести comparator !!!
    protected void addChild(NmSimpleNode child){
        if( children == null){
            children = new TreeSet<>();
            children.add(child);
        }        
    }
    
    @Override
    public Enumeration<NmNode> getChildren() {
        if (children == null) {
            return EMPTY_ENUMERATION;
        } else {

            return new Enumeration<NmNode>() {
                
                Iterator<NmSimpleNode> iterator =  children.iterator();

                @Override
                public boolean hasMoreElements() {
                    return iterator.hasNext();
                }

                @Override
                public NmNode nextElement() {
                    return iterator.next();
                }
            };
        }
    }
    
    public NmNode getSubTree(){

        NmSimpleNode node = this;

        ArrayList<NmSimpleNode> parents = new ArrayList<>();
        while( node.parent != null ){            
            parents.add(node.parent);
            node = node.parent;
        }
        
        if( parents.isEmpty()){
            return this;
        }
        
        Collections.reverse(parents);
        
        NmSimpleNode root = null;
        NmSimpleNode parent = null;
        for( int i=0; i<parents.size(); ++i){
            node = parents.get(i);
            NmSimpleNode clone = new NmSimpleNode(node.type, node.id, parent, null);
            if( i == 0){
                root = clone;
            }else{
                parent.addChild(clone); 
            }
            parent = clone;
        }
        
        parent.addChild(new NmSimpleNode(type, id, parent, children));
        return root;
    }
    
}
