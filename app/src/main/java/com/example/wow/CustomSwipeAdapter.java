package com.example.wow;

import android.content.Context;
import android.content.Intent;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



public class CustomSwipeAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.s1, R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5};

    private LayoutInflater layoutInflater;
    private Context ctx;

    public CustomSwipeAdapter(Context ctx) {
        this.ctx = ctx;
    }





    @Override
    public int getCount() {
        return image_resources.length; //# of images
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //PageAdapter Basic Setting

        layoutInflater = layoutInflater.from(ctx);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);

        ImageView imageView = item_view.findViewById(R.id.imageViewSwipe);
        imageView.setImageResource(image_resources[position]);


        //Interaction with user

       imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if(position == 0){
                    Intent i = new Intent(ctx,ShoppingCartActivity.class);
                    ctx.startActivity(i);
                }

                if(position == 1){
                    Intent i = new Intent(ctx,FirebaseActivity.class);
                    ctx.startActivity(i);}

                if(position == 2){
                    Intent i = new Intent(ctx, JSONActivity.class);
                    ctx.startActivity(i);
                }

                if(position == 3){
                    Intent i = new Intent(ctx, MusicPlayer.class);
                    ctx.startActivity(i);
                }


                if(position == 4){
                    Intent i = new Intent(ctx, MiniGame.class);
                    ctx.startActivity(i);
                }

            }
        });

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }



}