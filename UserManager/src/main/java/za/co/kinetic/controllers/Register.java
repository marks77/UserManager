package za.co.kinetic.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static za.co.kinetic.helpers.CloseURLConnection.closeServerConnection;

public class Register extends HttpServlet {

    private static String serviceURL;
    private static final Logger LOG = Logger.getLogger(Register.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String phone    = request.getParameter("phone");
        String password = request.getParameter("password");
        serviceURL = "http://localhost:8080/Kinetic/api/user/add?username="+username+"&phone="+phone+"&password="+password;
        
        try{
            URL url = new URL(serviceURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
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
            request.setAttribute("registrationMessage", jsonObj.get("registrationMessage").getAsString());       
            closeServerConnection(conn);
            getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);  
        } catch (MalformedURLException ex) {
            Logger.getLogger(ex.getMessage());
        }
    }
}