package ru.sirius.distributor.model;

public class SelectedNode extends ClassifierNode {

    public SelectedNode(NodeType nodeType, int id) {
        super(nodeType, id);
    }

    public SelectedNode(NodeType nodeType, int id, ClassifierNode parent) {
        super(nodeType, id, parent);
    }

    public void addNode(ClassifierNode root, ClassifierNode node) {

        while (node.getParent() != null) {
            node = new ClassifierNode(node.getParent());
        }

        mergeNode(root, node);
    }

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
