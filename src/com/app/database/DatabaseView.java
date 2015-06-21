/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.database;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 *
 * @author VJ
 */
public class DatabaseView {
    private JDialog SettingsDialog = null;
    private JOptionPane SettingsPane = null;
    private JPanel SettingsPanel = null;
    private JRadioButton MySQL,H2;
    private ButtonGroup group;
    private RadioButtonAction radioaction;

public DatabaseView(){
    
}

private void initComponents(){
  SettingsPanel = new JPanel();
  SettingsPanel.setLayout(new BorderLayout());
  
}


private void initRadioButtons(){
    MySQL = new JRadioButton("MySQL");
    H2 = new JRadioButton("H2");
    H2.setSelected(true);
    MySQL.setActionCommand("MySQL");
    H2.setActionCommand("H2");
    group = new ButtonGroup();
    group.add(H2);
    group.add(MySQL);
    
    
    
   
}

private void initActions(){
radioaction = new RadioButtonAction();
H2.addActionListener(radioaction);
MySQL.addActionListener(radioaction);
}



class RadioButtonAction implements ActionListener{
    public void actionPerformed(ActionEvent evt){
       switch(evt.getActionCommand()){
           case "H2" :
                      break;
               
           case "MySQL" : 
                            break;
               
           default:
       } 
    }

    }
}

