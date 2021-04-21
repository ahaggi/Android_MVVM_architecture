package com.example.mvvm_architecture.repo;


import androidx.lifecycle.MutableLiveData;

import com.example.mvvm_architecture.model.Product;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

public class ProductRepo {
    // To ensure that this class can NOT be initiated via the constructor
    private ProductRepo(){}

    private static ProductRepo instance;
    private ArrayList<Product> products = new ArrayList<>();


    public static ProductRepo getInstance(){
        if (instance == null){
            instance = new ProductRepo();
        }
        return instance;
    }

    // fetching the data from API is typically done here
    public MutableLiveData<List<Product>> fetchProducts(){
        callSomeAPI();
        MutableLiveData<List<Product>> data = new MutableLiveData<>();
        data.setValue(products);
        return  data;
    }

    private void callSomeAPI(){
        for (int i = 0; i < 20; i++) {
            Product p = new Product(i , "Product nr: " + i ,"https://cdn2.thecatapi.com/images/163.jpg" );
            products.add(p);
        }
    }









    public ArrayList<Product> getProducts() {
        return products;
    }
    public static void setInstance(ProductRepo instance) {
        ProductRepo.instance = instance;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }


}
