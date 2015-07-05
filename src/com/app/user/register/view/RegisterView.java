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
import com.app.ui.listui.ImageListModel;
import com.app.ui.listui.ListView;
import com.app.user.register.RegisterController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayer;
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
    private JButton P2Next, P2Finish,P2Reset,FullReset,Close;
    private ListView P2ListView;
    private List<String> DefaultImageList;
    private DisableUI P2layerui;
    private JLayer<JPanel> Phase2Layer;
    private int GridSelected = -1;               // Determin which grid is selected.
    private String ImageSelected = null;

    public RegisterView(RegisterController regcontrol, List<String> list) {
        RegControl = regcontrol;
        RegControl.addView(this);
        DefaultImageList = list;
        initMainPanel();
        initSharedBtns();
        initPhase1();
        initPhase2();
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
        MainPanel.add(P2MainPanel, "east,width " + ((3.0 / 4.0 * width)) + ",height " + height);
    }

    private void initSharedBtns() {
        SharedBtnPanel = new JPanel(new MigLayout());
        SharedBtnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Control Panel", TitledBorder.LEFT, TitledBorder.TOP));
        P2SharedBtns = new JPanel(new MigLayout());
        P2Next = new JButton("Next");
        P2Next.setEnabled(false);
        P2Finish = new JButton("Finish");
        P2Finish.setEnabled(false);
        P2Reset = new JButton("Reset Phase2");
        P2SharedBtns.add(P2Next,"wrap");
        P2SharedBtns.add(P2Finish,"wrap");
        P2SharedBtns.add(P2Reset,"");
        P2SharedBtns.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase 2", TitledBorder.LEFT, TitledBorder.TOP));
        SharedBtnPanel.add(P2SharedBtns);

    }

    private void initPhase1() {
        P1FormView = new FormView("Register");
        P1FormView.addButtonAction((ActionEvent evt) -> {
            String Details[] = P1FormView.getAllFields();
            RegControl.registerUser(Details[0], Details[1]);
        });
    }

    private void initPhase2() {
        initPhase2Panel();
        initP2Components();
        initP2Actions();
        addP2Components();
    }

    private void initPhase2Panel() {
        P2MainPanel = new JPanel();
        P2MainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Phase2", TitledBorder.LEFT, TitledBorder.TOP));
        P2MainPanel.setLayout(new BorderLayout());
    }

    private void initP2Components() {
        P2ListView = new ListView();
        P2ListView.installList(DefaultImageList);
        P2Grid = new GridView();
        P2Grid.setGridBorder(true);
    }

    private void initP2Actions() {

        P2ListView.setListSelectionListener((ListSelectionEvent evt) -> {
            if (!P2ListView.isSelectionEmpty()) {
                ImageSelected = P2ListView.getSelectionValue();
                RegControl.requestImage(ImageSelected);
                P2Next.setEnabled(false);
            }
        });

        P2Next.addActionListener((ActionEvent evt) -> {
            P2Next.setEnabled(false);
            RegControl.addUserEntry(ImageSelected, GridSelected);
            P2ListView.removeImage(ImageSelected);
            ImageSelected = null;
            GridSelected = -1;
        });

        P2Finish.addActionListener((ActionEvent evt) -> {
            RegControl.completeRegisteration();
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

    private void addP2Components() {
        P2MainPanel.add(P2ListView.getLayer(), BorderLayout.EAST);
        P2MainPanel.add(P2Grid.getComponent(), BorderLayout.CENTER);
    }

    public void loadList() {
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

    }

}
