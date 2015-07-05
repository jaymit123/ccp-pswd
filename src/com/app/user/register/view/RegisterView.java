/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.register.view;

import com.app.ui.GridView;
import com.app.ui.FormView;
import com.app.beans.Viewable;
import com.app.ui.listui.ImageListModel;
import com.app.ui.listui.ListView;
import com.app.user.register.RegisterController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author VJ
 */
public class RegisterView implements Viewable {

    private final RegisterController RegControl;
    private JPanel MainPanel;                               //The Main Panel holding the entire Registration View
    private JPanel RightSidePanel;
    private FormView Phase1View;                           //Gives the Phase 1 Form.       
    private JPanel Phase2Panel;                            // The Panel that holds all Phase2 Components
    private JPanel P2ListPanel;                            // Holds the JList in Phase2 & Some Buttons
    private GridView Phase2Grid;                           //Gives the Panel holding the grid.
    private JPanel P2GridPanel;                            // Holds Additional Buttons + Above Mentioned Grid from GridView
    private JButton j1, j2, j3;
    private ListView ListV;
    private List<String> DefaultImageList;

    public RegisterView(RegisterController regcontrol, List<String> list) {
        RegControl = regcontrol;
        RegControl.addView(this);
        DefaultImageList = list;
        initMainPanel();
        j1 = new JButton("j1");
        j2 = new JButton("j2");
        j3 = new JButton("j3");
        MainPanel.add(RightSidePanel, "west,grow");
        MainPanel.add(j3, "center ,grow");

    }

    private void initMainPanel() {
        MainPanel = new JPanel();
        MainPanel.setBackground(Color.red);
        MainPanel.setLayout(new MigLayout("fill"));

    }

    private void initRigtSidePanel() {
        RightSidePanel = new JPanel();
        RightSidePanel.setLayout(new MigLayout("fill"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = (1.0 / 2.0 * d.getWidth());
        double height = (2.0 / 3.0 * d.getHeight());
        RightSidePanel.add(j1, "width " + ((int) (1.0 / 3.0 * width)) + " , grow , wrap");
        RightSidePanel.add(j2, " , height " + ((int) (1.0 / 5.0 * height)) + " , grow");
    }

    private void initListView() {
        ListV = new ListView();
        ListV.setListSelectionListener((ListSelectionEvent evt) -> {
            if (!ListV.isSelectionEmpty()) {
                RegControl.requestImage(ListV.getSelectionValue());
            }
        });
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
