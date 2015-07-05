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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayer;
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
    private GridView P2Grid;                           //Gives the Panel holding the grid.
    private JPanel P2GridPanel;                            // Holds Additional Buttons + Above Mentioned Grid from GridView
    private JButton j1, j2, j3;
    private JButton P2Next, P2Finish;
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
        initPhase1();
        initPhase2();
        addMainComponents();

    }

    private void initMainPanel() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new MigLayout("fill"));
        SharedBtnPanel = new JPanel(new MigLayout());
        //   SharedBtnPanel.setBackground(Color.GREEN);
    }

    private void addMainComponents() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (1.0 / 2.0 * d.getWidth());
        double height = (2.0 / 3.0 * d.getHeight());
        MainPanel.add(P1FormView.getComponent(), "width " + ((2.0 / 5.0 * width)));
        MainPanel.add(SharedBtnPanel, " newline , width " + ((2.0 / 5.0 * width) + ",height " + (2.0 / 3.0 * height)));
        MainPanel.add(P2MainPanel, "east,width " + ((3.0 / 5.0 * width)) + ",height " + height);
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
        P2MainPanel.setLayout(new MigLayout("fill"));
    P2MainPanel.setBackground(Color.red);
    }

    private void initP2Components() {
        P2GridPanel = new JPanel(new MigLayout("fill"));
        P2ListView = new ListView();
        P2Grid = new GridView();
        P2Grid.setGridBorder(true);
        P2Next = new JButton("Next");
        P2Next.setEnabled(false);
        P2Finish = new JButton("Finish");
        P2Finish.setEnabled(false);
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
        P2GridPanel.add(P2Grid.getComponent(), "center , grow");
       // P2GridPanel.add(P2Next, "east , wrap");
       // P2GridPanel.add(P2Finish, "east");
        P2MainPanel.add(P2ListView.getLayer(),"grow 200");
        P2MainPanel.add(P2GridPanel, "newline");

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
