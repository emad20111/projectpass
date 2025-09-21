package Classes;

import java.util.ArrayList;

public class PasswordManager {
    private ArrayList<PasswordEntry> entries;
    
    public PasswordManager() {
        this.entries = new ArrayList<>();
    }
    
    public ArrayList<PasswordEntry> getEntries() {
        return entries;
    }
    
    public void setEntries(ArrayList<PasswordEntry> entries) {
        this.entries = entries;
    }
    
    public void addEntry(PasswordEntry entry) {
        entries.add(entry);
    }
    
    public boolean deleteEntry(int entryId) {
        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getId() == entryId) {
                entries.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public PasswordEntry getEntryById(int entryId) {
        for (PasswordEntry entry : entries) {
            if (entry.getId() == entryId) {
                return entry;
            }
        }
        return null;
    }

   
    
}
