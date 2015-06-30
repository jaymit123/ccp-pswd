/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.ui.common.formui;
import java.awt.BorderLayout;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
/**
 *
 * @author VJ
 */
public class FormView {
    private JPanel MainPanel = null;
    private JTextField UsernameField = null;
    private JPasswordField PasswordField = null;
    
    public FormView(){
        
        
    }
    
    private void iniPanel(){
       MainPanel = new JPanel();
       MainPanel.setLayout(new BorderLayout());
    }
    
    private void initFields(){
        UsernameField = new JTextField(8);
        UsernameField.setInputVerifier(new InputVerifier() {

            @Override
            public boolean verify(JComponent jc) {
               ((JTextField)jc).getText();
                return false;
            }

       
    });
                PasswordField = new JPasswordField(8);
                
    
}
}
