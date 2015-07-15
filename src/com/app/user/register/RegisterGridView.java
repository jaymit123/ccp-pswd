/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.ui.GridView;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JLayer;

/**
 *
 * @author VJ
 */
public class RegisterGridView extends GridView {

    private RegisterGridUI regGridUI;
    private final int viewportDimensions = 2;  //number of rows or cols in viewport
    private final int digit[] = new int[2];
    private final int gridsInViewport[] = new int[viewportDimensions * viewportDimensions]; //Total number of grids in viewport
    private Random shuffleRandom;

    public RegisterGridView() {
        layerUI = new RegisterGridUI();
        mainLayer = new JLayer<>(mainPanel, layerUI);
        regGridUI = (RegisterGridUI) layerUI;
        shuffleRandom = new Random();
    }

    private void shuffleNumberGen() {
       digit[0] = shuffleRandom.nextInt(gridNos - viewportDimensions);
       digit[1] = shuffleRandom.nextInt(gridNos - viewportDimensions);
    }

    public void startShuffle() {
        shuffleNumberGen();

        int index = 0;
        for (int i = 0; i < viewportDimensions; i++) {
            for (int j = 0; j < viewportDimensions; j++) {
                gridsInViewport[index] = Integer.parseInt(gridLabel[digit[0] + i][digit[1] + j].getName());
                ++index;
            }
        }

        int x = gridLabel[digit[0]][digit[1]].getX();
        int y = gridLabel[digit[0]][digit[1]].getY();

        regGridUI.setViewPosition(x, y, gridsInViewport);
        regGridUI.placeViewport();
    }

    public void stopShuffle() {
        regGridUI.removeViewPort();
    }

    @Override
    protected void setupGridView() {
        super.setupGridView(); //To change body of generated methods, choose Tools | Templates.
        regGridUI.setViewSize(gridWidth * viewportDimensions, gridHeight * viewportDimensions);
    }

    public void setPCCPImage(BufferedImage img) {
        img = processImage(img);
        mainPanel.paintImage(img);
        regGridUI.setViewportImage(img);
    }

}
