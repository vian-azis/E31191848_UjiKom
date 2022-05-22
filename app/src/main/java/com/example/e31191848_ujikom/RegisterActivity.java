package com.example.e31191848_ujikom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e31191848_ujikom.database.SQLiteHelper;
import com.example.e31191848_ujikom.model.ModelDaftar;

public class RegisterActivity extends AppCompatActivity {
    EditText inputNama, inputEmail, inputPassword, inputRePassword;
    Button buttonRegister;

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNama = findViewById(R.id.input_registrasi_nama);
        inputEmail = findViewById(R.id.input_registrasi_email);
        inputPassword = findViewById(R.id.input_registrasi_password);
        inputRePassword = findViewById(R.id.input_registrasi_re_password);

        buttonRegister = findViewById(R.id.register_save_button);

        sqLiteHelper = new SQLiteHelper(com.example.e31191848_ujikom.RegisterActivity.this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = inputNama.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String repassword = inputRePassword.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if(nama.isEmpty()) {
                    inputNama.setError("Masukkan nama");
                    return;
                } else {
                    inputNama.setError(null);
                }

                if (!email.matches(emailPattern)) {
                    inputEmail.setError("Alamat Email tidak valid");
                    inputEmail.requestFocus();
                    return;
                }

                if(email.isEmpty()) {
                    inputEmail.setError("Masukkan email");
                    return;
                } else {
                    inputEmail.setError(null);
                }

                if(password.isEmpty()) {
                    inputPassword.setError("Masukkan password");
                    return;
                } else {
                    inputPassword.setError(null);
                }

                if (password.length() < 6) {
                    inputPassword.setError("Password minimal terdiri dari 6 karakter");
                    inputPassword.requestFocus();
                    return;
                } else if (!password.equals(repassword)) {
                    inputRePassword.setError("Password tidak sama");
                    inputRePassword.requestFocus();
                    return;
                }

                if (sqLiteHelper.insertUser(new ModelDaftar(nama, email, password))) {
                    Toast.makeText(com.example.e31191848_ujikom.RegisterActivity.this, "Berhasil mendaftarkan akun", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(com.example.e31191848_ujikom.RegisterActivity.this, com.example.e31191848_ujikom.MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(com.example.e31191848_ujikom.RegisterActivity.this, "Email telah digunakan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(com.example.e31191848_ujikom.RegisterActivity.this, com.example.e31191848_ujikom.MainActivity.class));
        finish();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}