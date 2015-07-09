/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import com.app.beans.Viewable;
import com.app.user.login.LoginController;
import com.app.user.login.LoginView;
import com.app.user.register.RegisterController;
import com.app.user.register.RegisterView;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.WindowEvent;
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
    private LoginController LogControl;
    private LoginView LogView;
    private RegisterView RegView;
    private double appwidth, appheight;

    public HyperView(LoginController logcntrl, RegisterController regcntrl, List<String> ImgList) {
        LogControl = logcntrl;
        RegControl = regcntrl;
        LogControl.setMainView(this);
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
        MainFrame.dispatchEvent(new WindowEvent(MainFrame, Event.WINDOW_DESTROY));
        MainFrame.setResizable(false);
    }

    private void initMainMenu(List<String> ImgList) {
        MainMenu = new MainMenuView();
        initRegisterView(ImgList);
        initLoginView();
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

    private void initLoginView() {
        MainMenu.addLoginAction((ActionEvent evt) -> {

            LogView = new LoginView(LogControl);
            MainFrame.getContentPane().removeAll();
            MainFrame.add(LogView.getPanel());
            MainFrame.revalidate();
            MainFrame.repaint();
            MainFrame.setSize((int) (2.0 / 5.0 * appwidth), (int) (4.0 / 5.0 * appheight));
            MainFrame.setLocationRelativeTo(null);
            MainMenu.addLoginAction((ActionEvent ae) -> {  // Next Time Register Btn is pressed following code will be executed.
                MainFrame.getContentPane().removeAll();
                MainFrame.add(LogView.getPanel());
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

        if (pce.getPropertyName().equals("GoToMainMenu")) {
            showMainPanel();
        }
    }

}
