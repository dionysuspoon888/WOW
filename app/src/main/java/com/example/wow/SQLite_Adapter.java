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
public class SQLite_Adapter extends RecyclerView.Adapter<SQLite_Adapter.SQLiteViewHolder>{
    private Context ctx;
    private Cursor cursor;

    private ArrayList<ShoppingItem> shoppinglist;

    //Set Custom ClickedListener
    private OnItemClickedListener listener;

    //Contructor
    public SQLite_Adapter(Context context, Cursor cursors){
        ctx = context;
        cursor=cursors;

    }

    public static class SQLiteViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textViewProduct;
        public TextView textViewPrice;

        public SQLiteViewHolder(View itemView, final OnItemClickedListener listeners) {
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
    public SQLiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.shoppingcart_item,parent,false);

        return new SQLiteViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(SQLiteViewHolder holder, int position) {

        //ensure move to the position ,otherwise return
        if(!cursor.moveToPosition(position)){
            return;
        }

        //CoulumnIndex is the column name

        int imageID =cursor.getInt(cursor.getColumnIndex(ShoppingCartContract.ShoppingCartEntry.COLUMN_IMAGE));
        String product = cursor.getString(cursor.getColumnIndex(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRODUCT));
        double price=cursor.getDouble(cursor.getColumnIndex(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRICE));

        //ID
        long id =cursor.getInt(cursor.getColumnIndex(ShoppingCartContract.ShoppingCartEntry._ID));
        //Pass id to MainActivity
        holder.itemView.setTag(id);

        holder.imageView.setImageResource(imageID);
        holder.textViewProduct.setText(product);
        holder.textViewPrice.setText("HK$ "+price);



    }

    @Override
    public int getItemCount() {
        return cursor.getCount();//#row
    }


    //update Cursor
    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }
        cursor=newCursor;
        if (newCursor !=null){
            notifyDataSetChanged();
        }
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
