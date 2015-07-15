/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author VJ
 */
public class ImagePanel extends JPanel {

    private BufferedImage DisplayImage = null;
    private int x = -1;
    private int y = -1;

    public void setValues(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValuesEmpty() {
        return x == -1 || y == -1;
    }

    public void paintImage(BufferedImage bi) {
        DisplayImage = bi;
        repaint();                                                // repaint calls paintComponent method internally
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(DisplayImage, x, y, this);   // To Paint the image on the panel
    }
}
