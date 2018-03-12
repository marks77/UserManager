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

public class Logout extends HttpServlet{ 
    
    private static final Logger LOG = Logger.getLogger(Logout.class.getName());
    private static final long serialVersionUID = 1L;
    private static String serviceURL;
    private HttpURLConnection conn;
    private HttpSession session;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        session = request.getSession();
        String userId = null;
        String token = null;
        if(session.getAttribute("id") != null && session.getAttribute("token") != null){
            userId = (String) session.getAttribute("id");
            token = (String) session.getAttribute("token");
        }
        
        serviceURL = "http://localhost:8080/Kinetic/api/user/logout/"+userId+"?token="+token;
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
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));      
            String output = br.readLine();
          
            LOG.info("\n********************************Result from Server: " + output + "**********************************");
            
            JsonObject jsonObj = new JsonParser().parse(output).getAsJsonObject();
            
            String logoutStatus = jsonObj.get("logoutStatus").getAsString();
            String logoutMessage = jsonObj.get("logoutMessage").getAsString();
            
            if(session.getAttribute("id") != null && session.getAttribute("token") != null){
                session.removeAttribute("id");
                session.removeAttribute("token");
                session.invalidate();
            }
            
            if(logoutStatus.equalsIgnoreCase("true")){
                request.setAttribute("logoutMessage", logoutMessage);
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
                closeServerConnection(conn);
            }else{
                request.setAttribute("logoutMessage", logoutMessage);
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                closeServerConnection(conn);
            }
        }catch (MalformedURLException ex) {
            Logger.getLogger(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Logout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}
}
