/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sirius.distributor.service;

import ru.sirius.distributor.model.nomenclature.NomenclatureTreePrototype;
import java.util.List;
import java.util.Map;
import ru.sirius.distributor.db.NomenclatureProvider;
import ru.sirius.distributor.model.Article;
import ru.sirius.distributor.model.Group;


// Класс для работы с деревом артикулов -> редактирования ...  
// при каждом редактировании version увеличивается на 1
public class NomenclatureService {
    
    private List<Article> articles;
    private List<Group> groups;
    private NomenclatureTreePrototypeImp prototype;
    private long version;
    
    private NomenclatureService() {        
        articles = NomenclatureProvider.getArticles();
        groups = NomenclatureProvider.getGroups();
    }
    
    public static NomenclatureService getInstance() {
        return NomenclatureServiceHolder.INSTANCE;
    }
    
    private static class NomenclatureServiceHolder {
        private static final NomenclatureService INSTANCE = new NomenclatureService();
    }
    
    
    public NomenclatureTreePrototype getPrototype(){
        
        synchronized(this){
            if( prototype.version == version) return prototype;              
            prototype = new NomenclatureTreePrototypeImp(articles, groups, version);
        }
        
        prototype.makeTree();
            
        return prototype;                        
    }
    
    
    private static class NomenclatureTreePrototypeImp implements NomenclatureTreePrototype{

        private Map<Integer, Article> articles;
        private Map<Integer, Group> groups;
        private long version = 0;

        private NomenclatureTreePrototypeImp(List<Article> articles, List<Group> groups, long version ) {
        
            this.version = version;            
            
            for (Article article : articles) {
                this.articles.put(article.getId(), article.clone());
            }

            for (Group group : groups) {
                this.groups.put(group.getId(), group.clone());
            }
        }

        private void makeTree() {
            // Здесь уже создается само дерево !!! !!! !!!
            
            
        }
        
        
        // реализация самих методов интерфейса !!!

    }
}
