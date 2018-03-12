package za.co.kinetic.usermanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;
import za.co.kinetic.connections.ConnectionManager;
import za.co.kinetic.exceptions.DAOException;


public class UserDao {
    
    private static final Logger LOG = Logger.getLogger(UserDao.class.getName());
    
    public UserDao(){}
    
    public boolean registerUser(User user) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        
        boolean userNameExists = checkIfUserNameExisits(user.getUsername());
        if(userNameExists){
            return false;
        } 
        
        String query = "INSERT INTO users (username, phone, password, status) VALUES(?, ?, ?, ?);";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, 1);
            preparedStatement.execute();
            return true;
         }catch (SQLException ex) {
            Logger.getLogger(ex.getMessage());
        }
        return false;
    }
    
    public List<User> getAllUsers(int id, String token, String loginOption) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{

        List<User> userList = new ArrayList<>();
        Timestamp  nowDate = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime nowDateTime = nowDate.toLocalDateTime();
        LocalDateTime currentDateMinusFiveMinutes = nowDateTime.minusMinutes(5);

        String query = "SELECT * FROM users WHERE status = 1;";
        if(checkIfIdAndTokenExist(id, token)){
            if(loginOption.equalsIgnoreCase("1")){
               query = "SELECT * FROM users WHERE status = ?;";
            }else if(loginOption.equalsIgnoreCase("2")) {
               query = "SELECT * FROM users WHERE loggedIn = ?;";
            }else if(loginOption.equalsIgnoreCase("3")) {
               query = "SELECT * FROM users WHERE status = 1 AND (last_login_time BETWEEN ('" + currentDateMinusFiveMinutes +"') AND ('" +nowDateTime+ "'));";
            }
            
            try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                
                switch (loginOption) {
                    case "1":
                        preparedStatement.setString(1, "1");
                        break;
                    case "2":
                        preparedStatement.setString(1, "Y");
                        break;
                }
                
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    User userObject = new User();
                    userObject.setId(rs.getInt("id"));
                    userObject.setPhone(rs.getString("phone"));
                    userList.add(userObject);                
                }
            }           
        }
        return userList;
    }
    
    
    public Map<String, String> loginUser(String username, String password) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {

         UUID token = UUID.randomUUID();
         String tokenString = token.toString();
         Map<String, String> userMap = new HashMap<>();
         String query = "SELECT * FROM users WHERE username = ? AND password = ? AND status = ?;";
         
         try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, 1);     
            ResultSet rs = preparedStatement.executeQuery(); 

            while (rs.next()) {
                User userObj = new User();
                userObj.setId(rs.getInt("id")); 
                userMap.put("id", String.valueOf(userObj.getId()));
                userMap.put("token", tokenString);
                userMap.put("error", "");
                updateUserTableWithIDToken(userObj.getId(), tokenString);
            }
         }catch (SQLException ex) {
            Logger.getLogger(ex.getMessage());
        }
        return userMap;
    }
    
    private void updateUserTableWithIDToken(int id, String token) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, ParseException{
        
         java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
         String query = "UPDATE users SET token = ?, last_login_time = ?, loggedIn = ? WHERE id = ?;";

         try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            preparedStatement.setString(1, token);
            preparedStatement.setTimestamp(2,  sqlDate);
            preparedStatement.setString(3, "Y");
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();          
         }
    }
    
    public Map<String, String> logOutUser(int id, String token) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        Map<String, String> map = new HashMap<>();
        boolean userExists = checkIfIdAndTokenExist(id, token);
        if(userExists){
            map.put("logoutStatus", "true");
            map.put("logoutMessage", "Logged out successfully.");
        }else{
            map.put("logoutStatus", "false");
            map.put("logoutMessage", "Error logging you out.");
        }

        loggOff(id, token, userExists);
        return map;
    }
    
    private boolean checkIfUserNameExisits(String username) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        String query = "SELECT username FROM users WHERE username = ?;";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
    }
    
    private boolean checkIfIdAndTokenExist(int id, String token) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        String query = "SELECT * FROM users WHERE id = ? AND token = ? AND loggedIn = ?;";
        
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, token);
            preparedStatement.setString(3, "Y");
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
    }
    
    private void loggOff(int id, String token, boolean userExists) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        
        if(userExists){
            String query = "UPDATE users SET loggedIn = 'N' WHERE id = ? AND token = ?;";

            try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, token);
                preparedStatement.executeUpdate();          
             }
        }
    }
}