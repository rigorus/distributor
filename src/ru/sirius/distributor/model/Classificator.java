package ru.sirius.distributor.model;

import java.util.List;



public class Classificator {

    private int id;
    private int parentId; // для корня 0
    private String name;           
    private String comment;
    private List<Integer> articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Integer> getArticles() {
        return articles;
    }

    public void setArticles(List<Integer> articles) {
        this.articles = articles;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public boolean hasParent(){
        return parentId != 0;
    }        
}
