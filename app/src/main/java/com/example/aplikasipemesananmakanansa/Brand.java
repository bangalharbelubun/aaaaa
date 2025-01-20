// Brand.java
package com.example.aplikasipemesananmakanansa;

public class Brand {
    private final String name; // Nama brand
    private final int imageResId; // ID resource gambar

    // Constructor
    public Brand(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    // Getter untuk nama brand
    public String getName() {
        return name;
    }

    // Getter untuk ID resource gambar
    public int getImageResId() {
        return imageResId;
    }
}
