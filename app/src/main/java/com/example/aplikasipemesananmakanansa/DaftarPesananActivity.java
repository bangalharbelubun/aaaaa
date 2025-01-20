package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaftarPesananActivity extends AppCompatActivity {

    private ListView listViewPesanan;
    private List<String> pesananList;
    private TextView txtTotBayar;
    private Button btnByrSekarang;
    private SharedPreferences sharedPreferences;
    private Spinner spinnerPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pesanan);

        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod);
        txtTotBayar = findViewById(R.id.txtTotBayar);
        btnByrSekarang = findViewById(R.id.btnBayarSekarang);
        sharedPreferences = getSharedPreferences("orders", MODE_PRIVATE);

        // Atur adapter untuk Spinner dengan metode pembayaran
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.payment_methods, // Array dari strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaymentMethod.setAdapter(adapter);

        btnByrSekarang.setOnClickListener(view -> {
            // Panggil metode untuk menyimpan data ke SharedPreferences
            simpanPesananKeSharedPreferences();
        });

        listViewPesanan = findViewById(R.id.listViewPesanan);
        pesananList = new ArrayList<>();

        int orderCount = sharedPreferences.getInt("order_count", 0);
        float totalHargaKeseluruhan = 0;

        for (int i = 0; i < orderCount; i++) {
            String orderKey = "order_" + i;
            String itemName = sharedPreferences.getString(orderKey + "_itemName", "");
            int quantity = sharedPreferences.getInt(orderKey + "_quantity", 0);
            float totalPrice = sharedPreferences.getFloat(orderKey + "_totalPrice", 0);

            String pesananText = itemName + " (x" + quantity + ") Harga: Rp. " + (int) totalPrice;
            pesananList.add(pesananText);

            totalHargaKeseluruhan += totalPrice;
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pesananList);
        listViewPesanan.setAdapter(listAdapter);

        // Set total harga pada txtTotBayar
        txtTotBayar.setText("Rp. " + (int) totalHargaKeseluruhan);
    }

    private void simpanPesananKeSharedPreferences() {
        List<MenuItem> items = new ArrayList<>();
        int orderCount = sharedPreferences.getInt("order_count", 0);

        SharedPreferences riwayatSharedPreferences = getSharedPreferences("riwayat", MODE_PRIVATE);
        SharedPreferences.Editor riwayatEditor = riwayatSharedPreferences.edit();
        int riwayatCount = riwayatSharedPreferences.getInt("riwayat_count", 0);

        // Mendapatkan metode pembayaran dari Spinner
        String metodePembayaran = spinnerPaymentMethod.getSelectedItem().toString();

        // Loop melalui setiap pesanan yang ada
        for (int i = 0; i < orderCount; i++) {
            String orderKey = "order_" + i;
            String itemName = sharedPreferences.getString(orderKey + "_itemName", "");
            int quantity = sharedPreferences.getInt(orderKey + "_quantity", 0);
            float totalPrice = sharedPreferences.getFloat(orderKey + "_totalPrice", 0);
            MenuItem item = new MenuItem(itemName, "", totalPrice, 0);
            items.add(item);

            Date tanggalPesanan = new Date();
            Pesanan pesanan = new Pesanan(itemName, totalPrice, quantity, totalPrice, tanggalPesanan);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            String tanggalFormatted = dateFormat.format(tanggalPesanan);

            String pesananJson = convertPesananToJson(pesanan);

            String riwayatKey = "riwayat_" + riwayatCount;
            riwayatEditor.putString(riwayatKey, pesananJson);
            riwayatEditor.putString(riwayatKey + "_metodePembayaran", metodePembayaran);
            riwayatEditor.putString(riwayatKey + "_tanggal", tanggalFormatted);

            riwayatCount++;
        }

        riwayatEditor.putInt("riwayat_count", riwayatCount);
        riwayatEditor.apply();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(this, "Pesanan berhasil disimpan dengan metode pembayaran: " + metodePembayaran, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, RiwayatActivity.class);
        startActivity(intent);
    }

    private String convertPesananToJson(Pesanan pesanan) {
        Gson gson = new Gson();
        return gson.toJson(pesanan);
    }
}
