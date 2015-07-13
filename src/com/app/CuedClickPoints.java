package com.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.app.user.register.RegisterController;
import com.app.user.database.DatabaseModel;
import com.app.user.database.DatabaseType;
import com.app.user.dao.UserDAO;
import com.app.user.database.DatabaseException;
import com.app.io.ImageAccessException;
import com.app.user.security.AppSecurityException;
import com.app.io.ImageModel;
import com.app.user.login.LoginController;
import com.app.user.login.LoginModel;
import com.app.user.main.HyperView;
import com.app.user.main.SwingUncaughtException;
import com.app.user.register.RegisterModel;
import com.app.user.security.AuthenticationModel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author VJ
 */
public class CuedClickPoints {

    public static void main(String[] args) throws AppSecurityException, DatabaseException, ImageAccessException {
   //     SwingUncaughtException.registerExceptionHandler();
        try {
            //DatabaseModel dbmodel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
             DatabaseModel dbmodel = new DatabaseModel(DatabaseType.H2, "", "root", "", "CCP_User_Table");
            UserDAO udao = new UserDAO(dbmodel);
            ImageModel im = new ImageModel(System.getProperty("user.home") + "/Desktop/resources/");
            AuthenticationModel aum = new AuthenticationModel(udao, im.getImageList());
            RegisterController rg = new RegisterController();
            LoginController lg = new LoginController();
            RegisterModel rm = new RegisterModel(aum, im);
            LoginModel lm = new LoginModel(aum, im);
            rg.setRegisterModel(rm);
            lg.setLoginModel(lm);
            Thread.sleep(1000);
            //Runs program in EDT
            SwingUtilities.invokeLater(() -> {

               // SwingUncaughtException.registerExceptionHandler();
                HyperView hp = new HyperView(lg, rg, im.getImageList());
            });

        } catch (DatabaseException de) {
            JOptionPane.showMessageDialog(null, "Sorry, An Error Occured while contacting the database.\ncontact me at jaymit_123@hotmail.com", "Error.", JOptionPane.ERROR_MESSAGE);
        } catch (AppSecurityException | ImageAccessException | InterruptedException se) {
            JOptionPane.showMessageDialog(null, "Sorry, An Error Occured.\ncontact me at jaymit_123@hotmail.com", "Error.", JOptionPane.ERROR_MESSAGE);
        }
    }
}
