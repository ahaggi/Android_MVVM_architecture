package com.example.mvvm_architecture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvm_architecture.R;
import com.example.mvvm_architecture.model.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

// 1- create the adapterClass "with out extending anything"
// 2- create the viewHolderClass "MyViewHolder" and extend RecyclerView.ViewHolder:
//     This class will hold the  viewItem.xml in memory for each individual entry/data
//     and declare all of the widgets in the viewItem.xml
// 3- extend the adapterClass with RecyclerView.Adapter<T> where T is the newly created viewHolderClass "MyViewHolder"
// 4- Add the req members and init them in the default constructor
// 5- implement onCreateViewHolder and create & return an instance of "MyViewHolder"
// 6- implement onBindViewHolder

// IMPORTANT take a look at https://guides.codepath.com/android/using-the-recyclerview#binding-the-adapter-to-the-recyclerview
//                          https://www.youtube.com/watch?v=Vyqz_-sJGFk


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private static final String TAG = "RecycleViewAdapter";

    private List<Product> products;
    private Context context;

    private int cntOnCreateViewHolder = 0;
    private int cntOnBindViewHolder = 0;

    public RecycleViewAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is the place where the RecyclerView comes when it needs a new ViewHolder for a particular type of view. Initialisation specific things like setting onClickListeners should be done here.
        Log.d(TAG, "onCreateViewHolder: ##########################" + ++cntOnCreateViewHolder);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        // this method will be called every time an item is add to the list
        //Data is attached to the View in this function. Keep in mind that ViewHolders are recycled so the same ViewHolder will be used with some other Data as well, so better update data each time this function is called.
        Log.d(TAG, "onBindViewHolder: **************************" + ++cntOnBindViewHolder);


        final Product p = products.get(position);
        Glide.with(context).asBitmap().load("https://cdn2.thecatapi.com/images/163.jpg").apply(new RequestOptions().override(64, 64)).into(holder.productImageView);
        holder.productIdTextView.setText(p.getId() + "");
        holder.productTitleTextView.setText(p.getTitle());


        // if we want some action to happen when the user clicks an an item
        holder.productLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on product with id: " + p.getId());
                Snackbar.make(v, "onClick: clicked on product with id: " + p.getId(), Snackbar.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        //Whenever a ViewHolder occurs on the Screen, this callback is fired, User oriented events like Playing Videos or Audios when Views come onto screen should be done inside this.
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewRecycled(@NonNull MyViewHolder holder) {
        //Once a ViewHolder is successfully recycled, onViewRecycled gets called. This is when we should release resources held by the ViewHolder
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder holder) {
        //When the ViewHolder goes off the screen, this gets called. Perfect place to pause videos and audios, or other Memory intensive events.
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    // return the size of the data
    public int getItemCount() {

        // we could also use imgsUri.size()
        return products.size();
    }


    // This class will hold the widget/viewItem.xml in memory for each individual entry/data
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MyViewHolder";
        ImageView productImageView;
        TextView productIdTextView;
        TextView productTitleTextView;
        LinearLayout productLinearLayout;


        public MyViewHolder(@NonNull View v) {
            super(v);
            productImageView = v.findViewById(R.id.productImageView);
            productIdTextView = v.findViewById(R.id.productIdTextView);
            productTitleTextView = v.findViewById(R.id.productTitleTextView);
            productLinearLayout = v.findViewById(R.id.productLinearLayout);

        }
    }
}
