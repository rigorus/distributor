/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sirius.distributor.model.nomenclature;

import java.util.Map;


//TODO предполагается копировать каждый раз с оригинала 
// Реализовать два интерфейса один для изменения другой для работы 
public class NmSimpleTree {
    
    private final NmSimpleNode root;
    private Map<Integer, NmSimpleNode> nodes;
    
    
    private NmSimpleTree() {
        // постройка дерева !!!
        root = null;
    }
    
    public static NmSimpleTree getInstance() {
        return NmSimpleTreeHolder.INSTANCE;
        // Здесь должно быть клонирование
        // После клонирования считаем неизменяемым !!!
    }
    
    private static class NmSimpleTreeHolder {
        private static final NmSimpleTree INSTANCE = new NmSimpleTree();
    }

    public NmSimpleNode getRoot() {
        return root;
    }
              
    
}
