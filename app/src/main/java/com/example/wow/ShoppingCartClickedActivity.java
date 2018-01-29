package com.example.wow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by D on 1/29/2018.
 */

public class ShoppingCartClickedActivity extends BaseActivity {

    private String IMAGE = ShoppingCartActivity.EXTRA_IMAGE;
    private String PRODUCT = ShoppingCartActivity.EXTRA_PRODUCT;
    private String PRICE = ShoppingCartActivity.EXTRA_PRICE;
    private String QUANTITY =ShoppingCartActivity.EXTRA_QUANTITY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart_clicked);

        Intent i =getIntent();
        int imageRes=i.getIntExtra(IMAGE,0);
        String product=i.getStringExtra(PRODUCT);
        double price=i.getDoubleExtra(PRICE,0);
        int quantity=i.getIntExtra(QUANTITY,0);

        ImageView imageView=findViewById(R.id.image_shopping_clicked_view);
        TextView textViewProduct=findViewById(R.id.text_shopping_clicked_product);
        TextView textViewPrice=findViewById(R.id.text_shopping_clicked_price);
        TextView textViewQuantity=findViewById(R.id.text_shopping_clicked_qunatity);

        imageView.setImageResource(imageRes);
        textViewProduct.setText(product);
        textViewPrice.setText("HK$ "+price);
        textViewQuantity.setText("Quantity :"+quantity);


    }

    public void addToCart(View v){
        Toast.makeText(this, "This is sample Add to Cart", Toast.LENGTH_SHORT).show();
    }

    public void payNow(View v){
        Toast.makeText(this, "This is sample Pay Now", Toast.LENGTH_SHORT).show();
    }
}
