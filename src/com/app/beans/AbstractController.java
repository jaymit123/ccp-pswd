/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 *
 * @author VJ
 */
public abstract class AbstractController implements PropertyChangeListener {

    protected ArrayList<AbstractModel> modelList;
    protected ArrayList<Viewable> viewList;
    protected Viewable mainMenuView;

    public AbstractController(Viewable mainMenu) {
        mainMenuView = mainMenu;
        modelList = new ArrayList<>();
        viewList = new ArrayList<>();
    }

    public void contactMainMenuView(PropertyChangeEvent evt) {
        mainMenuView.modelPropertyChange(evt);
    }

    public void addModel(AbstractModel inputModel) {
        modelList.add(inputModel);
        inputModel.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel inputModel) {
        modelList.remove(inputModel);
        inputModel.removePropertyChangeListener(this);
    }

    public void addView(Viewable inputView) {
        viewList.add(inputView);
    }

    public void removeView(Viewable inputView) {
        viewList.remove(inputView);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    protected void setModelProperty(String propertyName, Object newValue) {

        for (AbstractModel model : modelList) {
            try {
                Method method = model.getClass().
                        getMethod(propertyName, new Class[]{
                            newValue.getClass()
                        }
                        );
                method.invoke(model, newValue);

            } catch (Exception ex) {
                
                //  Handle exception.
            }
        }
    }

    protected void setModelProperty(String propertyName, Object newValue1, Object newValue2) {

        for (AbstractModel model : modelList) {
            try {
                Method method = model.getClass().
                        getMethod(propertyName, new Class[]{newValue1.getClass(), newValue2.getClass()}
                        );
                method.invoke(model, newValue1, newValue2);
            } catch (Exception ex) {
                //  Handle exception.
            }
        }
    }

}
