package com.xiaomi.miyin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaomi.miyin.R;

import java.util.List;

public class ProfileGridViewAdapter extends RecyclerView.Adapter<ProfileGridViewAdapter.UploadedVideoGridViewHolder> {

    List<String> titles;
    List<Integer> images;
    Context context;

    public ProfileGridViewAdapter(Context context, List<String> titles, List<Integer> images){
        this.context = context;
        this.titles = titles;
        this.images = images;
        Log.i("YW_TEST", "gridview adapter constructed");
    }

    @NonNull
    @Override
    public UploadedVideoGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.container_grid_item, parent, false);
        Log.i("YW_TEST", "grid layout inflated");
        return new UploadedVideoGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadedVideoGridViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class UploadedVideoGridViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView gridIcon;

        public UploadedVideoGridViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.upload_preview_info_fav_count);
            gridIcon = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // upload view onclicked
                    Log.i("YW_TEST", "clicked on uploaded video: " + getAdapterPosition());
                    Toast.makeText(view.getContext(), "clicked on: " + getAdapterPosition(), Toast.LENGTH_SHORT);
                }
            });
        }
    }

}
