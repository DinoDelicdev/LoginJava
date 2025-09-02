package com.example.loginjava.login;

public class LoginUiState {

    private final String email;
    private final String password;
    private final String emailError;
    private final String passwordError;
    private final boolean isLoading;
    private final boolean loginSuccess;

    // pocetni state
    public LoginUiState() {
        this.email = "";
        this.password = "";
        this.emailError = null;
        this.passwordError = null;
        this.isLoading = false;
        this.loginSuccess = false;
    }

    // Konstruktor za novi state sa updatovanim vrijednostima
    public LoginUiState(String email, String password, String emailError, String passwordError, boolean isLoading, boolean loginSuccess) {
        this.email = email;
        this.password = password;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isLoading = isLoading;
        this.loginSuccess = loginSuccess;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getEmailError() { return emailError; }
    public String getPasswordError() { return passwordError; }
    public boolean isLoading() { return isLoading; }
    public boolean isLoginSuccess() { return loginSuccess; }
}