/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.listui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author VJ
 */
public class ImageListModel<E> extends AbstractListModel<E> {

    private List<E> input;

    public ImageListModel(List<E> inputList) {
        input = inputList;
    }

    public ImageListModel() {
        input = new ArrayList<>();
    }

    public boolean remove(E e) {
        return input.remove(e);
    }

    public boolean add(E addInput) {
        return input.add(addInput);
    }

    public boolean addAll(List<E> inputList) {
       return input.addAll(inputList);
    }

    @Override
    public int getSize() {
        return input.size();
    }

    @Override
    public E getElementAt(int index) {
        return input.get(index);
    }
    
    public void removeAll(){
        input.removeAll(input);
    }
    

}
