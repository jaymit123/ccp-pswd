/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import org.imgscalr.Scalr;
import com.app.ui.DisableUI;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;

/**
 *
 * @author VJ
 */
public class RegisterGridUI extends DisableUI {

    private int viewX = -1, viewY = -1, viewWidth = -1, viewHeight = -1;
    private boolean shuffleIsRunning = false;
    private BufferedImage viewportImage = null;
    private int[] viewportGrids;

    private void drawPCCPViewport(Graphics g, int w, int h) {
        Graphics2D g2 = ((Graphics2D) g);
        Color c = new Color(1.0f, 1.0f, 1.0f, 0.7f);
        g2.setPaint(c);
        g2.fillRect(0, 0, w, h);
        BufferedImage highlightGrid = Scalr.pad(Scalr.crop(viewportImage, viewX, viewY, viewWidth, viewHeight), 2, Color.BLACK, Scalr.OP_ANTIALIAS);
        g2.drawImage(highlightGrid, viewX, viewY, null);
        g2.dispose();
    }

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        int w = c.getWidth();
        int h = c.getHeight();
        if (shuffleIsRunning) {
            drawPCCPViewport(g, w, h);
        }
    }

    @Override
    public void startDisableUI() {
        if (shuffleIsRunning) {
            removeViewPort();
        }
        super.startDisableUI(); //To change body of generated methods, choose Tools | Templates.

    }

    public void setViewportImage(BufferedImage img) {

        viewportImage = img;

    }

    public boolean isViewValuesEmpty() {
        return (viewX == -1 && viewY == -1 && viewWidth == -1 && viewHeight == -1);
    }

    public void setViewPosition(int x, int y, int w, int h, int[] visibleGrid) {
        viewX = x;
        viewY = y;
        viewWidth = w;
        viewHeight = h;
        viewportGrids = visibleGrid;
    }

    public void placeViewport() {
        if (!shuffleIsRunning) {
            shuffleIsRunning = true;
            disableIsRunning = false;
        }
        firePropertyChange("shuffleui", 0, 1);
    }

    public void setViewPosition(int x, int y, int[] visibleGrid) {
        viewX = x;
        viewY = y;
        viewportGrids = visibleGrid;
    }

    public void setViewSize(int w, int h) {
        viewWidth = w;
        viewHeight = h;
    }

    public void removeViewPort() {
        if (!shuffleIsRunning) {
            return;
        }
        viewX = 0;
        viewY = 0;
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

    @Override
    public void eventDispatched(AWTEvent e, JLayer l) {
        if (disableIsRunning && e instanceof InputEvent) {
            ((InputEvent) e).consume();
        } else if (shuffleIsRunning && e instanceof InputEvent) {
            InputEvent ie = (InputEvent) e;
            processViewportRequest(ie);
        }

    }

    private void processViewportRequest(InputEvent e) {
        if (e.getSource() instanceof JLabel) {
            int gridNo = Integer.parseInt(((JLabel) e.getSource()).getName());
            for (int i = 0; i < viewportGrids.length; i++) {
                if (gridNo == viewportGrids[i]) {
                    break;
                }
                if ((i == (viewportGrids.length - 1)) && !(gridNo == viewportGrids[i])) {
                    e.consume();
                }
            }
        }
    }

}
