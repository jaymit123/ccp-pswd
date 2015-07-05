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

    private JPanel MainPanel, CardPanel;
    private JButton LoginBtn, RegisterBtn;

    public MainMenuView() {
        initMainPanel();
        initButtons(); 
    }

    private void initMainPanel() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new MigLayout("center,gapy 20"));
    }

    private void initButtons() {
        LoginBtn = new JButton("Login");
        RegisterBtn = new JButton("Register");
        MainPanel.add(LoginBtn, "span, split, center,wrap");
        MainPanel.add(RegisterBtn, "span, split, center");
    }

    public void addLoginAction(ActionListener al) {
        LoginBtn.addActionListener(al);
    }

    public void addRegisterAction(ActionListener al) {
        RegisterBtn.addActionListener(al);
    }
    
    
    public JPanel getPanel(){
        return MainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

    }
}
