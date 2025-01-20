package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PesanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        // Logika untuk memproses pembayaran di sini (rekening atau COD)
        boolean paymentSuccessful = processPayment();

        // Setelah pembayaran berhasil
        if (paymentSuccessful) {
            // Menjalankan PaymentSuccessActivity setelah pembayaran berhasil
            Intent successIntent = new Intent(PesanActivity.this, PaymentSuccessActivity.class);
            startActivity(successIntent);
        } else {
            // Jika pembayaran gagal, tampilkan pesan kesalahan
            Toast.makeText(this, "Pembayaran Gagal, Silakan Coba Lagi", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean processPayment() {
        // Logika untuk memproses pembayaran, apakah melalui rekening atau COD
        // Misalnya pembayaran sukses, kembalikan true
        return true;  // Gantilah dengan logika yang sesuai
    }
}
