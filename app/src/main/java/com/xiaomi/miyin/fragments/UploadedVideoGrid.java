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

import java.util.ArrayList;
import java.util.List;

public class UploadedVideoGrid extends Fragment {

    RecyclerView recyclerView;
    List<String> titles;
    List<Integer> images;
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

        titles.add("123");
        titles.add("456");
        titles.add("789");
        titles.add("102");
        titles.add("333");
        titles.add("333");
        titles.add("333");
        titles.add("333");
        titles.add("333");
        titles.add("333");
        titles.add("333");

        images.add(R.drawable.test_pic1);
        images.add(R.drawable.test_pic2);
        images.add(R.drawable.test_pic3);
        images.add(R.drawable.test_pic4);
        images.add(R.drawable.test5);
        images.add(R.drawable.test6);
        images.add(R.drawable.test7);
        images.add(R.drawable.test11);
        images.add(R.drawable.test9);
        images.add(R.drawable.test10);
        images.add(R.drawable.test11);

        adapter = new ProfileGridViewAdapter(getContext(), titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                3,
                GridLayoutManager.VERTICAL,
                false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}