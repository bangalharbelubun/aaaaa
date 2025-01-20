package com.example.aplikasipemesananmakanansa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;  // Import Log
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasipemesananmakanansa.PaymentActivity;
import com.example.aplikasipemesananmakanansa.Product;
import com.example.aplikasipemesananmakanansa.ProductAdapter;
import com.example.aplikasipemesananmakanansa.R;

import java.util.ArrayList;
import java.util.List;

public class ItemMenuActivity extends AppCompatActivity {

    private TextView brandTitle;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);

        brandTitle = findViewById(R.id.brandTitle);
        recyclerView = findViewById(R.id.rycProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Mendapatkan nama brand yang dipilih dari Intent
        Intent intent = getIntent();
        String brandName = intent.getStringExtra("brand_name");
        brandTitle.setText("Produk dari " + brandName);

// Membuat daftar produk berdasarkan brand yang dipilih
        productList = new ArrayList<>();

// Menambahkan produk untuk brand "The originote"
        if (brandName.equals("The originote")) {
            productList.add(new Product("Ceraluronic Essence Toner", 40000, R.drawable.a, 4.5f, "Essence toner yang memberikan kelembapan dan membuat kulit lebih cerah."));
            productList.add(new Product("Cicamide Facial Cleanser", 55000, R.drawable.b, 4.2f, "Facial cleanser lembut dengan kandungan Cicamide untuk membersihkan kulit."));
            productList.add(new Product("Hyalucera Moisturizer", 80000, R.drawable.c, 4.7f, "Moisturizer dengan Hyalucera untuk melembapkan kulit sepanjang hari."));
            productList.add(new Product("Peeling Solution", 40000, R.drawable.d, 4.3f, "Peeling solution untuk eksfoliasi ringan dan membantu regenerasi kulit."));
        }
// Menambahkan produk untuk brand "Elformula"
        else if (brandName.equals("Elformula")) {
            productList.add(new Product("Barrier Serum", 150000, R.drawable.e, 4.8f, "Serum untuk memperkuat skin barrier dan menjaga kelembapan kulit."));
            productList.add(new Product("Barrier Sunscreen SPF 50 PA+++", 120000, R.drawable.f, 4.6f, "Sunscreen dengan SPF 50 PA+++ untuk melindungi kulit dari sinar UV."));
            productList.add(new Product("Barrier Essence", 120000, R.drawable.g, 4.5f, "Essence untuk kulit lembap dan bercahaya dengan perlindungan ekstra."));
            productList.add(new Product("Barrier Moisture Gel", 130000, R.drawable.h, 4.7f, "Gel pelembap yang cepat meresap untuk hidrasi optimal."));
        }
// Menambahkan produk untuk brand "Skintific"
        else if (brandName.equals("Skintific")) {
            productList.add(new Product("Glycolic Acid Daily Clarifying Toner", 25000, R.drawable.i, 4.6f, "Toner harian dengan Glycolic Acid untuk membersihkan dan menyegarkan kulit."));
            productList.add(new Product("Serum Sunscreen", 30000, R.drawable.j, 4.5f, "Sunscreen berbentuk serum untuk melindungi kulit tanpa rasa lengket."));
            productList.add(new Product("Gentle Gel Cleanser", 35000, R.drawable.k, 4.8f, "Gel pembersih lembut untuk semua jenis kulit, bahkan kulit sensitif."));
            productList.add(new Product("MSH Niacinamide", 40000, R.drawable.r, 4.7f, "Produk perawatan dengan Niacinamide untuk meratakan warna kulit."));
        }
// Menambahkan produk untuk brand "Glad2Glow"
        else if (brandName.equals("Glad2Glow")) {
            productList.add(new Product("Cushion", 25000, R.drawable.s, 4.6f, "Cushion yang memberikan hasil akhir glowing alami dan perlindungan UV."));
            productList.add(new Product("Micellar Water", 30000, R.drawable.t, 4.3f, "Micellar water untuk membersihkan wajah dari kotoran dan makeup."));
            productList.add(new Product("Brightening Clay Stick", 35000, R.drawable.u, 4.5f, "Clay stick untuk membersihkan pori-pori dan mencerahkan kulit."));
            productList.add(new Product("Brightening Moisturizer", 40000, R.drawable.v, 4.2f, "Moisturizer untuk mencerahkan dan melembapkan kulit sepanjang hari."));
        }


        // Debugging: Log jumlah produk dan nama produk
        Log.d("ItemMenuActivity", "Jumlah produk: " + productList.size());
        for (Product product : productList) {
            Log.d("ItemMenuActivity", "Produk: " + product.getProductName());
        }

        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                // Saat produk diklik, buka halaman pembayaran
                Intent intent = new Intent(ItemMenuActivity.this, PaymentActivity.class);
                intent.putExtra("product_name", product.getProductName());
                intent.putExtra("product_price", product.getPrice());
                intent.putExtra("product_image", product.getImageResId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(productAdapter);
    }
}
