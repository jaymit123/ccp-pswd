/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.listui;

import com.app.ui.DisableUI;
import java.util.List;
import javax.swing.JLayer;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;

/**
 *
 * @author VJ
 */
public class ListView {

    private DisableUI layerui;
    private JLayer<JPanel> ListLayer;
    private JPanel MainPanel;
    private JList<String> ImageList;
    private JScrollPane ListScroll;
    private ImageListModel<String> ImageModel;
    private JLabelListRenderer ImageLabels;

    public ListView() {
        initMainPanel();
        initImageList();
        initLayer();
    }

    private void initMainPanel() {
        MainPanel = new JPanel(new MigLayout());
    }

    private void initImageList() {
        ImageModel = new ImageListModel<>();
        ImageList = new JList<>(ImageModel);
        ImageLabels = new JLabelListRenderer();

        ImageList.setDoubleBuffered(true);
        ImageList.setFixedCellHeight(75);
        ImageList.setFixedCellWidth(75);
        ImageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ImageList.setCellRenderer(ImageLabels);

        ListScroll = new JScrollPane(ImageList);
        ListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

    }

    private void initLayer() {
        MainPanel.add(ListScroll);
        layerui = new DisableUI();
        ListLayer = new JLayer<>(MainPanel, layerui);
    }

    public void setListSelectionListener(ListSelectionListener lsl) {
        ImageList.addListSelectionListener(lsl);
    }

    public void installList(List<String> list) {
        ImageModel.addAll(list);
    }
    
    public boolean isSelectionEmpty(){
        return ImageList.isSelectionEmpty();
    }

    public void uninstallList() {
        ImageModel.removeAll();
    }

    public void disableUI() {
        layerui.startDisableUI();
    }

    public void enableUI() {
        layerui.stopDisableUI();

    }

    public JLayer<JPanel> getLayer() {
        return ListLayer;
    }

    public boolean removeImage(String img) {
        return ImageModel.remove(img);
    }

    public String getSelectionValue() {
        return ImageList.getSelectedValue();
    }

}
