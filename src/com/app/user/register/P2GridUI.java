/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import org.imgscalr.Scalr;
import com.app.ui.DisableUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;

/**
 *
 * @author VJ
 */
public class P2GridUI extends DisableUI {

    private int viewX, viewY, viewWidth, viewHeight;
    private boolean shuffleIsRunning = false;
    private BufferedImage viewportImage = null;

    private void drawViewport(Graphics g, int w, int h) {
        if (viewportImage != null) {
            Graphics2D g2 = ((Graphics2D) g);
            Color c = new Color(1.0f, 1.0f, 1.0f, 0.7f);
            g2.setPaint(c);
            g2.fillRect(0, 0, w, h);
            BufferedImage higlightGrid = Scalr.pad(Scalr.crop(viewportImage, viewX, viewY, viewWidth, viewHeight), 2, Color.BLACK, Scalr.OP_ANTIALIAS);
            g2.drawImage(higlightGrid, viewX, viewY, null);
            g2.dispose();
        }
    }

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        int w = c.getWidth();
        int h = c.getHeight();
        if (disableIsRunning) {
            disableComponent(g, w, h);
        } else if (shuffleIsRunning) {
            drawViewport(g, w, h);
        }
    }

    public void setViewportImage(BufferedImage i) {

        viewportImage = i;

    }

    public void placeViewport(int x, int y, int w, int h) {
        viewX = x;
        viewY = y;
        viewWidth = w;
        viewHeight = h;
        if (!shuffleIsRunning) {
            shuffleIsRunning = true;
        }
        firePropertyChange("shuffleui", 0, 1);

    }

    public void removeViewPort() {
        if (!shuffleIsRunning) {
            return;
        }
        viewX = 0;
        viewY = 0;
        viewWidth = 0;
        viewHeight = 0;
        shuffleIsRunning = false;
        viewportImage = null;
        firePropertyChange("shuffleui", 0, 1);
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent evt, JLayer<? extends JPanel> l) {
        if ("disableui".equals(evt.getPropertyName()) || "shuffleui".equals(evt.getPropertyName())) {
            l.repaint();
        }
    }

}
