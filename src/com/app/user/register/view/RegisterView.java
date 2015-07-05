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
    private FormView Phase1View;                           //Gives the Phase 1 Form.       
    private JPanel Phase2Panel;                            // The Panel that holds all Phase2 Components
    private JPanel P2ListPanel;                            // Holds the JList in Phase2 & Some Buttons
    private GridView Phase2Grid;                           //Gives the Panel holding the grid.
    private JPanel P2GridPanel;                            // Holds Additional Buttons + Above Mentioned Grid from GridView
    private JButton j1, j2, j3;
    private JButton P2Next, P2Finish;
    private ListView ListV;
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
        MainPanel.add(Phase2Panel, "west,grow");
        MainPanel.add(j3, "center ,grow");

    }

    private void initMainPanel() {
        MainPanel = new JPanel();
        MainPanel.setBackground(Color.red);
        MainPanel.setLayout(new MigLayout("fill"));
    }
    
    private  void initPhase1(){
        Phase1View = new FormView("Register");
        Phase1View.addButtonAction((ActionEvent evt) -> {
            String Details[] = Phase1View.getAllFields();
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
        Phase2Panel = new JPanel();
        Phase2Panel.setLayout(new MigLayout("fill"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (1.0 / 2.0 * d.getWidth());
        double height = (2.0 / 3.0 * d.getHeight());
        Phase2Panel.add(j1, "width " + ((int) (1.0 / 3.0 * width)) + " , grow , wrap");
        Phase2Panel.add(j2, " , height " + ((int) (1.0 / 5.0 * height)) + " , grow");
    }

    private void initP2Components() {
        P2GridPanel = new JPanel(new MigLayout());
        ListV = new ListView();
        Phase2Grid = new GridView();
        Phase2Grid.setGridBorder(true);
        P2Next = new JButton("Next");
        P2Next.setEnabled(false);
        P2Finish.setEnabled(false);
        P2Finish = new JButton("Finish");
        P2Finish.setEnabled(false);
    }

    private void initP2Actions() {

        ListV.setListSelectionListener((ListSelectionEvent evt) -> {
            if (!ListV.isSelectionEmpty()) {
                ImageSelected = ListV.getSelectionValue();
                RegControl.requestImage(ImageSelected);
                P2Next.setEnabled(false);
            }
        });

        P2Next.addActionListener((ActionEvent evt) -> {
            P2Next.setEnabled(false);
            RegControl.addUserEntry(ImageSelected, GridSelected);
            ListV.removeImage(ImageSelected);
            ImageSelected = null;
            GridSelected = -1;
        });

        P2Finish.addActionListener((ActionEvent evt) -> {
            RegControl.completeRegisteration();
        });

        Phase2Grid.setGridActions(new MouseAdapter() {
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
        P2GridPanel.add(Phase2Grid.getComponent(), "center , grow");
        P2GridPanel.add(P2Next, "east , wrap");
        P2GridPanel.add(P2Finish, "east");
        Phase2Panel.add(P2GridPanel, "center , grow");
        Phase2Panel.add(ListV.getLayer(), "north");
    }

    public void loadList() {
        ListV.installList(new ArrayList<String>(DefaultImageList));
    }

    public void unloadList() {
        ListV.uninstallList();
    }

    public JPanel getPanel() {
        return MainPanel;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

    }

}
