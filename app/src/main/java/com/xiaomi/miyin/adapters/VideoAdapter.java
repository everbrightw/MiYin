package com.xiaomi.miyin.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.controllers.UserManager;
import com.xiaomi.miyin.model.ResponseStatus;
import com.xiaomi.miyin.model.Video;
import com.xiaomi.miyin.apis.ServiceCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        ImageView avatar;
        long videoID;
        boolean isFavorite = false;

        ImageView likeBtn;
        // TODO: this one need to dynamically initialized
        boolean liked = false;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.video_view);
            title = itemView.findViewById(R.id.text_video_title);
            description = itemView.findViewById(R.id.text_description);
            progressBar = itemView.findViewById(R.id.videoProgressBar);
            likeBtn = itemView.findViewById(R.id.like_button);
            avatar = itemView.findViewById(R.id.profile_avatar);

            Picasso.get().load("https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1760&q=80").into(avatar);

            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isFavorite){
                        Toast.makeText(itemView.getContext(), "Liked a video", Toast.LENGTH_SHORT).show();
                        likeVideo(itemView.getContext());
                    } else {
                        Toast.makeText(itemView.getContext(), "un-liked a video", Toast.LENGTH_SHORT).show();
                        unLikeVideo(itemView.getContext());
                    }
                    liked = !liked;
                }
            });

        }


        @SuppressLint("UseCompatLoadingForDrawables")
        void likeVideo(Context context){
            likeBtn.setImageDrawable(context.getDrawable(R.drawable.ic_solid_heart));
            postRequest(context, ServiceCall.LIKE);
        }

        void postRequest(Context context, String like){
            IMiVibeApi api = ApiClient.getVideoTestClient().create(IMiVibeApi.class);
            Call<ResponseStatus> call = api.likeAVideo(UserManager.getToken(context), ""+videoID,like);
            call.enqueue(new Callback<ResponseStatus>() {
                @Override
                public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                    if(!response.isSuccessful()){
                        Log.i("FAV_VIDEO", "failed: " + response.code());
                        return;
                    }

                    // update the favotie cache
                    isFavorite = like == ServiceCall.LIKE;

                    Log.i("FAV_VIDEO", "success favortie: " + response.body().getStatusMsg());
                }

                @Override
                public void onFailure(Call<ResponseStatus> call, Throwable t) {
                    Log.i("FAV_VIDEO", t.getMessage());
                }
            });
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        void unLikeVideo(Context context){
            likeBtn.setImageDrawable(context.getDrawable(R.drawable.ic_heart));
            postRequest(context, ServiceCall.UNLIKE);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
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

            videoID = video.getVideoID();
            isFavorite = video.isFavorite();

            likeBtn.setImageDrawable(!isFavorite ? itemView.getContext().getDrawable(R.drawable.ic_heart) :
                    itemView.getContext().getDrawable(R.drawable.ic_solid_heart));
        }
    }

}