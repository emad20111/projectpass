package Classes;

public class PasswordEntry extends BaseEntity {
      private String serviceName;
    private String username;
    private String encryptedPassword;
    private String url;
    private String notes;
    
    public PasswordEntry() {
        super();
    }
    
    public PasswordEntry(int entryId, String serviceName, String username, 
                       String encryptedPassword, String url, String notes) {
        super(entryId);
        this.serviceName = serviceName;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.url = url;
        this.notes = notes;
    }
        
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return serviceName + " - " + username;
    }
}
