package com.example.mvvm_architecture.model;

import org.parceler.Parcel;

// Annotate this class with @Parcel, since we are using "Parceler" framework
@Parcel
public class Product {
    private int id;
    private String title;
    private String imageUri;

    public Product() {
    }

    public Product(int id, String title, String imageUri) {
        this.id = id;
        this.title = title;
        this.imageUri = imageUri;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUri='" + imageUri + '\'' +
                '}';
    }
}
