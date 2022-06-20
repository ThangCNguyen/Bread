package edu.sjsu.android.bread;

public class UserAccount {
    private String purchaseName;
    private String username;
    private String email;
    private String password;
    private Budget budget;

    public UserAccount(String purchaseName, String username, String email, String password, Budget budget) {
        this.purchaseName = purchaseName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.budget = budget;
    }

    public String getName() { return purchaseName; }
    public String getUsername() { return username; }
    public String getEmail() { return this.email; }
    public String getPassword() { return password; }

    public boolean verifyEmail() {
        return false; //Subject to modify
    }

    public boolean loginVerification() {
        return false; //Subject to modify
    }
}
