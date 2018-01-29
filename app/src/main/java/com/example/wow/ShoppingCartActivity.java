package com.example.wow;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import java.util.ArrayList;

public class ShoppingCartActivity extends BaseActivity {
    public static final String EXTRA_IMAGE = "image";
    public static final String EXTRA_PRODUCT = "product";
    public static final String EXTRA_PRICE = "price";
    public static final String EXTRA_QUANTITY = "quantity";


    //DB
    private SQLiteDatabase database;

    private RecyclerView recyclerView;
    private ShoppingAdapter adapter;
    private ArrayList<ShoppingItem> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        //DB writable
        ShoppingCartDBHelper dbHelper = new ShoppingCartDBHelper((this));
        database = dbHelper.getWritableDatabase();

        setupShoppingList();
        setupRecyclerView();


    }


    public void setupShoppingList() {
        shoppingList = new ArrayList<>();
        shoppingList.add(new ShoppingItem(R.drawable.p1,"Lacoste Men's Short Sleeve Stretch Dark Brown Croc Pique",766.14,1));
        shoppingList.add(new ShoppingItem(R.drawable.p2,"NWT Wrangler Western Unlined Denim Jacket ",359.62,2));
        shoppingList.add(new ShoppingItem(R.drawable.p3,"RYDER CUP - BNWT - CUTTER & BUCK - MEN'S ",387.97,9));
        shoppingList.add(new ShoppingItem(R.drawable.p4,"Mens Slim Fitted Printed Blazer Sports Coat Blue Yellow Red",547.24,4));
        shoppingList.add(new ShoppingItem(R.drawable.p5,"TSHIRT T-SHIRT MAGLIETTA VASCO ROSSI CONCERTO",96.23,10));
        shoppingList.add(new ShoppingItem(R.drawable.p6,"AMBLIN ENTERTAINMENT T-shirt E.T. extra terrestrial Back ",262.35 ,6));

    }


    public void setupRecyclerView() {

        recyclerView = findViewById(R.id.reyclerView_shoppingCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new ShoppingAdapter(this, shoppingList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickedListener(new ShoppingAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                startShoppingCart(position);
            }
        });


    }
    //Set Up SQL Page
    public void setUpSQLite()

    {
        ContentValues cv = new ContentValues();
        String product = "p1";

        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_IMAGE, R.drawable.s1);
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRODUCT, product);
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRICE, 1);
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_QUANTITY, 3);
        database.insert(ShoppingCartContract.ShoppingCartEntry.TABLE_NAME, null, cv);

    }

    //Custom method after clicked
    public void startShoppingCart(int position){
        ShoppingItem shoppingItem= shoppingList.get(position);
        Intent shoppingintent= new Intent(this, ShoppingCartClickedActivity.class);
        shoppingintent.putExtra(EXTRA_IMAGE,shoppingItem.getImageResource());
        shoppingintent.putExtra(EXTRA_PRODUCT,shoppingItem.getProduct());
        shoppingintent.putExtra(EXTRA_PRICE,shoppingItem.getPrice());
        shoppingintent.putExtra(EXTRA_QUANTITY,shoppingItem.getQuantity());

        startActivity(shoppingintent);


    }

    public void sqlitePage(View v){
        startActivity(new Intent(this,SQLiteActivity.class));
    }

}