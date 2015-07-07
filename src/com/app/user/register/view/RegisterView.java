/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register.view;

import com.app.ui.GridView;
import com.app.ui.FormView;
import com.app.beans.Viewable;
import com.app.ui.DisableUI;
import com.app.ui.listui.ListView;
import com.app.user.register.RegisterController;
import com.app.user.register.RegisterStatus;
import com.app.user.status.ProcessStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author VJ
 */
public class RegisterView implements Viewable {

    private final RegisterController RegControl;
    private JPanel MainPanel;                               //The Main Panel holding the entire Registration View
    private FormView P1FormView;                           //Gives the Phase 1 Form.       
    private JPanel P2MainPanel;                            // The Panel that holds all Phase2 Components
    private JPanel SharedBtnPanel;                            // Holds Buttons
    private JPanel P2SharedBtns;
    private JPanel GlobalSharedBtns;
    private GridView P2Grid;                           //Gives the Panel holding the grid.
    private JButton P2Next, P2Finish, P2Reset, Restart, Close;
    private ListView P2ListView;
    private final List<String> DefaultImageList;
    private DisableUI P2layerui;
    private JLayer<JPanel> P2Layer;
    private int GridSelected = -1;               // Determin which grid is selected.
    private String ImageSelected = null;

    public RegisterView(RegisterController regcontrol, List<String> list) {
        RegControl = regcontrol;
        RegControl.setRegisterView(this);
        DefaultImageList = list;
        initMainPanel();
        initSharedBtns();
        initPhase1();
        initPhase2();
        initAllActions();
        addMainComponents();
    }

    private void initMainPanel() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new MigLayout("fill"));
    }

    private void addMainComponents() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (2.0 / 3.0 * d.getWidth());
        double height = (2.0 / 3.0 * d.getHeight());
        MainPanel.add(P1FormView.getComponent(), "width " + ((1.0 / 4.0 * width)));
        MainPanel.add(SharedBtnPanel, " newline , width " + ((1.0 / 4.0 * width) + ",height " + (2.0 / 3.0 * height)));
        MainPanel.add(P2Layer, "east,width " + ((3.0 / 4.0 * width)) + ",height " + height);
    }

    private void initSharedBtns() {
        SharedBtnPanel = new JPanel(new MigLayout());
        SharedBtnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Control Panel", TitledBorder.LEFT, TitledBorder.TOP));
        initP2SharedBtns();
        initGlobalSharedBtns();
        SharedBtnPanel.add(P2SharedBtns, "wrap,center,gaptop 20");
        SharedBtnPanel.add(GlobalSharedBtns, "center,gaptop 20");

    }

    private void initP2SharedBtns() {
        P2SharedBtns = new JPanel(new MigLayout());
        P2Next = new JButton("Next");
        P2Next.setEnabled(false);
        P2Finish = new JButton("Finish");
        P2Finish.setEnabled(false);
        P2Reset = new JButton("Reset Phase2");
        P2Reset.setEnabled(false);
        P2SharedBtns.add(P2Next, "wrap");
        P2SharedBtns.add(P2Finish, "wrap");
        P2SharedBtns.add(P2Reset);
        P2SharedBtns.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase 2", TitledBorder.LEFT, TitledBorder.TOP));

    }

    private void initGlobalSharedBtns() {
        GlobalSharedBtns = new JPanel(new MigLayout());
        Restart = new JButton("Restart");
        Restart.setEnabled(false);
        Close = new JButton("Close");
        GlobalSharedBtns.add(Restart);
        GlobalSharedBtns.add(Close);
        GlobalSharedBtns.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Global", TitledBorder.LEFT, TitledBorder.TOP));
    }

    private void initPhase1() {
        P1FormView = new FormView("Sign Up");
        P1FormView.addButtonAction((ActionEvent evt) -> {
            String Details[] = P1FormView.getAllFields();
            RegControl.registerUser(Details[0], Details[1]);
        });
    }

    private void initPhase2() {
        initPhase2Panel();
        initP2Components();
        addP2Components();
    }

    private void initPhase2Panel() {
        P2MainPanel = new JPanel();
        P2MainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase2", TitledBorder.LEFT, TitledBorder.TOP));
        P2MainPanel.setLayout(new BorderLayout());
    }

    private void initP2Components() {
        P2ListView = new ListView();
        P2Grid = new GridView();
        P2Grid.setGridBorder(true);
    }

    private void addP2Components() {
        P2MainPanel.add(P2ListView.getLayer(), BorderLayout.EAST);
        P2MainPanel.add(P2Grid.getComponent(), BorderLayout.CENTER);
        P2layerui = new DisableUI();
        P2Layer = new JLayer<>(P2MainPanel, P2layerui);
        P2layerui.startDisableUI();
    }

    private void initAllActions() {
        initP2SharedBtnActions();
        initGlobalBtnActions();
        initP2Actions();

    }

    private void initP2SharedBtnActions() {
        P2Next.addActionListener((ActionEvent evt) -> {
            P2Next.setEnabled(false);
            if (!P2Reset.isEnabled()) {
                P2Reset.setEnabled(true);
            }
            P2Grid.disableUI();
            P2ListView.removeImage(ImageSelected);
            RegControl.addUserEntry(ImageSelected, GridSelected);
            ImageSelected = null;
            GridSelected = -1;
        });

        P2Reset.addActionListener((ActionEvent evt) -> {
            P2ListView.removeImage(null);
            P2ListView.uninstallList();
            P2Grid.disableUI();
            P2Grid.setImage(null);
            disableP2Btns();
            P2ListView.installList(DefaultImageList);
            P2ListView.repaintList();
            RegControl.resetPhase2();
        });

        P2Finish.addActionListener((ActionEvent evt) -> {
            RegControl.completeRegisteration();
        });

    }

    private void initGlobalBtnActions() {
        Restart.addActionListener((ActionEvent evt) -> {
            Restart();
            RegControl.restart();
        });

        Close.addActionListener((ActionEvent evt) -> {
            Restart();
            RegControl.close();
        });

    }

    private void Restart() {
        Restart.setEnabled(false);
        P2ListView.removeImage(null);
        P2ListView.uninstallList();
        P2layerui.startDisableUI();
        P2Grid.setImage(null);
        P2ListView.repaintList();
        disableP2Btns();
        P1FormView.enableUI();
    }

    private void disableP2Btns() {
        P2Next.setEnabled(false);
        P2Finish.setEnabled(false);
        P2Reset.setEnabled(false);
    }

    private void initP2Actions() {

        P2ListView.setListSelectionListener((ListSelectionEvent evt) -> {
            if (!P2ListView.isSelectionEmpty() && !P2ListView.getValueIsAdjusting()) {
                ImageSelected = P2ListView.getSelectionValue();
                RegControl.requestImage(ImageSelected);
                P2Grid.enableUI();
                P2Next.setEnabled(false);
            }
        });

        P2Grid.setGridActions(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (me.getSource() instanceof JLabel) {
                    P2Next.setEnabled(true);
                    GridSelected = Integer.parseInt(((JLabel) me.getSource()).getName());
                }
            }

        });
    }

    public void loadList() {
        Collections.shuffle(DefaultImageList);
        P2ListView.installList(new ArrayList<String>(DefaultImageList));
    }

    public void unloadList() {
        P2ListView.uninstallList();
    }

    public JPanel getPanel() {
        return MainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

        switch (pce.getPropertyName()) {
            case "DisplayImage":
                P2Grid.setImage((Image) pce.getNewValue());
                break;

            case "RegisterStatus":
                handleRegisterStatus((RegisterStatus) pce.getNewValue());
                break;
        }
    }

    // model Property Change Methods.
    private void handleRegisterStatus(RegisterStatus regs) {
        switch (regs) {
            case INIT:
                P1FormView.disableUI();
                P2layerui.stopDisableUI();
                P2Grid.disableUI();
                Restart.setEnabled(true);
                Timer refreshJList = new Timer(800, (ActionEvent evt) -> {
                    loadList();
                    P2ListView.repaintList();
                });
                refreshJList.setRepeats(false);
                refreshJList.start();
                break;
        }
    }

}
