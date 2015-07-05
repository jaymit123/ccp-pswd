/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.listui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author VJ
 */
public class JLabelListRenderer extends JLabel implements ListCellRenderer<String> {

    private Image paintImage = null;

    JLabelListRenderer() {

        setSize(75, 75);
        setDoubleBuffered(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        g.drawImage(paintImage, 0, 0, this.getWidth() - 1, this.getHeight() - 1, null);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon a = null;
        try {
            // a = new ImageIcon(ImageIO.read(this.getClass().getResource(value.toString())).getScaledInstance(50,50, Image.SCALE_FAST));
            paintImage = ImageIO.read(this.getClass().getResource("/Images/"+value.toString()));
        } catch (IOException ex) {
            Logger.getLogger(JLabelListRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
        //  setIcon(a);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());

        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);

        return this;
    }
}
