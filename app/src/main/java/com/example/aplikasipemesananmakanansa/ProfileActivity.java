package com.example.aplikasipemesananmakanansa;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvName, tvEmail, tvPhone;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi komponen UI
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        // Inisialisasi Firebase Auth dan Database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        // Memuat profil pengguna
        loadUserProfile();
    }

    private void loadUserProfile() {
        // Mendapatkan ID pengguna saat ini
        String userId = mAuth.getCurrentUser().getUid();

        // Mengambil data pengguna dari Firebase Realtime Database
        database.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Mengambil data pengguna dan mengisi UI
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        tvName.setText(user.getName());
                        tvEmail.setText(user.getEmail());
                        tvPhone.setText(user.getPhone());
                    }
                } else {
                    // Jika data tidak ditemukan
                    Toast.makeText(ProfileActivity.this, "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Menangani error saat mengambil data
                Toast.makeText(ProfileActivity.this, "Gagal memuat data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
