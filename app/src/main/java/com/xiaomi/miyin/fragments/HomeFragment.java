package com.xiaomi.miyin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.adapters.VideoAdapter;
import com.xiaomi.miyin.model.Video;
import com.xiaomi.miyin.test.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager2;
    List<Video> videos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = getView().findViewById(R.id.viewpager);
        //videos = new ArrayList<>();

        //Video video1 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        //        "@usertest1", "okokokok");
        //videos.add(video1);

        //Video video2 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        //        "@usertest2", "okokokokuiucthebestuiucthebestuiucthbestuiuctebtestuiucubtest");
        //videos.add(video2);

        //viewPager2.setAdapter(new VideoAdapter(videos));

        //TestUtils.testFetchVideo(viewPager2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
}
