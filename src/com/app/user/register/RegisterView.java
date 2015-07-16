/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register;

import com.app.ui.FormView;
import com.app.beans.Viewable;
import com.app.ui.DisableUI;
import com.app.ui.listui.ListView;
import com.app.user.security.ValidationStatus;
import com.app.user.status.ExceptionStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author VJ
 */
public class RegisterView implements Viewable {

    private enum RegisterMode {

        NormalCCP, PersuasiveCCP
    };

    private final RegisterController regControl;
    private JPanel mainPanel;                               //The Main Panel holding the entire Registration View
    private FormView p1FormView;                           //Gives the Phase 1 Form.       
    private JPanel p2MainPanel;                            // The Panel that holds all Phase2 Components
    private JPanel sharedBtnPanel;                            // Holds Buttons
    private JPanel p2SharedBtns;
    private JPanel globalSharedBtns;
    private RegisterGridView p2Grid;                           //Gives the Panel holding the grid.
    private JButton p2Finish, p2Reset, p2Shuffle, restart, close;
    private ListView p2ListView;
    private final List<String> defaultImageList;
    private DisableUI p2LayerUI;
    private JLayer<JPanel> p2Layer;
    private RegisterMode regMode;

    public RegisterView(RegisterController regcontrol, List<String> list) {
        regControl = regcontrol;
        regControl.setRegisterView(this);
        defaultImageList = list;
        initMainPanel();
        initSharedBtns();
        initPhase1();
        initPhase2();
        initAllActions();
        addMainComponents();
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout("fill"));
    }

    private void addMainComponents() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (2.0 / 3.0 * d.getWidth());
        double height = (2.0 / 3.0 * d.getHeight());
        mainPanel.add(p1FormView.getComponent(), "width " + ((1.0 / 4.0 * width)));
        mainPanel.add(sharedBtnPanel, " newline , width " + ((1.0 / 4.0 * width) + ",height " + (2.0 / 3.0 * height)));
        mainPanel.add(p2Layer, "east,width " + ((3.0 / 4.0 * width)) + ",height " + height);
    }

    private void initSharedBtns() {
        sharedBtnPanel = new JPanel(new MigLayout());
        sharedBtnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Control Panel", TitledBorder.LEFT, TitledBorder.TOP));
        initP2SharedBtns();
        initGlobalSharedBtns();
        sharedBtnPanel.add(p2SharedBtns, "wrap,center,gaptop 20");
        sharedBtnPanel.add(globalSharedBtns, "center,gaptop 20");

    }

    private void initP2SharedBtns() {
        p2SharedBtns = new JPanel(new MigLayout());
        p2Finish = new JButton("Finish");
        p2Finish.setEnabled(false);
        p2Finish.setFocusPainted(false);
        p2Reset = new JButton("Reset Phase2");
        p2Reset.setEnabled(false);
        p2Reset.setFocusPainted(false);
        p2Shuffle = new JButton("Shuffle");
        p2Shuffle.setEnabled(false);
        p2Shuffle.setFocusPainted(false);
        p2SharedBtns.add(p2Finish, "wrap");
        p2SharedBtns.add(p2Reset, "wrap");
        p2SharedBtns.add(p2Shuffle);
        p2SharedBtns.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase 2", TitledBorder.LEFT, TitledBorder.TOP));

    }

    private void initGlobalSharedBtns() {
        globalSharedBtns = new JPanel(new MigLayout());
        restart = new JButton("Restart");
        restart.setEnabled(false);
        restart.setFocusPainted(false);
        close = new JButton("Close");
        close.setFocusPainted(false);
        globalSharedBtns.add(restart);
        globalSharedBtns.add(close);
        globalSharedBtns.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Global", TitledBorder.LEFT, TitledBorder.TOP));
    }

    private void initPhase1() {
        p1FormView = new FormView("Sign Up");
        p1FormView.addButtonAction((ActionEvent evt) -> {
            String Details[] = p1FormView.getAllFields();
            regControl.registerUser(Details[0], Details[1]);
        });
    }

    private void initPhase2() {
        initPhase2Panel();
        initP2Components();
        addP2Components();
    }

    private void initPhase2Panel() {
        p2MainPanel = new JPanel();
        p2MainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase2", TitledBorder.LEFT, TitledBorder.TOP));
        p2MainPanel.setLayout(new BorderLayout());
    }

    private void initP2Components() {
        p2ListView = new ListView();
        p2Grid = new RegisterGridView();
        p2Grid.setGridBorder(true);
    }

    private void addP2Components() {
        p2MainPanel.add(p2ListView.getLayer(), BorderLayout.EAST);
        p2MainPanel.add(p2Grid.getComponent(), BorderLayout.CENTER);
        p2LayerUI = new DisableUI();
        p2Layer = new JLayer<>(p2MainPanel, p2LayerUI);
        p2LayerUI.startDisableUI();
    }

    private void initAllActions() {
        initP2BtnActions();
        initGlobalBtnActions();
        initP2Actions();

    }

    private void initP2BtnActions() {

        //Phase 2 Reset Button
        p2Reset.addActionListener((ActionEvent evt) -> {
            regControl.resetPhase2();

        });

        //Phase 2 Finish Button
        p2Finish.addActionListener((ActionEvent evt) -> {
            regControl.completeRegisteration();
        });

    }

    private void initGlobalBtnActions() {
        restart.addActionListener((ActionEvent evt) -> {
            regControl.restart();
        });

        close.addActionListener((ActionEvent evt) -> {
            Restart();
            p1FormView.enableUI();
            regControl.goBack();
        });

    }

    private void Restart() {
        restart.setEnabled(false);
        p2ListView.removeImage(null);
        p2ListView.uninstallList();
        p2LayerUI.startDisableUI();
        p2Grid.resetImage();
        if (regMode != null && regMode == RegisterMode.PersuasiveCCP) {
            p2Grid.stopShuffle();
        }
        p2ListView.repaintList();
        disableP2Btns();
    }

    private void disableP2Btns() {
        p2Shuffle.setEnabled(false);
        p2Finish.setEnabled(false);
        p2Reset.setEnabled(false);
    }

    private void initP2Actions() {

        p2ListView.setListSelectionListener((ListSelectionEvent evt) -> {
            if (!p2ListView.isSelectionEmpty() && !p2ListView.getValueIsAdjusting()) {
                regControl.requestImage(p2ListView.getSelectionValue());
            }
        });

        p2Grid.setGridActions(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getSource() instanceof JLabel) {
                    int gridSelected = Integer.parseInt(((JLabel) me.getSource()).getName());
                    String imageSelected = p2ListView.getSelectionValue();
                    if (!p2Reset.isEnabled()) {
                        p2Reset.setEnabled(true);
                    }
                    regControl.addUserEntry(imageSelected, gridSelected);
                    p2Grid.disableUI();
                    p2ListView.removeImage(imageSelected);
                }
            }

        });

        p2Shuffle.addActionListener((ActionEvent evt) -> {
            p2Grid.startShuffle();
        });
    }

    public void loadList() {
        Collections.shuffle(defaultImageList);
        p2ListView.installList(new ArrayList<String>(defaultImageList));
    }

    public void unloadList() {
        p2ListView.uninstallList();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

        switch (pce.getPropertyName()) {
            case "DisplayImage":
                if (pce.getNewValue() != null) {
                    handleDisplayImage((BufferedImage) pce.getNewValue());
                }
                break;

            case "RegisterStatus":
                handleRegisterStatus((RegisterStatus) pce.getNewValue());
                break;

            case "ValidationStatus":
                handleValidationStatus((ValidationStatus) pce.getNewValue());
                break;

            case "ExceptionStatus":
                handleExceptionMessage((ExceptionStatus) pce.getNewValue());
                break;
        }
    }

    private void handleDisplayImage(BufferedImage bi) {
        switch (regMode) {
            case NormalCCP:
                p2Grid.setImage(bi);
                p2Grid.enableUI();
                break;

            case PersuasiveCCP:
                p2Grid.setPCCPImage(bi);
                p2Grid.startShuffle();
                p2Shuffle.setEnabled(true);

                break;
        }
    }

    // model Property Change Methods.
    private void handleRegisterStatus(RegisterStatus regs) {
        switch (regs) {
            case ALERT:
                int n = JOptionPane.showConfirmDialog(mainPanel, regs.getMessage(), "Alert", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    regControl.completeRegisteration();
                } else if (n == JOptionPane.NO_OPTION) {
                    p2Finish.setEnabled(true);
                }
                break;

            case P2_RESET:
                p2ListView.removeImage(null);
                unloadList();
                p2Grid.disableUI();
                p2Grid.resetImage();
                disableP2Btns();
                displayCCPOption();
                loadList();
                p2ListView.repaintList();
                JOptionPane.showMessageDialog(mainPanel, regs.getMessage(), "Phase2 Reset Success!", JOptionPane.DEFAULT_OPTION);
                break;

            case FULL_RESET:
                Restart();
                p1FormView.enableUI();
                JOptionPane.showMessageDialog(mainPanel, regs.getMessage(), "Reset Success!", JOptionPane.DEFAULT_OPTION);
                break;

            case REGISTER_SUCCESS:
                JOptionPane.showMessageDialog(mainPanel, regs.getMessage(), "Registeration Success!", JOptionPane.INFORMATION_MESSAGE);
                Restart();
                break;
            case REGISTER_FAILED:
                JOptionPane.showMessageDialog(mainPanel, regs.getMessage(), "Registeration Failed.", JOptionPane.ERROR_MESSAGE);
                Restart();
                break;

            case ADDED:
            default:
                break;
        }
    }

    private void handleValidationStatus(ValidationStatus vs) {
        switch (vs) {

            case PASSWORD_FMT_ERROR:
            case USERNAME_EXIST:
            case USERNAME_FMT_ERROR:
            case BOTH_SAME_ERROR:
                JOptionPane.showMessageDialog(mainPanel, vs.getValidationMsg(), "Error!", JOptionPane.ERROR_MESSAGE);
                p1FormView.resetUI();
                break;

            case NO_ACCOUNT:
                JOptionPane.showMessageDialog(mainPanel, vs.getValidationMsg(), "Account Error", JOptionPane.ERROR_MESSAGE);
                Restart();
                break;

            case BOTH_OK:
                JOptionPane.showMessageDialog(mainPanel, vs.getValidationMsg(), "Success!", JOptionPane.INFORMATION_MESSAGE);
                loadList();
                p2ListView.repaintList();
                displayCCPOption();
                p1FormView.disableUI();
                p2LayerUI.stopDisableUI();
                p2Grid.disableUI();
                restart.setEnabled(true);
                break;

            default:
                break;
        }
    }

    private void displayCCPOption() {
        Object options[] = {"Normal Mode", "Persuasive Mode"};

        int ans = JOptionPane.showOptionDialog(mainPanel, "Select Mode:", "Mode Selection", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);

        switch (ans) {
            case JOptionPane.NO_OPTION:
                p2Grid.setGridBorder(false);
                regMode = RegisterMode.PersuasiveCCP;
                break;

            case JOptionPane.YES_OPTION:
            default:
                p2Grid.setGridBorder(true);
                regMode = RegisterMode.NormalCCP;
                break;
        }

    }

    private void handleExceptionMessage(ExceptionStatus es) {
        switch (es) {
            case FATAL_ERROR:
                JOptionPane.showMessageDialog(mainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                break;

            case USER_EXIST:
                JOptionPane.showMessageDialog(mainPanel, es.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Restart();
                p1FormView.enableUI();
                break;
        }
    }

}
