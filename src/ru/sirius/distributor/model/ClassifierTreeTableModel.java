
package ru.sirius.distributor.model;

import java.util.HashMap;
import java.util.Map;
import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import ru.sirius.distributor.db.NomenclatureHelper;


public class ClassifierTreeTableModel extends AbstractTreeTableModel{

    private Map<Integer,Classificator> groups;
    private Map<Integer, Article> articles;
    private ClassifierNode rootNode;         
    private Map<Integer, ClassifierNode> nodes = new HashMap<>();

    public ClassifierTreeTableModel() {        

        this.articles = NomenclatureHelper.getARTICLES();
        this.groups = NomenclatureHelper.getCLASSIFICATIONS();        

        for (Classificator group : groups.values()) {
            ClassifierNode node = new ClassifierNode(ClassifierNode.NodeType.GROUP, group.getId());
            nodes.put(group.getId(), node);
            for(int article : group.getArticles()){
                ClassifierNode child = new ClassifierNode(ClassifierNode.NodeType.ARTICLE, article,node);
                node.addChild(child);
            }
        }

        for (ClassifierNode node : nodes.values()) {
            Classificator group = groups.get(node.getId());
            if (group.getParentId() == 0) {
                rootNode = node;
            } else {
                nodes.get(group.getParentId()).addChild(node);
            }
        }
    }    
    
    @Override
    public Object getRoot() {
        return rootNode;
    }
    
    public void setRoot(int id){
        rootNode =  nodes.get(id);
    }       
    
    @Override
    public boolean isLeaf(Object node) {
        ClassifierNode treenode = (ClassifierNode) node;
        return treenode.isLeaf();
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(Object object, int columnIndex) {
        
        ClassifierNode node = (ClassifierNode)object;
        boolean isGroup = node.isType(ClassifierNode.NodeType.GROUP);
        int id = node.getId();
        
        switch(columnIndex){
            case 0: 
                return isGroup ? groups.get(id).getName() : articles.get(id).getFullName();
            case 1:
                return isGroup ? null : articles.get(id).getId();                
            default:
                return "Unknown";
        }        
    }

    @Override
    public Object getChild(Object parent, int index) {
        ClassifierNode node = (ClassifierNode) parent;
        return node.getChildAt(index);
    }

    @Override
    public int getChildCount(Object parent) {
        ClassifierNode node = (ClassifierNode) parent;
        return node.getChildCount();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        ClassifierNode node = (ClassifierNode) parent;
        return node.getIndex((ClassifierNode) child);
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Наименование";
            case 1:
                return "Количество";
            default:
                return "Unknown";
        }
    }
}
