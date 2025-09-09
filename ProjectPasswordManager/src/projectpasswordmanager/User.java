package projectpasswordmanager;

public class User {
    private int userid;
    private String username ;
    private String masterpassword ;
    private PasswordManager passwordmanager;

    public User() {
    }

    public User(String username, String masterpassword) {
        this.username = username;
        this.masterpassword = masterpassword;
    }
    
}
