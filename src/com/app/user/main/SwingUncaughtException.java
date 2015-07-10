/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.user.main;

import javax.swing.JOptionPane;

/**
 *
 * @author VJ
 */
// handles checked exceptions that might occur at runtime
public class SwingUncaughtException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
      handleException(e);
    }
    
    
    public void handleException(Throwable e){
        System.out.println("Uncaught Exception:"+e.getMessage());
        JOptionPane.showMessageDialog(null, "Oh No. Something went wrong!\n Please contact me @ jaymit_123@hotmail.com", "Error", JOptionPane.ERROR_MESSAGE);
   
        System.exit(0);
    }
    
    public static void registerExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(new SwingUncaughtException());
    System.setProperty("sun.awt.exception.handler", SwingUncaughtException.class.getName());
  }
}
