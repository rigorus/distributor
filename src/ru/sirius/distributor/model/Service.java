package ru.sirius.distributor.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;
import ru.sirius.distributor.db.DbUtils;


public class Service {

    
    public void makeDelivery(Map<Article,Integer> order){
        
    }
    
    public static DefaultMutableTreeNode createClassificationTree() throws SQLException{
        
        Connection connection = DbUtils.getConnection();
        String SQL = "SELECT * FROM nomenclature.classification ORDER BY breadth_index";
        HashMap<Integer, DefaultMutableTreeNode> classifications = new HashMap<>();
        int rootIndex = 0;
        
        try( Statement statement = connection.createStatement()){
            ResultSet set = statement.executeQuery(SQL);
            while(set.next()){
                Classificator classification = new Classificator();
                int id = set.getInt("classification_id");
                int parentId = set.getInt("parent_id");
                classification.setId(id);
                classification.setParentId(parentId);
                classification.setName(set.getString("classification_name"));
                classification.setComment(set.getString("comment"));
                
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(classification);    
                node.setAllowsChildren(true);
                classifications.put(classification.getId(), node);
                if( parentId != 0){
                    DefaultMutableTreeNode parentNode = classifications.get(parentId);
                    parentNode.add(node);
                }else{
                    rootIndex = id;
                }                
            }
        }
        
        return classifications.get(rootIndex);
    } 
            
}
