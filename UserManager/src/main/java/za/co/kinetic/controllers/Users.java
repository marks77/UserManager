package za.co.kinetic.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static za.co.kinetic.helpers.CloseURLConnection.closeServerConnection;
import za.co.kinetic.usermanager.User;


public class Users extends HttpServlet {
    
    private static final Logger LOG = Logger.getLogger(Users.class.getName());
    private static final long serialVersionUID = 1L;
    private static String serviceURL;
    private HttpURLConnection conn;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        HttpSession session = request.getSession();
        String userId = null;
        String token = null;
        List<User> userList = new ArrayList<>();
        String loginOption = request.getParameter("loginOption");
        
        if(session.getAttribute("id") != null && session.getAttribute("token") != null){
            userId = (String) session.getAttribute("id");
            token = (String) session.getAttribute("token");
        }
        
        serviceURL = "http://localhost:8080/Kinetic/api/user/users?id="+userId+"&token="+token+"&loginOption="+loginOption;
         try{
            URL url = new URL(serviceURL);
            conn = (HttpURLConnection) url.openConnection();    
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json"); 
            conn.setRequestProperty("charset", "utf-8");
            conn.setDoOutput(true);
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            } 
            
            LOG.info("\n********************************Result from Server: **********************************");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
            String output = br.readLine();
            LOG.info(output);
            
            JsonObject jsonObj = new JsonParser().parse(output).getAsJsonObject();
            int size = jsonObj.get("users").getAsJsonArray().size();
            JsonArray array = jsonObj.get("users").getAsJsonArray();
            
            for(int i = 0; i < array.size(); i++){
                User userObject = new User();
                JsonObject jObj = (JsonObject) array.get(i);
                userObject.setId(jObj.get("id").getAsInt());
                userObject.setPhone(jObj.get("phone").getAsString());
                userList.add(userObject);
            }
            request.setAttribute("userData", userList);
                        
            closeServerConnection(conn);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response); 
         }catch (MalformedURLException ex) {
            Logger.getLogger(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}