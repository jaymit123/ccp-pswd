/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.app.database.DatabaseModel;
import com.app.database.DatabaseType;
<<<<<<< HEAD
import com.app.database.UserDAO;
import com.app.exceptions.DAOException;
=======
>>>>>>> origin/master
import com.app.exceptions.DatabaseException;
import com.app.security.register.EntryStatus;
import com.app.security.login.ExistingUser;
import com.app.security.login.PasswordResult;
import com.app.security.register.RegisterUser;
import com.app.security.AuthenticationModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VJ
 */
public class CuedClickPoints {

    /**
     * @param args the command line arguments
     */
<<<<<<< HEAD
    public static void main(String[] args) throws DatabaseException, DAOException {

        DatabaseModel dbmodel = new DatabaseModel(DatabaseType.MYSQL, "//localhost/db", "root", "", "CCP_User_Table");
        UserDAO udao = new UserDAO(dbmodel);
         List<String> a = new ArrayList<String>() {
            {
                add("x1.png");
                add("img.jpeg");
                add("x2.png");
                add("imgfd.png");
                add("x3.png");
                add("igigigigig.png");
                add("x4.png");
                add("x5.png");
                add("x6.png");
            }
        };
        AuthenticationModel aum = new AuthenticationModel(udao, a);
       
      
       
      
      /*  
        
        RegisterUser r = new RegisterUser("JayTM","MTyaJ");
        EntryStatus es = null;
                java.util.Scanner sc = new java.util.Scanner(System.in);
       do{
           String x = sc.next();
           int b = sc.nextInt();
           es = r.addEntry(x, b);
          System.out.println(es.getMessage());
       }while(es != EntryStatus.COMPLETED);
     System.out.println(aum.finalizeRegistration(r));
}*/
  
        ExistingUser exuser = aum.processAccount("JayTM","MTyaJ");
        PasswordResult pr;
        exuser.authenticateGrid(-1);
        java.util.Scanner sc = new java.util.Scanner(System.in);
        do {
            int x = sc.nextInt();
            System.out.println(pr = exuser.authenticateGrid(x));
            System.out.println(pr.getMessage());
        } while (pr == PasswordResult.CONTINUE);
    }
    


}
=======
   public static void main(String[] args)  {
  try{  DatabaseModel dbmodel = new DatabaseModel(DatabaseType.MYSQL,"//localhost/db","root","","CCP_User_Table");  
     

System.out.print(dbmodel.registerUser("ssddddddds", "s", ""));
}catch(DatabaseException e){
    
}
}}
>>>>>>> origin/master
