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
import com.app.user.security.AuthenticationException;
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

    public static void main(String[] args) {
        SwingUncaughtException.registerExceptionHandler();
        try {

            // 1. Load Database
           // DatabaseModel dbModel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
            DatabaseModel dbModel = new DatabaseModel(DatabaseType.H2, "", "root", "", "CCP_User_Table");
             ///DatabaseModel dbModel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/test", "root", "", "CCP_User_Table");
            UserDAO userDAO = new UserDAO(dbModel);

            //2. Load List of Images
            ImageModel imageModel = new ImageModel(System.getProperty("user.home") + "/Desktop/resources/");

            //3. Init Objects
            AuthenticationModel authenticationModel = new AuthenticationModel(userDAO, imageModel.getImageList());
            RegisterController registerCntrl = new RegisterController();
            LoginController loginCntrl = new LoginController();
            RegisterModel registerModel = new RegisterModel(authenticationModel, imageModel);
            LoginModel loginModel = new LoginModel(authenticationModel, imageModel);
            registerCntrl.setRegisterModel(registerModel);
            loginCntrl.setLoginModel(loginModel);

            SwingUtilities.invokeLater(() -> { //Runs GUI in EDT

                SwingUncaughtException.registerExceptionHandler();
                HyperView hp = new HyperView(loginCntrl, registerCntrl, imageModel.getImageList());
            });

        } catch (DatabaseException de) {
            JOptionPane.showMessageDialog(null, "Sorry, An Error Occured while contacting the database.\ncontact me at jaymit_123@hotmail.com", "Error.", JOptionPane.ERROR_MESSAGE);
        } catch (AuthenticationException | ImageAccessException e) {
            JOptionPane.showMessageDialog(null, "Sorry, An Error Occured.\ncontact me at jaymit_123@hotmail.com", "Error.", JOptionPane.ERROR_MESSAGE);
        }
    }
}
