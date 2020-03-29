package com.example.mvvm_architecture.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_architecture.model.Product;
import com.example.mvvm_architecture.repo.ProductRepo;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productLiveData;
    private MutableLiveData<Boolean> isUpdating;


    private ProductRepo productRepo;


    public void init(){
        if (productLiveData != null)
            return;
      productRepo = ProductRepo.getInstance();
      productLiveData = productRepo.fetchProducts();
      isUpdating = new MutableLiveData<>();
    }

    public void addNewProduct(){

        new AsyncTask<Void , Void , Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // The value of isUpdating is been observed in "MainActivity", any change to it will excute the observer.
                isUpdating.setValue(true);
            }


            @Override
            protected Void doInBackground(Void... voids) {

                List<Product> list = productLiveData.getValue();
                Product p = new Product(list.size() , "Product nr: " + list.size() ,"https://cdn2.thecatapi.com/images/163.jpg" );
                list.add(p);

                productLiveData.postValue(list);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // The value of isUpdating is been observed in "MainActivity", any change to it will excute the observer.
                isUpdating.setValue(false);
            }

        }.execute();
    }




    public LiveData<List<Product>> getProductLiveData() {
        // MutableLiveData extends LiveData that's why can return "products" as LivaDate
        return productLiveData;
    }

    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }
}
