/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.beans.Viewable;
import com.app.ui.FormView;
import com.app.ui.GridView;
import com.app.user.register.RegisterStatus;
import com.app.user.security.ValidationStatus;
import com.app.user.status.ExceptionStatus;
import com.sun.glass.ui.Window;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author VJ
 */
public class LoginView implements Viewable {

    private LoginController LoginControl;
    private GridView P2Grid;
    private JPanel P2GridPanel;
    private JPanel P1TopPanel;
    private JPanel MainPanel;
    private FormView P1FormView;
    private JPanel GlobalBtnsPanel;
    private JButton Close, LogOut;

    public LoginView(LoginController logincntrl) {
        LoginControl = logincntrl;
        LoginControl.setLoginView(this);
        MainPanel = new JPanel(new BorderLayout());
        initPhase1();
        initPhase2();
        initAllActions();
        MainPanel.add(P1TopPanel, BorderLayout.NORTH);
        MainPanel.add(P2GridPanel, BorderLayout.CENTER);
    }

    private void initPhase1() {
        P1TopPanel = new JPanel(new BorderLayout());
        P1FormView = new FormView("Log In");
        initBtnsPanel();
        P1TopPanel.add(P1FormView.getComponent(), BorderLayout.CENTER);
        P1TopPanel.add(GlobalBtnsPanel, BorderLayout.EAST);
    }

    private void initBtnsPanel() {
        GlobalBtnsPanel = new JPanel(new MigLayout());
        GlobalBtnsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Global", TitledBorder.LEFT, TitledBorder.TOP));

        Close = new JButton("Close");
        LogOut = new JButton("Log Out");
        LogOut.setEnabled(false);
        GlobalBtnsPanel.add(Close, "center,wrap,gaptop 10");
        GlobalBtnsPanel.add(LogOut, "center,gaptop 10");
    }

    private void initPhase2() {
        P2GridPanel = new JPanel(new BorderLayout());
        P2GridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase2", TitledBorder.LEFT, TitledBorder.TOP));
        P2Grid = new GridView();
        P2Grid.setGridBorder(false);
        P2Grid.setPanelBorder(true);
        P2GridPanel.add(P2Grid.getComponent(), BorderLayout.CENTER);
        P2Grid.disableUI();
    }

    private void initAllActions() {
        initGlobalBtnActions();
        initPhase1Actions();
        initPhase2Actions();
    }

    private void initGlobalBtnActions() {
        Close.addActionListener((ActionEvent evt) -> {
            Restart();
            LoginControl.logOut();
        });

        LogOut.addActionListener((ActionEvent evt) -> {
            Restart();
            LoginControl.logOut();
        });

    }

    private void initPhase1Actions() {
        P1FormView.addButtonAction((ActionEvent evt) -> {
            String Details[] = P1FormView.getAllFields();
            LoginControl.loginUser(Details[0], Details[1]);

        });

    }

    private void initPhase2Actions() {
        P2Grid.setGridActions(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getSource() instanceof JLabel) {
                    int GridSelected = Integer.parseInt(((JLabel) me.getSource()).getName());
                    LoginControl.authenticateUser(GridSelected);
                }
            }

        });

    }

    private void Restart() {
        P1FormView.enableUI();
        P1FormView.resetUI();
        LogOut.setEnabled(false);
        P2Grid.setImage(null);
        P2Grid.disableUI();
    }

    public JPanel getPanel() {
        return MainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case "LoginStatus":
                handleLoginStatus((LoginStatus) pce.getNewValue());
                break;

            case "ValidationStatus":
                handleValidationStatus((ValidationStatus) pce.getNewValue());
                break;

            case "ExceptionStatus":
                handleExceptionMessage((ExceptionStatus) pce.getNewValue());
                break;
        }
    }

    private void handleValidationStatus(ValidationStatus vs) {
        switch (vs) {

            case BOTH_ERROR:
                JOptionPane.showMessageDialog(MainPanel, vs.getValidationMsg(), "Login Error", JOptionPane.ERROR_MESSAGE);
                P1FormView.resetUI();
                break;

            case ACC_DOESNT_EXIST:
                JOptionPane.showMessageDialog(MainPanel, vs.getValidationMsg(), "Login Error", JOptionPane.ERROR_MESSAGE);
                P1FormView.resetUI();
                break;
        }
    }

    private void handleExceptionMessage(ExceptionStatus es) {

        switch (es) {
            case FATAL_ERROR:
                JOptionPane.showMessageDialog(MainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                break;

            case OTHER_ERROR:
                JOptionPane.showMessageDialog(MainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                P1FormView.disableUI();
                P1FormView.resetUI();
                break;
        }
    }

    private void handleLoginStatus(LoginStatus ls) {
        switch (ls) {
            case INIT:
                P1FormView.disableUI();
                P2Grid.setImage(ls.getImage());
                P2Grid.enableUI();
                break;
            case CONTINUE:
                P2Grid.setImage(ls.getImage());
                break;
            case SUCCESS:
                JOptionPane.showMessageDialog(MainPanel, ls.getMessage(), "Login Success", JOptionPane.INFORMATION_MESSAGE);
                LogOut.setEnabled(true);
                Close.setEnabled(false);
                P2Grid.disableUI();
                P2Grid.setImage(null);
                break;

            case FAILURE:
                JOptionPane.showMessageDialog(MainPanel, ls.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
                Restart();
                break;

            case ERROR:
                JOptionPane.showMessageDialog(MainPanel, ls.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
                Restart();
                break;
        }
    }

}
