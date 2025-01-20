package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Mengatur timer untuk berpindah ke LoginActivity setelah 2 detik
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Loading.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Menutup aktivitas Loading agar tidak bisa kembali
        }, 2000); // Waktu delay dalam milidetik (2000 ms = 2 detik)
    }
}
