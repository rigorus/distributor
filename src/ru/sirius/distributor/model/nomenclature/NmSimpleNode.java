/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sirius.distributor.model.nomenclature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import javax.swing.tree.TreeNode;
import ru.sirius.distributor.db.NomenclatureHelper;


public class NmSimpleNode implements NmNode{
    
    static public final Enumeration<NmSimpleNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
    
    protected NodeType type;
    protected int id;
    protected NmSimpleNode parent;
    protected ArrayList<NmSimpleNode> children;

    public NmSimpleNode(NodeType type, int id) {
        this.type = type;
        this.id = id;
    }

    public NmSimpleNode(NodeType type, int id, NmSimpleNode parent, ArrayList<NmSimpleNode> children) {
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
            children = new ArrayList<>();            
        }        
        children.add(child);
    }
    
        
    @Override
    public Enumeration<NmSimpleNode> children() {
        if (children == null) {
            return EMPTY_ENUMERATION;
        } else {

            return new Enumeration<NmSimpleNode>() {
                
                Iterator<NmSimpleNode> iterator =  children.iterator();

                @Override
                public boolean hasMoreElements() {
                    return iterator.hasNext();
                }

                @Override
                public NmSimpleNode nextElement() {
                    return iterator.next();
                }
            };
        }
    }
    
    @Override
    public NmSimpleNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children != null ? children.size() : 0;
    }

//    public NmSimpleNode getChild(NodeType type, int id) {
//        if (children != null) {
//            for (NmSimpleNode node : children) {
//                if (node.type == type && node.id == id) {
//                    return node;
//                }
//            }
//        }
//        return null;
//    }

    @Override
    public int getIndex(TreeNode node) {
        if (node == null) {
            throw new IllegalArgumentException("argument is null");
        }

        if (!isNodeChild(node)) {
            return -1;
        }
        return children.indexOf(node);
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        return children == null;
    }

    @Override
    public String toString() {
        return type == NodeType.ARTICLE
                ? NomenclatureHelper.getARTICLES().get(id).getFullName()
                : NomenclatureHelper.getGROUPS().get(id).getName();
    }



//    public boolean identical(Object object) {
//
//        if (object == null) {
//            return false;
//        }
//        if (this == object) {
//            return true;
//        }
//        if (object instanceof NmNode) {
//            NmNode node = (NmNode) object;
//            return this.type == node.getType()
//                    && this.id == node.getId();
//        }
//
//        return false;
//    }
    
    
    public boolean isNodeChild(TreeNode node) {
        boolean retval;

        if (node == null) {
            retval = false;
        } else {
            if (getChildCount() == 0) {
                retval = false;
            } else {
                retval = (node.getParent() == this);
            }
        }

        return retval;
    }
    
    public NmSimpleNode getSubTree() {

        NmSimpleNode node = this;

        ArrayList<NmSimpleNode> parents = new ArrayList<>();
        while (node.parent != null) {
            parents.add(node.parent);
            node = node.parent;
        }

        if (parents.isEmpty()) {
            return this;
        }

        Collections.reverse(parents);

        NmSimpleNode root = null;
        NmSimpleNode parent = null;
        for (int i = 0; i < parents.size(); ++i) {
            node = parents.get(i);
            NmSimpleNode clone = new NmSimpleNode(node.type, node.id, parent, null);
            if (i == 0) {
                root = clone;
            } else {
                parent.addChild(clone);
            }
            parent = clone;
        }

        parent.addChild(new NmSimpleNode(type, id, parent, children));
        return root;
    }

}
