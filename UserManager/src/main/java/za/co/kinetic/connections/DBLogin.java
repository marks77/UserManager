package za.co.kinetic.connections;

public enum DBLogin {
    
    USER("jdbc:mysql://localhost:3306/kinetic", "root", "password");
    
    private final String username;
    private final String password;
    private final String databaseURL;
    
    private DBLogin(String databaseURL, String username, String password){
        this.username = username;
        this.password = password;
        this.databaseURL = databaseURL;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseURL() {
        return databaseURL;
    } 
}