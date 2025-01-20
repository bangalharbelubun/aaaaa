package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BrandAdapter brandAdapter;
    private List<Brand> brandList;
    private Button btnLogout;  // Button untuk logout
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.rycView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Debugging: Log RecyclerView inisialisasi
        Log.d("MainActivity", "RecyclerView berhasil diinisialisasi");

        // Menambahkan daftar brand skincare
        brandList = new ArrayList<>();
        brandList.add(new Brand("The originote", R.drawable.theoriginote)); // Pastikan gambar ada di drawable
        brandList.add(new Brand("Elformula", R.drawable.elformula)); // Pastikan gambar ada di drawable
        brandList.add(new Brand("Skintific", R.drawable.aa)); // Pastikan gambar ada di drawable
        brandList.add(new Brand("Glad2Glow", R.drawable.glad2glow)); // Pastikan gambar ada di drawable

        // Debugging: Log jumlah brand
        Log.d("MainActivity", "Jumlah brand: " + brandList.size());

        // Inisialisasi adapter dan menghubungkannya dengan RecyclerView
        brandAdapter = new BrandAdapter(brandList, new BrandAdapter.OnBrandClickListener() {
            @Override
            public void onBrandClick(Brand brand) {
                // Saat brand diklik, buka ItemMenuActivity
                Intent intent = new Intent(MainActivity.this, ItemMenuActivity.class);
                intent.putExtra("brand_name", brand.getName());
                startActivity(intent);

                // Debugging: Log brand yang dikirim
                Log.d("MainActivity", "Mengirim brand_name: " + brand.getName());
            }
        });
        recyclerView.setAdapter(brandAdapter);

        // Debugging: Log adapter berhasil diatur
        Log.d("MainActivity", "Adapter berhasil di-set ke RecyclerView");

        // Menambahkan fungsi untuk tombol logout
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        // Logout dari akun Firebase
        mAuth.signOut();

        // Arahkan pengguna kembali ke LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Menutup MainActivity agar tidak bisa kembali ke halaman utama
    }

    // Navigasi ke RiwayatActivity
    public void toRiwayat(View view) {
        Intent intent = new Intent(this, RiwayatActivity.class);
        startActivity(intent);
        Log.d("MainActivity", "Navigasi ke RiwayatActivity");
    }

    // Navigasi ke DaftarPesananActivity
    public void toDaftarPesanan(View view) {
        Intent intent = new Intent(this, DaftarPesananActivity.class);
        startActivity(intent);
        Log.d("MainActivity", "Navigasi ke DaftarPesananActivity");
    }
}
