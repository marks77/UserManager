package za.co.kinetic.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import za.co.kinetic.exceptions.DAOException;
import static za.co.kinetic.helpers.CloseURLConnection.closeServerConnection;
import za.co.kinetic.usermanager.UserDao;
import za.co.kinetic.usermanager.DestroySessionVariables;

public class SessionListener extends HttpServlet implements HttpSessionListener{
    
    private static String serviceURL;
    private HttpURLConnection conn;
    private UserDao userDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DestroySessionVariables killSession;
    
    @Override
    public void sessionCreated(HttpSessionEvent event){
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent event){
        
        String userId = null;
        String token = null;
        HttpSession session = event.getSession();
        if(session.getAttribute("token") != null && session.getAttribute("id") != null){
            userId = (String) session.getAttribute("id");
            token = (String) session.getAttribute("token");
            serviceURL = "http://localhost:8080/Kinetic/api/user/logout/"+userId+"?token="+token;     
            Map<String, String> logOutMap = new HashMap<>();
            userDAO = new UserDao();
            
            URL url;
            try {
                url = new URL(serviceURL);
                conn = (HttpURLConnection) url.openConnection();    
                try {
                    conn.setRequestMethod("GET");
                } catch (ProtocolException ex) {
                    Logger.getLogger(SessionListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                conn.setRequestProperty("Content-Type", "application/json"); 
                conn.setRequestProperty("charset", "utf-8");
                conn.setDoOutput(true);
                System.out.println("Logging of the user due to inactivity.");
                logOutMap = userDAO.logOutUser(Integer.parseInt(userId), token);
                
                if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                } 
            
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
                String output = br.readLine();

                JsonObject jsonObj = new JsonParser().parse(output).getAsJsonObject();

                String logoutStatus = jsonObj.get("logoutStatus").getAsString();
                String logoutMessage = jsonObj.get("logoutMessage").getAsString();
                System.out.println("Logout status: " + logoutStatus);
                System.out.println("Logout message: " + logoutMessage);
                //killSession.destroySessionVariables();
                
                if(session.getAttribute("id") != null && session.getAttribute("token") != null){
                    session.removeAttribute("id");
                    session.removeAttribute("token");
                    session.invalidate();
                }
                closeServerConnection(conn);
                request.setAttribute("logoutMessage", logoutMessage);
                response.sendRedirect("/login.jsp");
               
            } catch (MalformedURLException ex) {
                Logger.getLogger(SessionListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SessionListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DAOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(SessionListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
