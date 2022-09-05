package com.xiaomi.miyin.adapters;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.model.Video;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder>{

    public static String TAG = "VideoAdapter";

    List<Video> videos;

    public VideoAdapter(List<Video> videos){
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_video, parent, false);
        Log.i(TAG," onCreate");
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        holder.setData(videos.get(position));
        Log.i(TAG,"onBind");
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder{

        VideoView videoView;
        TextView title, description;
        ProgressBar progressBar;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.video_view);
            title = itemView.findViewById(R.id.text_video_title);
            description = itemView.findViewById(R.id.text_description);
            progressBar = itemView.findViewById(R.id.videoProgressBar);

        }

        void setData(Video video){
            videoView.setVideoPath(video.getUrl());
            title.setText(video.getTitle());
            description.setText(video.getDescription());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(TAG," onPrepared");
                    progressBar.setVisibility(View.GONE);
                    mp.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.i(TAG," onCompletion");
                    mp.start();
                }
            });

        }
    }

}