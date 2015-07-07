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

    public void paintImage(Image bi) {
        DisplayImage = bi;
        repaint();                                                // repaint calls paintComponent method internally
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(DisplayImage, 2, 0, this.getWidth() - 5, this.getHeight() - 2, this);   // To Paint the image on the panel

    }
}
