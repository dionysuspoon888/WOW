package com.example.wow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Define the adapter
public class JsonRecyclerViewAdapter extends RecyclerView.Adapter<JsonRecyclerViewAdapter.ViewHolder> {

    private ArrayList<JSONItem> list;
    private OnItemClickListener listener;

    private Context ctx;

    //OnClick UI
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listeners) {
        listener = listeners;
    }

    public JsonRecyclerViewAdapter(Context context, ArrayList<JSONItem> lists) {
        ctx = context;
        list = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create ViewHolder when not enough
        View v = LayoutInflater.from(ctx).inflate(R.layout.json_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Define the data shows in the card View
        JSONItem currentItem = list.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likeCount = currentItem.getLikeCount();
        int viewCount = currentItem.getViewCount();

        holder.textViewCreator.setText(creatorName);
        holder.textViewLikes.setText("Likes: " + likeCount);
        holder.textView.setText("Views: "+viewCount);

        Picasso.with(ctx).load(imageUrl).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder set up(mandatory for RecyclerView)
        public ImageView imageView;
        public TextView textViewCreator;
        public TextView textViewLikes;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewCreator = itemView.findViewById(R.id.text_view_creator);
            textViewLikes = itemView.findViewById(R.id.text_view_likes);
            textView=itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}