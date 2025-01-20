package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SuksesPesan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukses_pesan);
    }

    public void goBackToMain(View view) {
        Intent intent = new Intent(SuksesPesan.this, MainActivity.class);
        startActivity(intent);
        finish();  // Menutup halaman sukses
    }

}
