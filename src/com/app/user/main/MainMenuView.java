/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import javax.swing.JPanel;
import javax.swing.JButton;
import com.app.beans.Viewable;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author VJ
 */
public class MainMenuView implements Viewable {

    private JPanel mainPanel;
    private JButton loginBtn, registerBtn;

    public MainMenuView() {
        initMainPanel();
        initButtons();
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
        mainPanel.add(registerBtn, "span, split, center");
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
