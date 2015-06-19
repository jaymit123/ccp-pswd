/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.app.database.DatabaseModel;
import java.sql.SQLException;
/**
 *
 * @author VJ
 */
public class CuedClickPoints {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
       DatabaseModel.execute();
    }
    
}
