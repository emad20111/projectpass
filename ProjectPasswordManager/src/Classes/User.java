package Classes;

public class User extends BaseEntity{
     private String username;
    private String masterPassword;
    private PasswordManager passwordManager;
    
    public User() {
        super();
        this.passwordManager = new PasswordManager();
    }
    
    public User(String username, String masterPassword) {
        this();
        this.username = username;
        this.masterPassword = masterPassword;
    }
        
    public PasswordManager getPasswordManager() {
        return passwordManager;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getMasterPassword() {
        return masterPassword;
    }
    
    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }
    }
    

