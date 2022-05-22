package com.example.e31191848_ujikom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e31191848_ujikom.database.SQLiteHelper;

public class MainActivity extends AppCompatActivity {
    EditText inputEmail, inputPassword;
    Button btnLogin;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String dataLogin;

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.login_button);

        sharedPreferences = com.example.e31191848_ujikom.MainActivity.this.getSharedPreferences("Remember", MODE_PRIVATE);
        dataLogin = sharedPreferences.getString("login", "");

        sqLiteHelper = new SQLiteHelper(com.example.e31191848_ujikom.MainActivity.this);

        if (dataLogin.isEmpty()) {
            // harus login
        } else {
            startActivity(new Intent(com.example.e31191848_ujikom.MainActivity.this, MapsActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(email.isEmpty()) {
                    inputEmail.setError("masukkan email");
                    return;
                } else {
                    inputEmail.setError(null);
                }

                if(password.isEmpty()) {
                    inputPassword.setError("masukkan password");
                    return;
                } else {
                    inputPassword.setError(null);
                }

                if (sqLiteHelper.getSingleUser(email, password)) {
                    Toast.makeText(com.example.e31191848_ujikom.MainActivity.this, "Selamat Datang " + email, Toast.LENGTH_SHORT).show();
                    editor = sharedPreferences.edit();
                    editor.putString("login", email);
                    editor.commit();

                    startActivity(new Intent(com.example.e31191848_ujikom.MainActivity.this, MapsActivity.class));
                    finish();
                } else {
                    Toast.makeText(com.example.e31191848_ujikom.MainActivity.this, "Email atau Password anda salah", Toast.LENGTH_SHORT).show();

                    inputEmail.setText("");
                    inputPassword.setText("");

                    inputEmail.requestFocus();
                }
            }
        });
    }

    public void registerAction(View view) {
        startActivity(new Intent(com.example.e31191848_ujikom.MainActivity.this, com.example.e31191848_ujikom.RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        finish();
    }
}