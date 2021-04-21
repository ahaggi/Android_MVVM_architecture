package com.example.mvvm_architecture.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_architecture.model.Product;
import com.example.mvvm_architecture.repo.ProductRepo;

import org.parceler.Parcel;

import java.util.List;

// Annotate this class with @Parcel, since we are using "Parceler" framework
@Parcel
public class ProductViewModel{

    /*
    Why "ProductViewModel" class extends the "androidx.lifecycle.ViewModel"?
    A ViewModel is always created in association with a scope of (fragment/activity) and will be retained as long as the scope is alive.
    In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a configuration change (e.g. screen rotation). The new instance of the owner will just re-connected to the existing ViewModel.

    That is also why we initiate "ProductViewModel" inside the MainActivity as flws:
    productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
    instead of using its constructor "productViewModel = new ProductViewModel();"

    Lifecycle awareness even if "ProductViewModel" does not extends ViewModel:
        If we intiate an instance of "ProductViewModel" by its constructor, the "ProductViewModel" will not be aware of the (fragment/activity)'s lifecycle. Which means that in case of
        configuration change (e.g. screen rotation), the state of ProductViewModel will start over and the data will not be persistent.

        To fix that we have to assert that ProductViewModel is recreated only in case it is started for the first time.
        One way to do that is by checking if (savedInstanceState == null) then create new instance of ProductViewModel, Otherwise retrieve the prev saved instance of "ProductViewModel", and
        also we have the "ProductViewModel's instance" inside the overridden method protected void onSaveInstanceState(@NonNull Bundle outState) {..}
        Note: In order to save an instance of ProductViewModel, it has either:
            - implement the "Parcelable interface with writeToParcel() and createFromParcel() methods".
            - or use "Parceler" framework which simplify the management of Parcelable objects.
                    Add the Parceler dependency to app/gradle, and annotate ProductViewModel and its fieldsmembers with @Parcel

    * */

    private MutableLiveData<List<Product>> productLiveData;
    private MutableLiveData<Boolean> isUpdating;
    private ProductRepo productRepo;


    public void init() {
        if (productLiveData != null)
            return;

        productRepo = ProductRepo.getInstance();
        productLiveData = productRepo.fetchProducts();
        isUpdating = new MutableLiveData<>();
    }

    public void addNewProduct() {
        new AddProductAsyncTask(productLiveData , isUpdating).execute();
    }

    public LiveData<List<Product>> getProductLiveData() {
        // MutableLiveData extends LiveData that's why can return "products" as LivaDate
        return productLiveData;
    }
    public LiveData<Boolean> getIsUpdating() {
        return isUpdating;
    }
    public ProductRepo getProductRepo() {
        return productRepo;
    }


    public void setProductLiveData(MutableLiveData<List<Product>> productLiveData) {
        this.productLiveData = productLiveData;
    }
    public void setIsUpdating(MutableLiveData<Boolean> isUpdating) {
        this.isUpdating = isUpdating;
    }
    public void setProductRepo(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }










    private static class AddProductAsyncTask extends AsyncTask<Void, Void, Void> {
        private MutableLiveData<Boolean> isUpdating;
        private MutableLiveData<List<Product>> productLiveData;

        AddProductAsyncTask( MutableLiveData<List<Product>> _productLiveData , MutableLiveData<Boolean> _isUpdating) {
            this.isUpdating = _isUpdating;
            this.productLiveData = _productLiveData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // The value of isUpdating is been observed in "MainActivity", any change to it will execute the observer.
            isUpdating.setValue(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<Product> list = productLiveData.getValue();
            Product p = new Product(list.size(), "Product nr: " + list.size(), "https://cdn2.thecatapi.com/images/163.jpg");
            list.add(p);

            productLiveData.postValue(list);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // The value of isUpdating is been observed in "MainActivity", any change to it will execute the observer.
            isUpdating.setValue(false);
        }

    }
}
