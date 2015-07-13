/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import org.imgscalr.Scalr;

/**
 *
 * @author VJ
 */
public class GridView {
    protected DisableUI layerUi = null;
    protected JLayer<JPanel> mainLayer = null;
    protected ImagePanel mainPanel = null;
    protected int gridNos = 9;
    protected JLabel gridLabel[][] = new JLabel[gridNos][gridNos];
    protected int gridX = -1, gridY = -1, gridWidth = -1, gridHeight = -1;
    protected boolean isGridInitialized = false;

    public GridView() {
        initPanel();
        initGrids();
        initLayer();
    }

    private void initPanel() {
        mainPanel = new ImagePanel();
        mainPanel.setLayout(new GridLayout(gridNos, gridNos, 0, 0));                 //creates layout to place labels in grid form

    }

    private void initLayer() {
        layerUi = new DisableUI();
        mainLayer = new JLayer<>(mainPanel, layerUi);
    }

    private void initGrids() {
        int number = 1;                       //assign number from 1 to (gridNo*gridNo)
        for (int i = 0; i < gridNos; i++) {
            for (int j = 0; j < gridNos; j++) {
                gridLabel[i][j] = new JLabel();
                gridLabel[i][j].setOpaque(false);
                gridLabel[i][j].setName("" + (number));         // Since for loop index is 0 to 80, we add 1 to the name to make it 1 to 81
                mainPanel.add(gridLabel[i][j]);              //add it to MainPanel
                ++number;
            }
        }
    }

    public void setGridBorder(boolean isVisible) {
        if (isVisible) {
            if (gridLabel[0][0].getBorder() == null) {// checks if Border of first grid is transparent or opaque
                for (int i = 0; i < gridNos; i++) {
                    for (int j = 0; j < gridNos; j++) {
                        gridLabel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }
                }

            }
        } else {
            if (gridLabel[0][0].getBorder() != null) {
                for (int i = 0; i < gridNos; i++) {
                    for (int j = 0; j < gridNos; j++) {
                        gridLabel[i][j].setBorder(BorderFactory.createEmptyBorder());
                    }
                }
            }
        }
    }

    protected void setupGridView() {
        gridX = gridLabel[0][0].getX();
        gridY = gridLabel[0][0].getY();
        gridWidth = gridLabel[0][0].getWidth();
        gridHeight = gridLabel[0][0].getHeight();
        mainPanel.setValues(gridX, gridY);
    }

    protected BufferedImage processImage(BufferedImage img) {
        if (!isGridInitialized) {
            setupGridView();
            isGridInitialized = true;
        }
        return Scalr.resize(img, Scalr.Mode.FIT_EXACT, gridWidth * gridNos, gridHeight * gridNos, Scalr.OP_ANTIALIAS);
    }

    public void setImage(BufferedImage img) {
        img = processImage(img);
        mainPanel.paintImage(img);
    }

    public void setPanelBorder(boolean visible) {
        if (visible) {
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } else {
            mainPanel.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    public void setGridActions(MouseListener ml) {
        for (int i = 0; i < gridNos; i++) {
            for (int j = 0; j < gridNos; j++) {
                gridLabel[i][j].addMouseListener(ml);
            }
        }
    }

    public void disableUI() {
        layerUi.startDisableUI();
    }

    public void enableUI() {
        layerUi.stopDisableUI();
    }

    public void resetImage() {
        mainPanel.paintImage(null);
    }

    public JLayer<JPanel> getComponent() {
        return mainLayer;
    }

}
