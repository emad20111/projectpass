package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Operation {
    private final PasswordManager passwordManager;
    private final PasswordGenerator passwordGenerator;
    private static int currentUserId;
    private static String currentUsername;
    
    public Operation() {
        this.passwordManager = new PasswordManager();
        this.passwordGenerator = new PasswordGenerator();
    }
    
    public boolean login(String username, String password) {
        String sql = "SELECT user_id, username FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                currentUserId = rs.getInt("user_id");
                currentUsername = rs.getString("username");
                loadUserEntries(); 
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    
    public boolean register(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean changePassword(String oldPassword, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_id = ? AND password = ?";
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, currentUserId);
            stmt.setString(3, oldPassword);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public void addEntry(String serviceName, String username, String plainPassword, String url, String notes) {
        String encryptedPassword = encryptPassword(plainPassword);
        String sql = "INSERT INTO entries (service_name,user_id , username, encrypted_password, url, notes) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, serviceName);
            stmt.setInt(2, currentUserId);
            stmt.setString(3, username);
            stmt.setString(4, encryptedPassword);
            stmt.setString(5, url);
            stmt.setString(6, notes);
            stmt.executeUpdate();
            
           
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                PasswordEntry entry = new PasswordEntry(
                    rs.getInt(1), serviceName, username, encryptedPassword, url, notes
                );
                passwordManager.addEntry(entry);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public boolean deleteEntry(int entryId) {
        String sql = "DELETE FROM entries WHERE entry_id = ? AND user_id = ?";
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, entryId);
            stmt.setInt(2, currentUserId);
            boolean success = stmt.executeUpdate() > 0;
            
            if (success) {
                passwordManager.deleteEntry(entryId);
            }
            return success;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean updateEntry(int entryId, String serviceName, String username, String plainPassword, String url, String notes) {
        String encryptedPassword = encryptPassword(plainPassword);
        String sql = "UPDATE entries SET service_name = ?, username = ?, encrypted_password = ?, url = ?, notes = ? WHERE entry_id = ? AND user_id = ?";
        
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, serviceName);
            stmt.setString(2, username);
            stmt.setString(3, encryptedPassword);
            stmt.setString(4, url);
            stmt.setString(5, notes);
            stmt.setInt(6, entryId);
            stmt.setInt(7, currentUserId);
            boolean success = stmt.executeUpdate() > 0;
            
            if (success) {
                PasswordEntry entry = passwordManager.getEntryById(entryId);
                if (entry != null) {
                    entry.setServiceName(serviceName);
                    entry.setUsername(username);
                    entry.setEncryptedPassword(encryptedPassword);
                    entry.setUrl(url);
                    entry.setNotes(notes);
                }
            }
            return success;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public PasswordEntry getEntryById(int entryId) {
        return passwordManager.getEntryById(entryId);
    }
    
    public List<PasswordEntry> getAllEntries() {
        return passwordManager.getEntries();
    }
    
    public String getDecryptedPassword(int entryId) {
        PasswordEntry entry = passwordManager.getEntryById(entryId);
        if (entry != null) {
            return decryptPassword(entry.getEncryptedPassword());
        }
        return "";
    }
    
   
    public String generatePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecialChars) {
        passwordGenerator.setLength(length);
        passwordGenerator.setIncludeUppercase(includeUppercase);
        passwordGenerator.setIncludeNumbers(includeNumbers);
        passwordGenerator.setIncludeSpecialChars(includeSpecialChars);
        return passwordGenerator.generate();
    }
    
    public int validatePasswordStrength(String password) {
        return passwordGenerator.validateStrength(password);
    }
    
    
    private String encryptPassword(String plainPassword) {
         return CryptoUtils.encrypt(plainPassword);
    }
    
    private String decryptPassword(String encryptedPassword) {
         return CryptoUtils.decrypt(encryptedPassword);
    }
    
   
    private void loadUserEntries() {
        String sql = "SELECT * FROM entries WHERE user_id = ? ORDER BY service_name";
        try (PreparedStatement stmt = DBconnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();
            
            List<PasswordEntry> entries = new ArrayList<>();
            while (rs.next()) {
                PasswordEntry entry = new PasswordEntry(
                    rs.getInt("entry_id"),
                    rs.getString("service_name"),
                    rs.getString("username"),
                    rs.getString("encrypted_password"),
                    rs.getString("url"),
                    rs.getString("notes")
                );
                entries.add(entry);
            }
            passwordManager.setEntries((ArrayList<PasswordEntry>) entries);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    public String getCurrentUsername() {
        return currentUsername;
    }
    
    public int getCurrentUserId() {
        return currentUserId;
    }
    
     public void close() {
        DBconnection.closeConnection();
     }
    
}
