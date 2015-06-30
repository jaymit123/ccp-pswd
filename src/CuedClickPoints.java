/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.app.database.DatabaseModel;
import com.app.database.DatabaseType;
import com.app.database.UserDAO;
import com.app.exceptions.DAOException;
import com.app.exceptions.DatabaseException;
import com.app.exceptions.ImageAccessException;
import com.app.exceptions.SecurityException;
import com.app.security.register.EntryStatus;
import com.app.security.login.ExistingUser;
import com.app.security.login.PasswordResult;
import com.app.security.register.RegisterUser;
import com.app.security.Authentication;
import java.util.ArrayList;
import java.util.List;
import com.app.io.ImageModel;
import java.util.Iterator;

/**
 *
 * @author VJ
 */
public class CuedClickPoints {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SecurityException, DatabaseException, ImageAccessException {
        long t1,t2;
        System.out.println("|aaa.jpeg&81|aaa.jpeg&81|aaa.jpeg&81|aaa.jpeg&81|aaa.jpeg&10".matches("(\\|[^\\s/?<>\\\\:*|]+\\.[a-zA-Z0-9]{3,5}\\&\\d{2}){3,5}"));

        DatabaseModel dbmodel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
        UserDAO udao = new UserDAO(dbmodel);
        ImageModel im = new ImageModel("/Images/");
        Authentication aum = new Authentication(udao, im.getImageList());

System.out.println(aum.checkUsername("Jay1234"));

        RegisterUser r = new RegisterUser("Jay1234", "09d213");

        EntryStatus es = null;
        Iterator<String> z = im.getImageList().iterator();
        java.util.Scanner sc = new java.util.Scanner(System.in);
        
System.out.println(t1 = System.nanoTime());
        ExistingUser exuser = aum.processAccount("Jay1234", "09d213");
                System.out.println(t2 = System.nanoTime());
System.out.println(t2 -t1);
        PasswordResult pr;
        exuser.authenticateGrid(-1);

        do {
            int x = sc.nextInt();
            System.out.println(pr = exuser.authenticateGrid(x));
            System.out.println(pr.getMessage());
        } while (pr == PasswordResult.CONTINUE);
        

    }

}
