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

    private JFrame mainFrame;
    private MainMenuView mainMenu;
    private RegisterController regControl;
    private LoginController logControl;
    private LoginView logView;
    private RegisterView legView;
    private double appWidth, appHeight;

    public HyperView(LoginController logcntrl, RegisterController regcntrl, List<String> ImgList) {
        logControl = logcntrl;
        regControl = regcntrl;
        logControl.setMainView(this);
        regControl.setMainView(this);
        initFrame();
        initMainMenu(ImgList);
        mainFrame.add(mainMenu.getPanel());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void initFrame() {
        mainFrame = new JFrame("CCP");
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        appWidth = ScreenSize.getWidth();
        appHeight = ScreenSize.getHeight();
        mainFrame.setSize((int) (1.0 / 8.0 * appWidth), (int) (1.0 / 4.0 * appHeight));
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.dispatchEvent(new WindowEvent(mainFrame, Event.WINDOW_DESTROY));
        mainFrame.setResizable(false);
    }

    private void initMainMenu(List<String> ImgList) {
        mainMenu = new MainMenuView();
        initRegisterView(ImgList);
        initLoginView();
    }

    private void initRegisterView(List<String> ImageList) {
        mainMenu.addRegisterAction((ActionEvent evt) -> {

            legView = new RegisterView(regControl, ImageList);        //First Time following code will be next time
            mainFrame.getContentPane().removeAll();
            mainFrame.add(legView.getPanel());
            mainFrame.revalidate();
            mainFrame.repaint();
            mainFrame.setSize((int) (2.0 / 3.0 * appWidth), (int) (2.0 / 3.0 * appHeight));
            mainFrame.setLocationRelativeTo(null);
            mainMenu.addLoginAction((ActionEvent ae) -> {  // Next Time Register Btn is pressed following code will be executed.
                mainFrame.getContentPane().removeAll();
                mainFrame.add(legView.getPanel());
                mainFrame.revalidate();
                mainFrame.repaint();
                mainFrame.setSize((int) (2.0 / 3.0 * appWidth), (int) (2.0 / 3.0 * appHeight));
                mainFrame.setLocationRelativeTo(null);
            });
        });

    }

    private void initLoginView() {
        mainMenu.addLoginAction((ActionEvent evt) -> {

            logView = new LoginView(logControl);
            mainFrame.getContentPane().removeAll();
            mainFrame.add(logView.getPanel());
            mainFrame.revalidate();
            mainFrame.repaint();
            mainFrame.setSize((int) (2.0 / 5.0 * appWidth), (int) (4.0 / 5.0 * appHeight));
            mainFrame.setLocationRelativeTo(null);
            mainMenu.addLoginAction((ActionEvent ae) -> {  // Next Time Register Btn is pressed following code will be executed.
                mainFrame.getContentPane().removeAll();
                mainFrame.add(logView.getPanel());
                mainFrame.revalidate();
                mainFrame.repaint();
                mainFrame.setSize((int) (2.0 / 3.0 * appWidth), (int) (2.0 / 3.0 * appHeight));
                mainFrame.setLocationRelativeTo(null);
            });
        });
    }

    private void showMainPanel() {
        mainFrame.getContentPane().removeAll();
        mainFrame.add(mainMenu.getPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setSize((int) (1.0 / 8.0 * appWidth), (int) (1.0 / 4.0 * appHeight));
        mainFrame.setLocationRelativeTo(null);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent pce) {

        if (pce.getPropertyName().equals("GoToMainMenu")) {
            showMainPanel();
        }
    }

}
