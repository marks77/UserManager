package za.co.kinetic.usermanager;

import java.io.Serializable;

public class User implements Serializable{
    
    private String username;
    private String phone;
    private String password;
    private int id;

    public User(){}
    
    public User(String username, String phone, String password){
        this.username = username;
        this.phone    = phone;
        this.password = password;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", phone=" + phone + ", password=" + password + ", id=" + id +"}";
    }   
}