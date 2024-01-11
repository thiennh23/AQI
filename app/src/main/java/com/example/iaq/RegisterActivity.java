package com.example.iaq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Button back;
    private Button register;
    private EditText username;
    private EditText email;
    private EditText pwd;
    private EditText rePwd;
    private static final int CHECK_INTERVAL_MS = 500; // Every half a second
    private static final int TIMEOUT_MS = 10000; // 10 seconds
    private WebView webview;
    private ProgressBar progress;
    private boolean hasFormSubmitted = false;
    TextView LoginTV;
    CheckBox showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FindViewByID();

        //Register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.setVisibility(View.INVISIBLE);
                progress.setVisibility(View.VISIBLE);

                String usernameSTR = username.getText().toString().trim();
                String emailSTR = email.getText().toString().trim();
                String passwordSTR = pwd.getText().toString().trim();
                String repasswordSTR = rePwd.getText().toString().trim();
                register(usernameSTR, emailSTR, passwordSTR, repasswordSTR);
            }
        });

        //Back button
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //Click to textview LOGIN
        LoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //SHOW PASSWORD
        // Set up a listener for the CheckBox
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPassword(showPass, pwd);
                showPassword(showPass, rePwd);
            }
        });

    }

    void FindViewByID(){
        LoginTV = findViewById(R.id.LoginTV);
        back = findViewById(R.id.btn_back);
        username = findViewById(R.id.edt_username);
        email = findViewById(R.id.edt_email);
        pwd = findViewById(R.id.edt_password);
        rePwd = findViewById(R.id.edt_rePassword);
        register = findViewById(R.id.btn_register);
        showPass = findViewById(R.id.showpass);
        webview = findViewById(R.id.wv_loading);
        progress = findViewById(R.id.loading);
    }

    public void showPassword(CheckBox showpass, EditText pass)
    {
        // Save cursor position
        int cursorPosition = pass.getSelectionStart();
        // Get the current left drawables
        Drawable leftDrawable = pass.getCompoundDrawables()[0];
        if (showpass.isChecked()) {
            // If the CheckBox is checked, show the password
            pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility), null);
        } else {
            // If the CheckBox is unchecked, hide the password
            pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pass.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, getResources().getDrawable(R.drawable.visibility_off), null);
        }
        // Restore cursor position
        pass.setSelection(cursorPosition);
    }

    //REGISTER WEB-VIEW
    private void register(String usr, String mail, String pass, String rePass) {
        // Clear cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("WebView", "Page loaded: " + url);
                super.onPageFinished(view, url);

                // Handling different URLs
                if(url.contains("swagger")) {
                    clickButtonWhenAvailable(view, ".btn.authorize.unlocked");
                }

                if(url.contains("openid-connect/auth") || url.contains("login-actions/authenticate")) {
                    view.evaluateJavascript("document.querySelector('a.btn.waves-effect.waves-light').click();", null);
                }

                if (hasFormSubmitted) {
                    view.evaluateJavascript(
                            "(function() { \n" +
                                    " let emailError = document.querySelector('[data-error=\"Email already exists.\"]'); \n" +
                                    " let invalidEmail = document.querySelector('[data-error=\"Invalid email address.\"]'); \n" +
                                    " let passwordError = document.querySelector('[data-error=\"Password confirmation doesn\\'t match.\"]'); \n" +
                                    " let usernameError = document.querySelector('span.red-text');\n" +
                                    " if (emailError) return 'emailError';\n" +
                                    " else if (invalidEmail) return 'invalidEmail';\n" +
                                    " else if (usernameError) return 'usernameError';\n" +
                                    " else if (passwordError) return 'passwordError';\n" +
                                    " else return null\n" +
                                    " })();",
                            value -> {
                                Log.i("WebView", "hasFormSubmitted: " + url);
                                if (value != null && !value.equals("null")) {
                                    switch (value) {
                                        case "\"emailError\"":
                                            Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "\"invalidEmail\"":
                                            Toast.makeText(RegisterActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "\"usernameError\"":
                                            Toast.makeText(RegisterActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                                            break;
                                        case "\"passwordError\"":
                                            Toast.makeText(RegisterActivity.this, "Password confirmation doesn't match", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                    hasFormSubmitted = false;
                                    register.setVisibility(View.VISIBLE);
                                    progress.setVisibility(View.INVISIBLE);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                }

                if(url.contains("login-actions/registration")) {
                    Log.i("Webview", "Register: " + url);
                    String userScript = "document.getElementById('username').value = '" + usr + "';";
                    String emailScript = "document.getElementById('email').value = '" + mail + "';";
                    String pwdScript = "document.getElementById('password').value = '" + pass + "';";
                    String rePwdScript = "document.getElementById('password-confirm').value = '" + rePass + "';";

                    view.evaluateJavascript(userScript, null);
                    view.evaluateJavascript(emailScript, null);
                    view.evaluateJavascript(pwdScript, null);
                    view.evaluateJavascript(rePwdScript, null);
                    if (!hasFormSubmitted) {
                        view.evaluateJavascript("document.querySelector('button[name=\"register\"]').click();", null);
                        hasFormSubmitted = true;
                    }
                }
            }
        });

        webview.loadUrl("https://uiot.ixxc.dev/swagger/#/");
    }


    private void clickButtonWhenAvailable(WebView webView, String selector) {
        final long startTime = System.currentTimeMillis();

        final Runnable checkInitialButtonExistence  = new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript(
                        "(function(selector) { return !!document.querySelector(selector); })('" + selector + "');",
                        value -> {
                            // If button exists or we've reached timeout, try clicking or exit
                            if ("true".equals(value) || System.currentTimeMillis() - startTime > TIMEOUT_MS) {
                                webView.evaluateJavascript("document.querySelector('" + selector + "').click();", null);
                                webView.evaluateJavascript("document.querySelector('.btn.modal-btn.auth.authorize.button').click();",null);
                            } else {
                                // Otherwise, keep checking
                                new Handler().postDelayed(this, CHECK_INTERVAL_MS);
                            }
                        }
                );
            }
        };
        // Start the checking
        new Handler().postDelayed(checkInitialButtonExistence , CHECK_INTERVAL_MS);
    }
}