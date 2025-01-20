package com.example.aplikasipemesananmakanansa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RiwayatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRiwayat;
    private RiwayatAdapter riwayatAdapter;
    private List<RiwayatItem> riwayatItems;
    private SharedPreferences sharedPreferences;
    private Button btnKeMenu;
    private ImageView reset;

    // Reset the order history stored in SharedPreferences
    private void reset() {
        // Get SharedPreferences to clear order history
        SharedPreferences sharedPreferences = getSharedPreferences("riwayat", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply(); // Apply changes asynchronously
        Toast.makeText(this, "Berhasil di RESET", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        // Initialize reset button and set click listener to reset the history
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(); // Call reset to clear history
            }
        });

        // Initialize the menu button and set the intent to go back to MainActivity
        btnKeMenu = findViewById(R.id.btnMenu);
        btnKeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatActivity.this, MainActivity.class);
                startActivity(intent); // Go back to the main menu
            }
        });

        // Set up the RecyclerView for displaying order history
        recyclerViewRiwayat = findViewById(R.id.recyclerViewRiwayat);
        riwayatItems = new ArrayList<>();
        sharedPreferences = getSharedPreferences("riwayat", MODE_PRIVATE);

        // Get the number of orders stored
        int riwayatCount = sharedPreferences.getInt("riwayat_count", 0);

        // Loop through each saved order and add them to the history list
        for (int i = 0; i < riwayatCount; i++) {
            String riwayatKey = "riwayat_" + i;
            String pesananJson = sharedPreferences.getString(riwayatKey, "");
            String metodePembayaran = sharedPreferences.getString(riwayatKey + "_metodePembayaran", "");
            String tanggalFormatted = sharedPreferences.getString(riwayatKey + "_tanggal", ""); // Retrieve formatted date

            Pesanan pesanan = convertJsonToPesanan(pesananJson);

            // Format each order details into a readable format
            String itemPesanan = "Item: " + pesanan.getItemName() + " (x" + pesanan.getQuantity() + ")";
            String totalHarga = "Harga: Rp " + (int) pesanan.getTotalPrice();
            String tanggal = "Tanggal: " + tanggalFormatted;
            String metodePembayaranText = "Metode Pembayaran: " + metodePembayaran;

            // Add the formatted order details to the history list
            RiwayatItem riwayatItem = new RiwayatItem(itemPesanan, totalHarga, tanggal, metodePembayaranText);
            riwayatItems.add(riwayatItem);
        }

        // Set up the adapter to display the history items in the RecyclerView
        riwayatAdapter = new RiwayatAdapter(riwayatItems);
        recyclerViewRiwayat.setAdapter(riwayatAdapter);
        recyclerViewRiwayat.setLayoutManager(new LinearLayoutManager(this)); // Set the layout manager to display items vertically
    }

    // Convert Pesanan object to JSON string
    private String convertPesananToJson(Pesanan pesanan) {
        Gson gson = new Gson();
        return gson.toJson(pesanan);
    }

    // Convert JSON string to Pesanan object
    private Pesanan convertJsonToPesanan(String pesananJson) {
        Gson gson = new Gson();
        return gson.fromJson(pesananJson, Pesanan.class);
    }
}
