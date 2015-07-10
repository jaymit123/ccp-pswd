/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Image;

/**
 *
 * @author VJ
 */
public class ImagePanel extends JPanel {

    private Image DisplayImage = null;
    private int x, y, w, h;

    public void setValues(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isValuesEmpty() {
        if (w == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void paintImage(Image bi) {
        DisplayImage = bi;
        repaint();                                                // repaint calls paintComponent method internally
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(DisplayImage, x, y, w, h, this);   // To Paint the image on the panel

    }
}
