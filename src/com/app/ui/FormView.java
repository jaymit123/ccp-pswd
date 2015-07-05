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

    private JPanel MainPanel = null;
    private JLabel UsernameLabel, PasswordLabel;
    private JButton Submit;
    private JTextField UsernameField = null;
    private JPasswordField PasswordField = null;
    private DisableUI layerui;
    private JLayer<JPanel> FormLayer;

    public FormView(String button) {
        initPanel();
        initOtherComponents(button);
        addComponents();
    }

    private void initPanel() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new MigLayout("center, gapy 20"));
        layerui = new DisableUI();
        FormLayer = new JLayer<JPanel>(MainPanel, layerui);
    }

    private void initOtherComponents(String button) {
        UsernameField = new JTextField(8);
        PasswordField = new JPasswordField(8);
        UsernameLabel = new JLabel("Username");
        PasswordLabel = new JLabel("Password");
        Submit = new JButton(button);
        Submit.setFocusPainted(false);
    }

    public void addButtonAction(ActionListener al) {
        Submit.addActionListener(al);
    }

    public void disableUI() {
        layerui.startDisableUI();
        Submit.setEnabled(false);
    }

    public void enableUI() {
        layerui.stopDisableUI();
        Submit.setEnabled(true);
    }

    public void resetUI() {
        UsernameField.setText("");
        PasswordField.setText("");

    }

    public String[] getAllFields() {
        return new String[]{UsernameField.getText(), PasswordField.getPassword().toString()};
    }

    private void addComponents() {
        MainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase1", TitledBorder.LEFT, TitledBorder.TOP));
        MainPanel.add(UsernameLabel);
        MainPanel.add(UsernameField, "wrap");
        MainPanel.add(PasswordLabel);
        MainPanel.add(PasswordField, "span");
        MainPanel.add(Submit, "span, split, center");
        FormLayer = new JLayer<JPanel>(MainPanel, layerui);

    }

    public JLayer<JPanel> getComponent() {
        return FormLayer;
    }
}
