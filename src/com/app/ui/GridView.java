/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Color;
import java.awt.Cursor;
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

    private DisableUI layerui = null;
    private JLayer<JPanel> gridlayer = null;
    private JLabel GridLabel[] = new JLabel[81];
    private ImagePanel MainPanel = null;

    public GridView() {
        initPanel();
        initGrids();
    }

    private void initPanel() {
        MainPanel = new ImagePanel();
        MainPanel.setLayout(new GridLayout(9, 9, 0, 0));                 //creates layout to place labels in grid form
        MainPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        layerui = new DisableUI();
        gridlayer = new JLayer<>(MainPanel, layerui);
    }

    private void initGrids() {
        for (int i = 0; i < 81; i++) {
            GridLabel[i] = new JLabel();
            GridLabel[i].setOpaque(false);
            GridLabel[i].setName("" + (i + 1));         // Since for loop index is 0 to 80, we add 1 to the name to make it 1 to 81
            MainPanel.add(GridLabel[i]);              //add it to MainPanel
        }
    }

    public void setGridBorder(boolean isVisible) {
        if (isVisible) {
            if (GridLabel[0].getBorder() == null) {// checks if Border of first grid is transparent or opaque
                for (JLabel BorderLabel : GridLabel) {
                    BorderLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }

            }
        } else {
            if (GridLabel[0].getBorder() != null) {
                for (JLabel BorderLabel : GridLabel) {
                    BorderLabel.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }

    public void setGridActions(MouseListener ml) {
        for (JLabel AddAction : GridLabel) {
            AddAction.addMouseListener(ml);
        }
    }

    public void setImage(Image img) {
        MainPanel.paintImage(img);
    }

    public void disableUI() {                    
        layerui.startDisableUI();
    }

    public void enableUI() {                   
        layerui.stopDisableUI();
    }

    public void reset() {
        MainPanel.paintImage(null);
    }

    public JLayer<JPanel> getComponent() {
        return gridlayer;
    }

}
