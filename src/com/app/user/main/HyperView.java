/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import com.app.user.register.RegisterController;
import com.app.user.register.view.RegisterView;
import java.awt.CardLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 *
 * @author VJ
 */
public class HyperView {

    private JFrame MainFrame;
    private JPanel CardPanel;
    private CardLayout Cards;
    private MainMenuView MainMenu;
    private RegisterController RegControl;
    private RegisterView RegView;
    private double appwidth, appheight;

    public HyperView(RegisterController regcntrl) {
        RegControl = regcntrl;
        initFrame();
        initCardPanel();
        initMainMenu();
        addComponents();
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

    private void initCardPanel() {
        CardPanel = new JPanel();
        Cards = new CardLayout();
        CardPanel.setLayout(Cards);

    }

    private void initMainMenu() {
        MainMenu = new MainMenuView();
        initRegisterView();
    }

    private void initRegisterView() {
        MainMenu.addRegisterAction((ActionEvent evt) -> {
            RegView = new RegisterView(RegControl);        //Init RegisterView
            CardPanel.add(RegView.getPanel(), "Register");
            Cards.show(CardPanel, "Register");
            MainFrame.setSize((int) (1.0 / 2.0 * appwidth), (int) (2.0 / 3.0 * appheight));
            MainFrame.setLocationRelativeTo(null);
            MainMenu.addLoginAction((ActionEvent ae) -> {  // Next Time Register Btn is pressed following code will be executed.
                Cards.show(CardPanel, "Register");
                MainFrame.setSize((int) (1.0 / 2.0 * appwidth), (int) (2.0 / 3.0 * appheight));
                MainFrame.setLocationRelativeTo(null);
            });
        });

    }

    private void showMainPanel() {
        Cards.show(CardPanel, "MainMenu");
        MainFrame.setSize((int) (1.0 / 2.0 * appwidth), (int) (2.0 / 3.0 * appheight));
        MainFrame.setLocationRelativeTo(null);
    }

    private void addComponents() {
        CardPanel.add(MainMenu.getPanel(), "MainMenu");
        MainFrame.add(CardPanel);
        Cards.show(CardPanel, "MainMenu");
    }

}
