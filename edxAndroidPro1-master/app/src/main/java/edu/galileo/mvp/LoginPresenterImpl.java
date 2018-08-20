package edu.galileo.mvp;

import android.text.TextUtils;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            loginView.setPasswordError(R.string.error_invalid_password);
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            loginView.setUsernameError(R.string.error_field_required);
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
            return;
        } else if (!isEmailValid(username)) {
            loginView.setUsernameError(R.string.error_invalid_email);
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
            return;
        }

//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
//        }
        loginView.showProgress(true);
        loginModel.login(username, password, this);
    }

    @Override
    public void onCanceled() {
        loginView.showProgress(false);
    }

    @Override
    public void onPasswordError() {
        loginView.showProgress(false);
        loginView.setPasswordError(R.string.error_incorrect_password);
    }

    @Override
    public void onSuccess() {
        loginView.showProgress(false);
        loginView.successAction();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
