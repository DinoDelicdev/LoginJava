package com.example.loginjava.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loginjava.network.ApiService;
import com.example.loginjava.network.LoginRequest; // We will create this next
import com.example.loginjava.network.LoginResponse; // We will create this next
import com.example.loginjava.network.RetrofitClient; // We will create this next

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginUiState> _uiState = new MutableLiveData<>(new LoginUiState());


    public LiveData<LoginUiState> getUiState() {
        return _uiState;
    }

    private final ApiService apiService = RetrofitClient.getApiService();

    public void onEmailChanged(String email) {
        LoginUiState currentState = _uiState.getValue();
        if (currentState != null) {

            LoginUiState newState = new LoginUiState(email, currentState.getPassword(), null, currentState.getPasswordError(), currentState.isLoading(), false);
            _uiState.postValue(newState);
        }
    }

    public void onPasswordChanged(String password) {
        LoginUiState currentState = _uiState.getValue();
        if (currentState != null) {
            LoginUiState newState = new LoginUiState(currentState.getEmail(), password, currentState.getEmailError(), null, currentState.isLoading(), false);
            _uiState.postValue(newState);
        }
    }


    private boolean validateInputs() {
        LoginUiState currentState = _uiState.getValue();
        if (currentState == null) return false;

        String email = currentState.getEmail();
        String password = currentState.getPassword();


        boolean isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        boolean isPasswordValid = password.length() >= 6;

        String emailError = null;
        String passwordError = null;

        if (!isEmailValid) {
            emailError = "Uneseni email nije u pravom formatu";
        }
        if (!isPasswordValid) {
            passwordError = "Šifra mora biti duga najmanje 6 karaktera";
        }


        if (emailError != null || passwordError != null) {
            _uiState.postValue(new LoginUiState(email, password, emailError, passwordError, false, false));
            return false;
        }

        return true;
    }


    public void handleLogin() {
        if (!validateInputs()) {
            return;
        }

        LoginUiState currentState = _uiState.getValue();
        if (currentState == null) return;


        _uiState.postValue(new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, null, true, false));


        LoginRequest request = new LoginRequest(currentState.getEmail(), currentState.getPassword());


        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    LoginUiState successState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, null, false, true);
                    _uiState.postValue(successState);
                } else {
                    LoginUiState errorState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, "Invalid credentials", false, false);
                    _uiState.postValue(errorState);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                LoginUiState failureState = new LoginUiState(currentState.getEmail(), currentState.getPassword(), null, "Network error", false, false);
                _uiState.postValue(failureState);
            }
        });
    }
}