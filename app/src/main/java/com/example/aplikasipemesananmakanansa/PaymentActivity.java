package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice;
    private Button btnRekening, btnCOD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Inisialisasi elemen UI
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        btnRekening = findViewById(R.id.btnRekening);
        btnCOD = findViewById(R.id.btnCOD);

        // Mendapatkan data produk dari Intent
        Intent intent = getIntent();
        String productNameString = intent.getStringExtra("product_name");
        int productPriceValue = intent.getIntExtra("product_price", 0);
        int productImageResId = intent.getIntExtra("product_image", R.drawable.placeholder_image);

        // Tampilkan data di elemen UI
        productName.setText(productNameString);
        productPrice.setText("Rp " + productPriceValue);
        productImage.setImageResource(productImageResId);

        // Menangani klik tombol pembayaran dengan Rekening
        btnRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke layar konfirmasi rekening
                Intent intentRekening = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                intentRekening.putExtra("paymentMethod", "Rekening");
                intentRekening.putExtra("product_name", productNameString);
                intentRekening.putExtra("product_price", productPriceValue);
                intentRekening.putExtra("product_image", productImageResId);
                startActivity(intentRekening);
            }
        });

        // Menangani klik tombol pembayaran dengan COD
        btnCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke layar konfirmasi COD
                Intent intentCOD = new Intent(PaymentActivity.this, ConfirmationActivity.class);
                intentCOD.putExtra("paymentMethod", "COD");
                intentCOD.putExtra("product_name", productNameString);
                intentCOD.putExtra("product_price", productPriceValue);
                intentCOD.putExtra("product_image", productImageResId);
                startActivity(intentCOD);
            }
        });
    }
}
