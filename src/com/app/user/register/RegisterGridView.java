/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.ui.GridView;
import java.awt.image.BufferedImage;
import javax.swing.JLayer;
import javax.swing.JPanel;

/**
 *
 * @author VJ
 */
public class RegisterGridView extends GridView {

    private RegisterGridUI regGridUI = null;
    private final int viewportDimensions = 2;  //number of rows / cols in viewport
    private final int digit[] = new int[2];
    private int gridsInViewport[] = new int[viewportDimensions * viewportDimensions]; //Total number of grids in viewport
private Random 
    @Override
    protected void initLayer() {
        regGridUI = new RegisterGridUI();
        gridLayer = new JLayer<>(mainPanel, regGridUI);
    }

    @Override
    public void enableUI() {
        regGridUI.stopDisableUI();
    }

    @Override
    public void disableUI() {
        regGridUI.startDisableUI();
    }

    @Override
    protected void setupGridView() {
        super.setupGridView(); //To change body of generated methods, choose Tools | Templates.
        regGridUI.setViewSize(gridWidth, gridHeight);
    }

    public void setPCCPImage(BufferedImage img) {
        img = processImage(img);
        mainPanel.paintImage(img);
        regGridUI.setViewportImage(img);
    }

}
