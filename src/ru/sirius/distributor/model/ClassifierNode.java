package ru.sirius.distributor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Objects;
import javax.swing.tree.TreeNode;
import ru.sirius.distributor.db.NomenclatureHelper;

public class ClassifierNode implements TreeNode {

    public enum NodeType {

        GROUP, ARTICLE
    };
    static public final Enumeration<TreeNode> EMPTY_ENUMERATION = Collections.emptyEnumeration();
    private NodeType nodeType;
    private int id;
    private int order;
    private ClassifierNode parent;
    private ArrayList<ClassifierNode> children = null;

    public ClassifierNode(NodeType nodeType, int id) {
        this.nodeType = nodeType;
        this.id = id;
    }

    public ClassifierNode(NodeType nodeType, int id, int order, ClassifierNode parent) {
        this.nodeType = nodeType;
        this.id = id;
        this.order = order;
        this.parent = parent;
    }

    public ClassifierNode(ClassifierNode node) {
        this.nodeType = node.nodeType;
        this.id = node.id;
        this.order = node.order;
        this.parent = node.parent;
    }

    public ClassifierNode(NodeType nodeType, int id, ClassifierNode parent) {
        this.parent = parent;
        this.nodeType = nodeType;
        this.id = id;
    }

    public void addChild(ClassifierNode child) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(child);
        Collections.sort(children, new Comparator<ClassifierNode>() {
            @Override
            public int compare(ClassifierNode o1, ClassifierNode o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
    }

    public boolean isType(NodeType nodeType) {
        return this.nodeType == nodeType;
    }

    public int getId() {
        return id;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    @Override
    public ClassifierNode getParent() {
        return parent;
    }

    public ArrayList<ClassifierNode> getChildren() {
        return children;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public ClassifierNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return children != null ? children.size() : 0;
    }

    public ClassifierNode getChild(NodeType type, int id) {
        if (children != null) {
            for (ClassifierNode node : children) {
                if (node.isType(nodeType) && node.id == id) {
                    return node;
                }
            }
        }
        return null;
    }

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
    public Enumeration children() {
        if (children == null) {
            return EMPTY_ENUMERATION;
        } else {

            return new Enumeration<ClassifierNode>() {
                int count = 0;

                @Override
                public boolean hasMoreElements() {
                    return count < children.size();
                }

                @Override
                public ClassifierNode nextElement() {
                    if (count < children.size()) {
                        return children.get(count++);
                    }
                    throw new NoSuchElementException("ClassifierNode Enumeration");
                }
            };
        }
    }

    @Override
    public String toString() {
        return nodeType == NodeType.ARTICLE
                ? NomenclatureHelper.getARTICLES().get(id).getFullName()
                : NomenclatureHelper.getGROUPS().get(id).getName();
    }

    public void setParent(ClassifierNode parent) {
        this.parent = parent;
    }

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

    @Override
    public boolean equals(Object object) {

        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (object instanceof ClassifierNode) {
            ClassifierNode node = (ClassifierNode) object;
            return this.nodeType == node.nodeType
                    && this.id == node.id;
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
