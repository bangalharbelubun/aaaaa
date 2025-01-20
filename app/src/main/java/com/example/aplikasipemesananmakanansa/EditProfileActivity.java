package com.example.aplikasipemesananmakanansa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private Button btnSave;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        loadCurrentUserProfile();

        // Tombol Simpan
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();

            if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                saveUserProfile(name, email, phone);
            } else {
                Toast.makeText(EditProfileActivity.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCurrentUserProfile() {
        String userId = mAuth.getCurrentUser().getUid();
        database.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                User user = task.getResult().getValue(User.class);
                if (user != null) {
                    etName.setText(user.getName());
                    etEmail.setText(user.getEmail());
                    etPhone.setText(user.getPhone());
                }
            } else {
                Toast.makeText(EditProfileActivity.this, "Gagal memuat data pengguna", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserProfile(String name, String email, String phone) {
        String userId = mAuth.getCurrentUser().getUid();
        User updatedUser = new User(name, email, phone);

        database.child(userId).setValue(updatedUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditProfileActivity.this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish(); // Kembali ke ProfileActivity setelah menyimpan
            } else {
                Toast.makeText(EditProfileActivity.this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
