package za.co.kinetic.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.kinetic.exceptions.DAOException;

public class ConnectionManager {
    
    private static Connection conn;
    private static final Logger LOG = Logger.getLogger(ConnectionManager.class.getName());

    public ConnectionManager() {}

    public static Connection getConnection() throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException{ 
        try {
            if (conn != null) {
                if (!conn.isClosed()) {
                    LOG.info("Previous connection was not previously closed, closing connection now.");
                }
            }
            conn = getConnectionToDB();
            LOG.info("\n\nDataSource found (Kinetic)"); 
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return conn;
    }
    
    private static Connection getConnectionToDB() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return DriverManager.getConnection(DBLogin.USER.getDatabaseURL(), DBLogin.USER.getUsername(), DBLogin.USER.getPassword());
        } catch (SQLException ex) {
            LOG.log(Level.INFO, "Could not connect to Kinetic database: {0}", ex.getMessage());
        }
        return null;
    }
}