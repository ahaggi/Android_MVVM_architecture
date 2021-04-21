package com.example.mvvm_architecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_architecture.model.Product;
import com.example.mvvm_architecture.viewmodel.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.mvvm_architecture.adapter.*;


/*
-----------------
| RecyclerView  |
| LayoutManager |  ->  Adapter  ->  Dataset
-----------------

            https://guides.codepath.com/android/using-the-recyclerview#binding-the-adapter-to-the-recyclerview
*/

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";



    private RecycleViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private ProgressBar progress_circular;

    private ProductViewModel productViewModel;



    /*
    * the data is init/fetched in the "ProductRepo"
    * observing the changes is done through "ProductViewModel"
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        initViews();
        setupFab();


    }
    private void initViewModel(){
        /*
        // With ViewModelFactory
        val viewModel = ViewModelProvider(this, YourViewModelFactory).get(YourViewModel::class.java)
        OR
        //Without ViewModelFactory
        val viewModel = ViewModelProvider(this).get(YourViewModel::class.java)
        */
        // Without ViewModelFactory

        // IMPORTANT read the comments in ProductViewModel file, on "Why "ProductViewModel" class extends the "androidx.lifecycle.ViewModel"?"

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.init();

        // Observe changes done to the LiveData "the objects in our ViewModel"
        productViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.notifyDataSetChanged();
            }


        });
        productViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                showProgressCircle(aBoolean);
                if (!aBoolean)
                    scrollToPosition(productViewModel.getProductLiveData().getValue().size()-1);
            }
        });



    }

    private void initViews(){
        adapter = new RecycleViewAdapter(this, productViewModel.getProductLiveData().getValue());
        recyclerView = findViewById(R.id.recycleView);
        fab = findViewById(R.id.fab_add);
        progress_circular = findViewById(R.id.progressCircular);
        initRecycleView();


    }



    private void initRecycleView() {
        Log.d(TAG, "initRecycleView: ");

        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//      setLayoutManager is to set the layout of the contents, i.e. list of repeating views in the recycler view
        recyclerView.setLayoutManager(linearLayoutManager);
    }



    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productViewModel.addNewProduct();
                Toast.makeText(view.getContext(), "bla bla bla!", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void scrollToPosition(int position){
//        alt 2- smooth scrolling
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        ((LinearLayoutManager) recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
    }



    private void showProgressCircle(boolean b){
        int visibility = b ? View.VISIBLE : View.GONE;
        progress_circular.setVisibility(visibility);
    }


}


