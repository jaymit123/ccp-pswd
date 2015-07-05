/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;
import javax.swing.Timer;

/**
 *
 * @author VJ
 */
public class DisableUI extends LayerUI<JPanel> implements ActionListener {

    private Timer FadeTimer = null;
    private boolean disableIsRunning = false;
    private boolean isFadingOut = false;
    private int FadeCount, FadeLimit = 15;

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        int w = c.getWidth();
        int h = c.getHeight();
        if (!disableIsRunning) {
            return;
        }
        Graphics2D g2 = ((Graphics2D) g);
        Composite savedcomp = (Composite) g2.getComposite();
        float fade = (float) FadeCount / (float) FadeLimit;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f * fade));
        g2.fillRect(0, 0, w, h);
        g2.dispose();

    }

    public void startDisableUI() {
        if (disableIsRunning) {
            return;
        }
        disableIsRunning = true;
        isFadingOut = false;
        FadeCount = 0;
        initTimer();
    }

    public void stopDisableUI() {
        if (!disableIsRunning) {
            return;
        }
        isFadingOut = true;
        initTimer();
    }

    public void initTimer() {
        int fps = 25;
        int tick = 1000 / fps;
        FadeTimer = new Timer(tick, this);
        FadeTimer.start();
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    public void uninstallUI(JComponent c) {
        ((JLayer) c).setLayerEventMask(0);
        super.uninstallUI(null);
    }

    @Override
    public void eventDispatched(AWTEvent e, JLayer l) {
        if (disableIsRunning && e instanceof InputEvent) {
            ((InputEvent) e).consume();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (disableIsRunning) {
            if (!isFadingOut) {
                if (FadeCount < FadeLimit) {
                   if(++FadeCount == FadeLimit) FadeTimer.stop();
                }
            } else {
                --FadeCount;
                if (FadeCount == 0) {
                    disableIsRunning = false;
                    isFadingOut = false;
                    FadeTimer.stop();
                } else {

                }
            }
            firePropertyChange("disableui", 0, 1);
        }
    }

    @Override
    public void applyPropertyChange(PropertyChangeEvent evt, JLayer<? extends JPanel> l) {
        if ("disableui".equals(evt.getPropertyName())) {
            l.repaint();
        }
    }

}
