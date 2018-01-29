package com.example.wow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class FirebaseImageAdapter extends RecyclerView.Adapter<FirebaseImageAdapter.ImageViewHolder> {
    private Context ctx;
    private List<FirebaseUploadItem> uploads;

    //Define Custom Click Listener
    private OnItemClickListener itemListener;

    public FirebaseImageAdapter(Context context, List<FirebaseUploadItem> uploads) {
        ctx = context;
        this.uploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Define the Created View Holder
        View v = LayoutInflater.from(ctx).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        //Set up Card View
        FirebaseUploadItem uploadCurrent = uploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(ctx)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher) //Default Image when loading
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    //Holds the View
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,

            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        //View we define
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            //Set up Listener in itemView
            itemView.setOnClickListener(this); //short click
            itemView.setOnCreateContextMenuListener(this); //long click =menu with method
        }

        //short Click event
        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) { //Make sure Valid
                    itemListener.getIDHint(position);
                }
            }
        }
        //long click menu event

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1, "Get Zoom!");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete it from DB!");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }
        //long click menu event in the MenuItem
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (itemListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    //Set up the method
                    switch (item.getItemId()) {
                        case 1:
                            itemListener.getZoom(position);
                            return true;
                        case 2:
                            itemListener.deleteItem(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }

    //Define Custom Click Listener Interface
    public interface OnItemClickListener {
        //Custom Method
        void getIDHint(int position);

        void getZoom(int position);

        void deleteItem(int position);
    }

    //Define Custom Click Listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }
}