package com.example.aplikasipemesananmakanansa;

public class Product {

    private String productName;
    private int price;
    private int imageResId;
    private float rating;
    private String comment;

    public Product(String productName, int price, int imageResId, float rating, String comment) {
        this.productName = productName;
        this.price = price;
        this.imageResId = imageResId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
