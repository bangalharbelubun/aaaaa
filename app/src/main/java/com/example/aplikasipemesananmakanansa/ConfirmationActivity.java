package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasipemesananmakanansa.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConfirmationActivity extends AppCompatActivity {

    private Button btnConfirmPayment;
    private EditText etShippingAddress;
    private Spinner spinnerPaymentMethod;
    private String selectedPaymentMethod = "Rekening"; // Default payment method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);
        etShippingAddress = findViewById(R.id.etShippingAddress);
        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);

        // Set up spinner for payment method selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter);

        spinnerPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPaymentMethod = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedPaymentMethod = "Rekening"; // Default to "Rekening"
            }
        });

        // Tombol konfirmasi pembayaran
        btnConfirmPayment.setOnClickListener(v -> {
            if (!etShippingAddress.getText().toString().isEmpty()) {
                // Simpan alamat ke server atau database
                Toast.makeText(this, "Pembayaran berhasil dikonfirmasi.", Toast.LENGTH_SHORT).show();
                sendPaymentNotification();
            } else {
                Toast.makeText(this, "Harap isi alamat pengiriman.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi untuk mengirim notifikasi ke WhatsApp
    private void sendPaymentNotification() {
        String phoneNumber = "6285321470260"; // Nomor WhatsApp yang menerima notifikasi (tanpa tanda "+")
        String message = "Pembayaran telah berhasil dikirim melalui "  + ". Harap mengirim bukti pembayaran.";

        try {
            // URL encode pesan untuk memastikan karakter khusus diproses dengan benar
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            String url = "https://wa.me/6285321470260?text=" + encodedMessage;

            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal mengirim notifikasi ke WhatsApp.", Toast.LENGTH_SHORT).show();
        }
    }
}
