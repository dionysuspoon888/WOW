package com.example.wow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SQLiteActivity extends BaseActivity {


    //DB
    private SQLiteDatabase database;

    private RecyclerView recyclerView;
    private SQLite_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_page);

        //DB writable
        ShoppingCartDBHelper dbHelper = new ShoppingCartDBHelper((this));
        database = dbHelper.getWritableDatabase();

        setupShoppingList(R.drawable.p1,"Lacoste Men's Short Sleeve Stretch Dark Brown Croc Pique",766.14,1);
        setupShoppingList(R.drawable.p2,"NWT Wrangler Western Unlined Denim Jacket ",359.62,2);
        setupShoppingList(R.drawable.p3,"RYDER CUP - BNWT - CUTTER & BUCK - MEN'S ",387.97,9);
        setupShoppingList(R.drawable.p4,"Mens Slim Fitted Printed Blazer Sports Coat Blue Yellow Red",547.24,4);
        setupShoppingList(R.drawable.p7,"Archaic AFFLICTION Mens T-Shirt HERCULES Cross ",164.09,1);
        setupShoppingList(R.drawable.p8,"Lambretta Parka Mens Hooded Scooter Carnaby MOD SKA  ",764.96 ,9);


        setupRecyclerView();




    }
    public void setupShoppingList(int imageResources,String products,double prices,int quantitys) {
        ContentValues cv = new ContentValues();

        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_IMAGE, imageResources );
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRODUCT,products);
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_PRICE,prices);
        cv.put(ShoppingCartContract.ShoppingCartEntry.COLUMN_QUANTITY,quantitys);
        database.insert(ShoppingCartContract.ShoppingCartEntry.TABLE_NAME,null,cv);


    }


    public void setupRecyclerView() {

        recyclerView = findViewById(R.id.reyclerView_SQLite);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter=new SQLite_Adapter(this,getAllItems());//problem
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickedListener(new SQLite_Adapter.OnItemClickedListener() {
            @Override
            public void onItemClick(int position) {
                startShoppingCart(position);
            }
        });

        //Touch,Pull item to Remove
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag()); //Get the ID

            }


        }).attachToRecyclerView(recyclerView); //ItemTouchHelper attachs to recyclerview



    }

    private Cursor getAllItems() {
        //select Column from Table where a=b having . group by .orded by .

        return database.query(
                ShoppingCartContract.ShoppingCartEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,

                null
        );
    }

    public void startShoppingCart(int position){}


    public void removeItem(long id){
        //Delete the entry(row) from Table where _ID =id <-- passed by Tag in Adapter
        database.delete(ShoppingCartContract.ShoppingCartEntry.TABLE_NAME,
                ShoppingCartContract.ShoppingCartEntry._ID+'='+ id,null);
        adapter.swapCursor(getAllItems()); //update cursor after change

    }

}

