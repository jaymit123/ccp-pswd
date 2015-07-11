/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;

/**
 *
 * @author VJ
 */
public class GridView {

    private DisableUI layerUi = null;
    private JLayer<JPanel> gridLayer = null;
    private ImagePanel mainPanel = null;
    private int gridNos = 9;
    private JLabel gridLabel[] = new JLabel[gridNos * gridNos];

    public GridView() {
        initPanel();
        initGrids();

    }

    private void initPanel() {
        mainPanel = new ImagePanel();
        mainPanel.setLayout(new GridLayout(gridNos, gridNos, 0, 0));                 //creates layout to place labels in grid form
        layerUi = new DisableUI();
        gridLayer = new JLayer<>(mainPanel, layerUi);
    }

    private void initGrids() {
        for (int i = 0; i < gridNos * gridNos; i++) {
            gridLabel[i] = new JLabel();
            gridLabel[i].setOpaque(false);
            gridLabel[i].setName("" + (i + 1));         // Since for loop index is 0 to 80, we add 1 to the name to make it 1 to 81
            mainPanel.add(gridLabel[i]);              //add it to MainPanel
        }
    }

    public void setGridBorder(boolean isVisible) {
        if (isVisible) {
            if (gridLabel[0].getBorder() == null) {// checks if Border of first grid is transparent or opaque
                for (JLabel BorderLabel : gridLabel) {
                    BorderLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

            }
        } else {
            if (gridLabel[0].getBorder() != null) {
                for (JLabel BorderLabel : gridLabel) {
                    BorderLabel.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }

    public void setPanelBorder(boolean visible) {
        if (visible) {
            mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } else {
            mainPanel.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    public void setGridActions(MouseListener ml) {
        for (JLabel AddAction : gridLabel) {
            AddAction.addMouseListener(ml);
        }
    }

    public void setImage(Image img) {
        if (mainPanel.isValuesEmpty()) {
            mainPanel.setValues(gridLabel[0].getX(), gridLabel[0].getY(), gridLabel[0].getWidth() * (gridNos), gridLabel[0].getHeight() * (gridNos));
        }
        mainPanel.paintImage(img);
    }

    public void disableUI() {
        layerUi.startDisableUI();
    }

    public void enableUI() {
        layerUi.stopDisableUI();
    }

    public void reset() {
        mainPanel.paintImage(null);
    }

    public JLayer<JPanel> getComponent() {
        return gridLayer;
    }

}
