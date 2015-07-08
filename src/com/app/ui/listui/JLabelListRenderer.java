/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.listui;

import com.app.io.ImageModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
        g.drawImage(paintImage, 4, 4, this.getWidth() - 8, this.getHeight() - 8, null);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        try {
            paintImage = ImageIO.read(new File(System.getProperty("user.home")+"/Desktop/resources/Thumbnails/"+value));
            repaint();
        } catch (IOException ex) {
            System.out.print("Exception in JLabelListRenderer");
            Logger.getLogger(JLabelListRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

        } else {
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setOpaque(true);

        return this;
    }
}
