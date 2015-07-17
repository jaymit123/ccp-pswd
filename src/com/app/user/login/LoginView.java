/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.login;

import com.app.beans.Viewable;
import com.app.ui.FormView;
import com.app.ui.GridView;
import com.app.user.security.ValidationStatus;
import com.app.user.status.ExceptionStatus;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author VJ
 */
public class LoginView implements Viewable {

    private LoginController loginControl;
    private GridView p2Grid;
    private JPanel p2GridPanel;
    private JPanel p1TopPanel;
    private JPanel mainPanel;
    private FormView p1FormView;
    private JPanel globalBtnsPanel;
    private JButton close, logOut;

    public LoginView(LoginController logincntrl) {
        loginControl = logincntrl;
        loginControl.setLoginView(this);
        mainPanel = new JPanel(new BorderLayout());
        initPhase1();
        initPhase2();
        initAllActions();
        mainPanel.add(p1TopPanel, BorderLayout.NORTH);
        mainPanel.add(p2GridPanel, BorderLayout.CENTER);
    }

    private void initPhase1() {
        p1TopPanel = new JPanel(new BorderLayout());
        p1FormView = new FormView("Log In");
        initBtnsPanel();
        p1TopPanel.add(p1FormView.getComponent(), BorderLayout.CENTER);
        p1TopPanel.add(globalBtnsPanel, BorderLayout.EAST);
    }

    private void initBtnsPanel() {
        globalBtnsPanel = new JPanel(new MigLayout());
        globalBtnsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Global", TitledBorder.LEFT, TitledBorder.TOP));

        close = new JButton("Close");
        logOut = new JButton("Log Out");
        logOut.setEnabled(false);
        globalBtnsPanel.add(close, "center,wrap,gaptop 10");
        globalBtnsPanel.add(logOut, "center,gaptop 10");
    }

    private void initPhase2() {
        p2GridPanel = new JPanel(new BorderLayout());
        p2GridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase2", TitledBorder.LEFT, TitledBorder.TOP));
        p2Grid = new GridView();
        p2Grid.setPanelBorder(true);
        p2GridPanel.add(p2Grid.getComponent(), BorderLayout.CENTER);
        p2Grid.disableUI();
    }

    private void initAllActions() {
        initGlobalBtnActions();
        initPhase1Actions();
        initPhase2Actions();
    }

    private void initGlobalBtnActions() {
        close.addActionListener((ActionEvent evt) -> {
            Restart();
            loginControl.logOut();
        });

        logOut.addActionListener((ActionEvent evt) -> {
            Restart();
            loginControl.logOut();
        });

    }

    private void initPhase1Actions() {
        p1FormView.addButtonAction((ActionEvent evt) -> {
            String Details[] = p1FormView.getAllFields();
            loginControl.loginUser(Details[0], Details[1]);

        });

    }

    private void initPhase2Actions() {
        p2Grid.setGridActions(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getSource() instanceof JLabel) {
                    int GridSelected = Integer.parseInt(((JLabel) me.getSource()).getName());
                    loginControl.authenticateUser(GridSelected);
                }
            }

        });

    }

    private void Restart() {
        p1FormView.enableUI();
        p1FormView.resetUI();
        logOut.setEnabled(false);
        p2Grid.resetImage();
        p2Grid.disableUI();
    }

    public JPanel getPanel() {
        return mainPanel;
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
                handleExceptionStatus((ExceptionStatus) pce.getNewValue());
                break;
        }
    }

    private void handleValidationStatus(ValidationStatus vs) {
        switch (vs) {

            case BOTH_ERROR:
                JOptionPane.showMessageDialog(mainPanel, vs.getValidationMsg(), "Login Error", JOptionPane.ERROR_MESSAGE);
                p1FormView.resetUI();
                break;

            case ACC_DOESNT_EXIST:
                JOptionPane.showMessageDialog(mainPanel, vs.getValidationMsg(), "Login Error", JOptionPane.ERROR_MESSAGE);
                p1FormView.resetUI();
                break;
        }
    }

    private void handleExceptionStatus(ExceptionStatus es) {

        switch (es) {
            case FATAL_ERROR:
                JOptionPane.showMessageDialog(mainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ((JFrame) (SwingUtilities.getWindowAncestor(mainPanel))).dispose();

                break;

            case OTHER_ERROR:
                JOptionPane.showMessageDialog(mainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                p1FormView.disableUI();
                p1FormView.resetUI();
                break;
        }
    }

    private void handleLoginStatus(LoginStatus ls) {
        switch (ls) {
            case INIT:
                p1FormView.disableUI();
                p2Grid.setImage(ls.getImage());
                p2Grid.enableUI();
                break;
            case CONTINUE:
                p2Grid.setImage(ls.getImage());
                break;
            case SUCCESS:
                JOptionPane.showMessageDialog(mainPanel, ls.getMessage(), "Login Success", JOptionPane.INFORMATION_MESSAGE);
                logOut.setEnabled(true);
                close.setEnabled(false);
                p2Grid.disableUI();
                p2Grid.resetImage();
                break;

            case FAILURE:
            case ERROR:
                JOptionPane.showMessageDialog(mainPanel, ls.getMessage(), "Login Failed", JOptionPane.ERROR_MESSAGE);
                Restart();
                break;

        }
    }

}
