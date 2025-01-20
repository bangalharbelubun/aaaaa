package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etPhone;
    private Button btnRegister;
    private FirebaseAuth mAuth; // Firebase Authentication instance
    private FirebaseDatabase mDatabase; // Firebase Database instance
    private DatabaseReference mDatabaseReference; // Reference to Firebase Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi view dan FirebaseAuth
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("users");

        // Tombol Register
        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String phone = etPhone.getText().toString();

            // Validasi input
            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(email, password, name, phone);
            }
        });
    }

    private void registerUser(String email, String password, String name, String phone) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registrasi berhasil
                        String userId = mAuth.getCurrentUser().getUid(); // Mendapatkan UID pengguna yang baru terdaftar

                        // Membuat objek User
                        User user = new User(name, email, phone);

                        // Menyimpan data pengguna ke Firebase Realtime Database
                        mDatabaseReference.child(userId).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        // Data berhasil disimpan
                                        Toast.makeText(RegisterActivity.this, "Akun berhasil dibuat", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Gagal menyimpan data pengguna
                                        Toast.makeText(RegisterActivity.this, "Gagal menyimpan data pengguna", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Registrasi gagal
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
