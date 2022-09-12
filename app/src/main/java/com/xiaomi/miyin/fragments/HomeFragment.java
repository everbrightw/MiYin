package com.xiaomi.miyin.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.model.LoadingView;
import com.xiaomi.miyin.model.Video;
import com.xiaomi.miyin.apis.ServiceCall;

import java.util.List;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager2;
    List<Video> videos;
    View likeBtn;
    ProgressBar progressBar;

    MaterialButton button;
    LoadingView loadingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        //=========fake testing==========
       // viewPager2.setAdapter(new VideoAdapter(TestUtils.generateFakeVideos()));

        // fetch videos from the server
        ServiceCall.retrieveFeedVideos(getParentFragmentManager(),viewPager2, progressBar, loadingView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("HomeFragment", "fresh");
                ServiceCall.retrieveFeedVideos(getParentFragmentManager(),viewPager2, progressBar, loadingView);
                loadingView.hideOfflineInfo();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    void init(){
        viewPager2 = getView().findViewById(R.id.viewpager);

        // init loading offline view
        progressBar = getView().findViewById(R.id.main_progressbar);
        ImageView off = getView().findViewById(R.id.wifi_off_icon);
        TextView hint = getView().findViewById(R.id.no_service_text);
        button = getView().findViewById(R.id.refresh_btn);
        loadingView =  new LoadingView(progressBar, off, hint, button);

    }

}
