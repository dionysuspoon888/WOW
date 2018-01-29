package com.example.wow;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by D on 1/29/2018.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder>{
    private Context ctx;
    private ArrayList<ShoppingItem> shoppinglist;

    //Set Custom ClickedListener
    private OnItemClickedListener listener;

    //Contructor
    public ShoppingAdapter(Context ctx, ArrayList<ShoppingItem> shoppinglists){
        this.ctx = ctx;
        shoppinglist=shoppinglists;

    }

    public static class ShoppingViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewProduct;
        public TextView textViewPrice;

        public ShoppingViewHolder(View itemView, final OnItemClickedListener listeners) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_shopping_view);
            textViewProduct=itemView.findViewById(R.id.text_shopping_product);
            textViewPrice=itemView.findViewById(R.id.text_shopping_price);


            //Custom method
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listeners != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) { //Check Position is valid
                            listeners.onItemClick(position);
                        }

                    }
                }
            });
        }
    }

    @Override
    public ShoppingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.shoppingcart_item,parent,false);

        return new ShoppingViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(ShoppingViewHolder holder, int position) {
        ShoppingItem currentItem = shoppinglist.get(position);
        Double price = currentItem.getPrice();


        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textViewProduct.setText(currentItem.getProduct());
        holder.textViewPrice.setText("HK$ "+price);



    }

    @Override
    public int getItemCount() {
        return shoppinglist.size();
    }


    //Set Custom ClickedListener Interface
    public interface OnItemClickedListener{
        void onItemClick(int position);
    }


    //Set Custom ClickedListener
    public void setOnItemClickedListener(OnItemClickedListener listeners){
        listener =listeners;

    }
}
