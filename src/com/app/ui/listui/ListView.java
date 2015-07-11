/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.listui;

import com.app.ui.DisableUI;
import java.util.List;
import javax.swing.JLayer;
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

    private DisableUI layerUi;
    private JLayer<JPanel> listLayer;
    private JPanel mainPanel;
    private JList<String> imageList;
    private JScrollPane listScroll;
    private ImageListModel<String> imageModel;
    private JLabelListRenderer imageLabels;

    public ListView() {
        initMainPanel();
        initImageList();
        initLayer();
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
    }

    private void initImageList() {
        imageModel = new ImageListModel<>();
        imageList = new JList<>(imageModel);
        imageLabels = new JLabelListRenderer();
        imageList.setCellRenderer(imageLabels);
        imageList.setDoubleBuffered(true);
        imageList.setFixedCellHeight(75);
        imageList.setFixedCellWidth(75);        
        imageList.setVisibleRowCount(6);
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listScroll = new JScrollPane(imageList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void initLayer() {
        mainPanel.add(listScroll);
        layerUi = new DisableUI();
        listLayer = new JLayer<>(mainPanel, layerUi);
    }

    public void setListSelectionListener(ListSelectionListener lsl) {
        imageList.addListSelectionListener(lsl);
    }

    public void installList(List<String> list) {
        imageModel.addAll(list);
    }

    public void repaintList() {
        imageList.revalidate();
        imageList.repaint();
    }

    public boolean isSelectionEmpty() {
        return imageList.isSelectionEmpty();
    }

    public boolean getValueIsAdjusting() { 
        return imageList.getValueIsAdjusting();
    }

    public void uninstallList() {
        imageModel.removeAll();
    }

    public void disableUI() {
        layerUi.startDisableUI();
    }

    public void enableUI() {
        layerUi.stopDisableUI();

    }

    public JLayer<JPanel> getLayer() {
        return listLayer;
    }

    public boolean removeImage(String img) {
        imageList.clearSelection();
        boolean result = imageModel.remove(img);
        imageLabels.revalidate();
        imageLabels.repaint();
        repaintList();
        return result;
    }

    public String getSelectionValue() {
        return imageList.getSelectedValue();
    }

}
