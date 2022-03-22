/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import hr.algebra.utils.MessageUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author zakesekresa
 */
public class DataSourceSingleton {

    private static String SERVER_NAME;
    private static String DATABASE_NAME;
    private static String USER;
    private static String PASSWORD;

    private DataSourceSingleton() {
    }

    static {
        try {
            // Load Settings
            Properties loadProps = new Properties();
            loadProps.loadFromXML(new FileInputStream("databaseConnectionSettings.xml"));
            SERVER_NAME = loadProps.getProperty("SERVER_NAME");
            DATABASE_NAME = loadProps.getProperty("DATABASE_NAME");
            USER = loadProps.getProperty("USER");
            PASSWORD = loadProps.getProperty("PASSWORD");

        } catch (IOException iOException) {
            Logger.getLogger(DataSourceSingleton.class.getName()).log(Level.SEVERE, null, iOException);
            MessageUtils.showErrorMessage("Error", "Database connection configuration file error.");
            System.exit(-1);
        }
    }
    private static DataSource instance;

    public static DataSource getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static DataSource createInstance() {
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setServerName(SERVER_NAME);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
