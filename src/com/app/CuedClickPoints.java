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
import com.app.user.dao.DAOException;
import com.app.user.database.DatabaseException;
import com.app.io.ImageAccessException;
import com.app.user.security.SecurityException;
import com.app.user.register.RegisterStatus;
import com.app.user.register.RegisterUser;
import com.app.user.security.AuthenticationModel;
import java.util.ArrayList;
import java.util.List;
import com.app.io.ImageModel;
import com.app.user.main.HyperView;
import com.app.user.register.RegisterModel;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author VJ
 */
public class CuedClickPoints {

    public static void main(String[] args) throws SecurityException, DatabaseException, ImageAccessException {
          
        DatabaseModel dbmodel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
        UserDAO udao = new UserDAO(dbmodel);
        ImageModel im = new ImageModel("/Images/");
        AuthenticationModel aum = new AuthenticationModel(udao, im.getImageList());
        RegisterController rg = new RegisterController();

        RegisterModel rm = new RegisterModel(aum, im);
        rg.setRegisterModel(rm);

        HyperView hp = new HyperView(rg, im.getImageList());
    }
}
