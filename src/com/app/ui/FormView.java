/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui;

import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author VJ
 */
public class FormView {

    private JPanel mainPanel = null;
    private JLabel usernameLabel, passwordLabel;
    private JButton Submit;
    private JTextField usernameField = null;
    private JPasswordField passwordField = null;
    private DisableUI layerUI;
    private JLayer<JPanel> formLayer;

    public FormView(String button) {
        initPanel();
        initOtherComponents(button);
        addComponents();
    }

    private void initPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("center, gapy 20"));
        layerUI = new DisableUI();
        formLayer = new JLayer<JPanel>(mainPanel, layerUI);
    }

    private void initOtherComponents(String button) {
        usernameField = new JTextField(8);
        passwordField = new JPasswordField(8);
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
        Submit = new JButton(button);
        Submit.setFocusPainted(false);
    }

    public void addButtonAction(ActionListener al) {
        Submit.addActionListener(al);
    }

    public void disableUI() {
        layerUI.startDisableUI();
        Submit.setEnabled(false);
        usernameField.setEditable(false);
        passwordField.setEditable(false);
    }

    public void enableUI() {
        layerUI.stopDisableUI();
        resetUI();
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        Submit.setEnabled(true);
    }

    public void resetUI() {
        usernameField.setText("");
        passwordField.setText("");

    }

    public String[] getAllFields() {
        return new String[]{usernameField.getText(), String.valueOf(passwordField.getPassword())};
    }

    private void addComponents() {
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase1", TitledBorder.LEFT, TitledBorder.TOP));
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField, "wrap");
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField, "span");
        mainPanel.add(Submit, "span, split, center");
        formLayer = new JLayer<JPanel>(mainPanel, layerUI);

    }

    public JLayer<JPanel> getComponent() {
        return formLayer;
    }
}
