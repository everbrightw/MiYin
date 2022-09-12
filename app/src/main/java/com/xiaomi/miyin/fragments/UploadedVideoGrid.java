package com.xiaomi.miyin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.adapters.ProfileGridViewAdapter;
import com.xiaomi.miyin.apis.ServiceCall;

import java.util.ArrayList;
import java.util.List;

public class UploadedVideoGrid extends Fragment {

    RecyclerView recyclerView;
    List<String> titles;
    List<String> images;
    ProfileGridViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("YW_TEST", "i am inflated 1");
        //TestUtils.testApi();
        //TestUtils.testRegister();
        return inflater.inflate(R.layout.fragment_profile_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.upload_list);

        titles = new ArrayList<>();
        images = new ArrayList<>();
        adapter = new ProfileGridViewAdapter(getContext(), titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                3,
                GridLayoutManager.VERTICAL,
                false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // ******* API request for retrieving user uploaded video *******
        ServiceCall.retrieveUserPublishedVideos(getContext(), recyclerView);
        //recyclerView.setAdapter(adapter);
    }
}