package ru.sirius.distributor.ui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import ru.sirius.distributor.db.NomenclatureHelper;


public class ArticleTableModel extends AbstractTableModel{

    private ArrayList<Integer> classificatiors;
    private ArrayList<Integer> articles;

    public void setClassificatiors(ArrayList<Integer> classificatiors) {
        this.classificatiors = classificatiors;
    }

    
    @Override
    public int getRowCount() {
        return NomenclatureHelper.getARTICLES().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 1: 
                return NomenclatureHelper.getARTICLES().get(rowIndex).getId();
            case 0:
                return NomenclatureHelper.getARTICLES().get(rowIndex).getFullName();
            case 2: 
                return NomenclatureHelper.getARTICLES().get(rowIndex).getDescription();
            default:
                return NomenclatureHelper.getARTICLES().get(rowIndex).getComment();
        }
    }
    
    @Override
    public String getColumnName(int columnIndex){
        switch( columnIndex){
            case 0:
                return "Наименование";
            case 1:
                return "Количество";
            default:
                return "";
        }
    }
    
    

}
