package za.co.kinetic.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static za.co.kinetic.helpers.CloseURLConnection.closeServerConnection;
import za.co.kinetic.helpers.EncryptionClass;

public class Login extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(Login.class.getName());
    private static final long serialVersionUID = 1L;
    private static String serviceURL;
    private HttpURLConnection conn;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = EncryptionClass.encryptString(request.getParameter("password"));
        serviceURL = "http://localhost:8080/Kinetic/api/user/login?username="+username+"&password="+password; // Ideally this end point should be configurable.
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3 * 60); // Ideally this figures should be configurable.

        try{
            URL url = new URL(serviceURL);
            conn = (HttpURLConnection) url.openConnection();    
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }         
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
            String output = br.readLine();
          
            LOG.info("\n********************************Result from Server: " + output + "**********************************");
            
            JsonObject jsonObj = new JsonParser().parse(output).getAsJsonObject();
            
            String error = jsonObj.get("error").getAsString();
            String id = jsonObj.get("id").getAsString();
            String token = jsonObj.get("token").getAsString(); 
            if(error == null || error.isEmpty()){
                session.setAttribute("id", id);
                session.setAttribute("token", token);
                closeServerConnection(conn);
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);  
            }else{
                closeServerConnection(conn);
                request.setAttribute("error", error);
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response); 
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}