package Classes;

import java.util.Random;


public class PasswordGenerator {
     private int length;
    private boolean includeUppercase;
    private boolean includeNumbers;
    private boolean includeSpecialChars;
    
    public PasswordGenerator() {
        this.length = 12;
        this.includeUppercase = true;
        this.includeNumbers = true;
        this.includeSpecialChars = true;
    }
    
    public PasswordGenerator(int length, boolean includeUppercase, 
                           boolean includeNumbers, boolean includeSpecialChars) {
        this.length = length;
        this.includeUppercase = includeUppercase;
        this.includeNumbers = includeNumbers;
        this.includeSpecialChars = includeSpecialChars;
    }
    
    public int getLength() {
        return length;
    }
    
    public void setLength(int length) {
        this.length = length;
    }
    
    public boolean isIncludeUppercase() {
        return includeUppercase;
    }
    
    public void setIncludeUppercase(boolean includeUppercase) {
        this.includeUppercase = includeUppercase;
    }
    
    public boolean isIncludeNumbers() {
        return includeNumbers;
    }
    
    public void setIncludeNumbers(boolean includeNumbers) {
        this.includeNumbers = includeNumbers;
    }
    
    public boolean isIncludeSpecialChars() {
        return includeSpecialChars;
    }
    
    public void setIncludeSpecialChars(boolean includeSpecialChars) {
        this.includeSpecialChars = includeSpecialChars;
    }
    
    public String generate() {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String special = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        
        String chars = lowercase;
        if (includeUppercase) chars += uppercase;
        if (includeNumbers) chars += numbers;
        if (includeSpecialChars) chars += special;
        
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            password.append(chars.charAt(index));
        }
        
        return password.toString();
    }
    
    public int validateStrength(String password) {
        int strength = 0;
        if (password.length() >= 8) strength++;
        if (password.matches(".*[A-Z].*")) strength++;
        if (password.matches(".*[0-9].*")) strength++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*")) strength++;
        
        return strength;
    }
}
