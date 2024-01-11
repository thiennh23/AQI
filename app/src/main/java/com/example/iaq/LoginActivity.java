package com.example.iaq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iaq.Models.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    APIInterface apiInterface;
    String grant_type = "password";
    String client_id = "openremote";
    EditText edt_username, edt_password;
    Button btn_login, btn_back;
    TextView tv_register;
    CheckBox showPass, rememberMe;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FindViewByID();

        //TEST
        imageView = findViewById(R.id.logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Button Login
        //Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usr = String.valueOf(edt_username.getText());
                String pwd = String.valueOf(edt_password.getText());
                getToken(usr, pwd);
            }
        });


        //Button back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //SHOW PASSWORD
        // Set up a listener for the CheckBox
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showPassword(showPass, edt_password);
            }
        });

    }

    void FindViewByID(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.registerTV);
        btn_back = findViewById(R.id.btnBack);
        showPass = findViewById(R.id.showpass);
        rememberMe = (CheckBox)findViewById(R.id.remember);
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

    public void getToken(String usr, String pwd) {
        Call<Token> call = apiInterface.login(client_id, usr, pwd, grant_type);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    assert response.body() != null;
                    Log.i("Login123", response.body().getAccess_token());
                    Token Token = response.body();
                    Log.i("Login123", response.toString());
                    APIClient.token = Token.access_token;
                    Log.i("Login123", Token.refresh_token);
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Can not login!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}