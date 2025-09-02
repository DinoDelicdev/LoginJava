// File: LoginUiState.java
// 🔴 Make sure this package name is correct for your project.
package com.example.loginjava.login;

// This is the Java version of your Kotlin 'data class'.
// Its only job is to hold the information about what the UI should look like.
public class LoginUiState {

    // These fields are 'final' because a state should be immutable.
    // We create a new state object instead of changing an existing one.
    private final String email;
    private final String password;
    private final String emailError;
    private final String passwordError;
    private final boolean isLoading;
    private final boolean loginSuccess;

    // The default constructor for the initial state of the screen.
    public LoginUiState() {
        this.email = "";
        this.password = "";
        this.emailError = null;
        this.passwordError = null;
        this.isLoading = false;
        this.loginSuccess = false;
    }

    // A constructor to create a new state with updated values.
    public LoginUiState(String email, String password, String emailError, String passwordError, boolean isLoading, boolean loginSuccess) {
        this.email = email;
        this.password = password;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.isLoading = isLoading;
        this.loginSuccess = loginSuccess;
    }

    // "Getters" are public methods that let other classes read the private fields.
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getEmailError() { return emailError; }
    public String getPasswordError() { return passwordError; }
    public boolean isLoading() { return isLoading; }
    public boolean isLoginSuccess() { return loginSuccess; }
}