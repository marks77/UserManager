package za.co.kinetic.usermanager;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import java.util.logging.Logger;
import javax.ws.rs.QueryParam;
import za.co.kinetic.exceptions.DAOException;
import static za.co.kinetic.helpers.EncryptionClass.encryptString;

@Path("/user")
public class UserService{
   
    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    UserDao userDao = new UserDao();
    
    @PUT
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response addUser(@FormParam("username") String username, 
                            @FormParam("phone")    String phone,
                            @FormParam("password") String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{

        User user = new User(username, phone, encryptString(password));
        Map<String, String> map = new HashMap<>();
        
        try {
            if(userDao.registerUser(user)){
                map.put("registrationMessage", "User added succesfully.");  
            }else{
                map.put("registrationMessage", "User could not be added succesfully.");
            }
        } catch (DAOException ex) {
            Logger.getLogger(ex.getMessage());
        }
        JSONObject json = new JSONObject(map);
        return Response.status(200).entity(json.toString()).build();
    }
    
    @GET
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getAllUsers(@QueryParam("id") int id, @QueryParam("token") String token, @QueryParam("loginOption") String loginOption) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
      
        List<User> userList = new ArrayList<>();
        userList =  userDao.getAllUsers(id, token, loginOption);
        JSONObject obj = new JSONObject();
        obj.put("users", userList);
        System.out.println(obj.toString());
        return Response.status(200).entity(obj.toString()).build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
   
    public Response loginUser(@FormParam("username") String username, 
                              @FormParam("password") String password,
                              @Context HttpServletResponse servletResponse) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException{
        
        Map<String, String> map = new HashMap<>();
        map = userDao.loginUser(username, password); 
        if(map.isEmpty()){
            map.put("error", "Username or password is incorrect.");
            map.put("id", "");
            map.put("token", "");
        }
        JSONObject json = new JSONObject(map);
        return Response.status(200).entity(json.toString()).build();
    }
    

    @GET
    @Path("/logout/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
   
    public Response logOutUser(@PathParam("id") int id, @QueryParam("token") String token ) throws DAOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
   
        Map<String, String> map = new HashMap<>();
        map = userDao.logOutUser(id, token);
        JSONObject json = new JSONObject(map);
        return Response.status(200).entity(json.toString()).build();
    }
}