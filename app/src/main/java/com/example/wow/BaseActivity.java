package com.example.wow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by D on 1/27/2018.
 */

public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_shopping:
                startActivity(new Intent(this,ShoppingCartActivity.class));

                return true;
            case R.id.menu_firebase:
                startActivity( new Intent(this,FirebaseActivity.class));

                return true;
            case R.id.menu_jsonimage:
                startActivity(new Intent(this,JSONActivity.class));

                return true;
            case R.id.menu_musicplayer:
                startActivity( new Intent(this,MusicPlayer.class));

                return true;

            case R.id.menu_minigame:
                startActivity( new Intent(this,MiniGame.class));

                return true;
            case R.id.menu_info:
                startActivity( new Intent(this,InfoActivity.class));

                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}
