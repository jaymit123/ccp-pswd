/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import com.app.beans.Viewable;
import com.app.user.register.RegisterController;
import com.app.user.register.RegisterStatus;
import com.app.user.register.view.RegisterView;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.util.List;

/**
 *
 * @author VJ
 */
public class HyperView implements Viewable {

    private JFrame MainFrame;
    private MainMenuView MainMenu;
    private RegisterController RegControl;
    private RegisterView RegView;
    private double appwidth, appheight;

    public HyperView(RegisterController regcntrl, List<String> ImgList) {
        RegControl = regcntrl;
        RegControl.setMainView(this);
        initFrame();
        initMainMenu(ImgList);
        MainFrame.add(MainMenu.getPanel());
        MainFrame.setLocationRelativeTo(null);
        MainFrame.setVisible(true);
    }

    private void initFrame() {
        MainFrame = new JFrame("CCP");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        appwidth = ScreenSize.getWidth();
        appheight = ScreenSize.getHeight();
        MainFrame.setSize((int) (1.0 / 8.0 * appwidth), (int) (1.0 / 6.0 * appheight));
        MainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MainFrame.setResizable(false);
    }

    private void initMainMenu(List<String> ImgList) {
        MainMenu = new MainMenuView();
        initRegisterView(ImgList);
    }

    private void initRegisterView(List<String> ImageList) {
        MainMenu.addRegisterAction((ActionEvent evt) -> {

            RegView = new RegisterView(RegControl, ImageList);        //First Time following code will be next time
            MainFrame.getContentPane().removeAll();
            MainFrame.add(RegView.getPanel());
            MainFrame.revalidate();
            MainFrame.repaint();
            MainFrame.setSize((int) (2.0 / 3.0 * appwidth), (int) (2.0 / 3.0 * appheight));
            MainFrame.setLocationRelativeTo(null);
            MainMenu.addLoginAction((ActionEvent ae) -> {  // Next Time Register Btn is pressed following code will be executed.
                MainFrame.getContentPane().removeAll();
                MainFrame.add(RegView.getPanel());
                MainFrame.revalidate();
                MainFrame.repaint();
                MainFrame.setSize((int) (2.0 / 3.0 * appwidth), (int) (2.0 / 3.0 * appheight));
                MainFrame.setLocationRelativeTo(null);
            });
        });

    }

    private void showMainPanel() {
        MainFrame.getContentPane().removeAll();
        MainFrame.add(MainMenu.getPanel());
        MainFrame.revalidate();
        MainFrame.repaint();
        MainFrame.setSize((int) (1.0 / 8.0 * appwidth), (int) (1.0 / 6.0 * appheight));
        MainFrame.setLocationRelativeTo(null);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {
       
        if (pce.getNewValue().equals(RegisterStatus.CLOSE)) {
            showMainPanel();
        }
    }

}
