package ru.sirius.distributor.model;


public class ClassifierRow {

    public enum ROW_TYPE {GROUP, ARTICLE };
    
    private ROW_TYPE rowType;
    private int id;

    public ClassifierRow(ROW_TYPE rowType, int id) {
        this.rowType = rowType;
        this.id = id;
    }

    public ROW_TYPE getRowType() {
        return rowType;
    }

    public int getId() {
        return id;
    }       
}
