/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import javax.swing.JPanel;
import javax.swing.JButton;
import com.app.beans.Viewable;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author VJ
 */
public class MainMenuView implements Viewable {

    private JPanel mainPanel;
    private JButton loginBtn, registerBtn;
    private JLabel aboutLabel;

    public MainMenuView() {
        initMainPanel();
        initButtons();
        initAboutLabel();
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("center,gapy 20"));
    }

    private void initButtons() {
        loginBtn = new JButton("Login");
        loginBtn.setFocusPainted(false);
        registerBtn = new JButton("Register");
        loginBtn.setFocusPainted(false);
        mainPanel.add(loginBtn, "span, split, center,wrap");
        mainPanel.add(registerBtn, "span, split, center,wrap");
    }

    private void initAboutLabel() {
        aboutLabel = new JLabel();
        aboutLabel.setToolTipText("Click for more information");
        try{
        Image im = ImageIO.read(MainMenuView.class.getResource("abouticon.png"));
        aboutLabel.setIcon(new ImageIcon(im));
        }catch(IOException ex){
            aboutLabel.setText("About");
        }
        initAboutAction();
        mainPanel.add(aboutLabel, "center");
    }

    private void initAboutAction() {
        aboutLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                JOptionPane.showMessageDialog(mainPanel, "Created by Jaymit Desai\n email id: jaymitd123@gmail.com", "About Me", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }

    public void addLoginAction(ActionListener al) {
        loginBtn.addActionListener(al);
    }

    public void addRegisterAction(ActionListener al) {
        registerBtn.addActionListener(al);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

    }
}
