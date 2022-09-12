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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteVideoGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteVideoGrid extends Fragment {

    RecyclerView recyclerView;
    List<String> titles;
    List<String> images;
    ProfileGridViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("YW_TEST", "i am inflated 2");

        return inflater.inflate(R.layout.fragment_profile_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.upload_list);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        //titles.add("123");
        //titles.add("456");
        //titles.add("789");
        //titles.add("102");
        //titles.add("333");
        //titles.add("333");
        //titles.add("333");
        //titles.add("333");
        //titles.add("333");
        //titles.add("333");
        //titles.add("333");

        //String testImage = "https://images.unsplash.com/photo-1597431824273-fd1ec691a0e7?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=800&ixid=MnwxfDB8MXxyYW5kb218MHx8fHx8fHx8MTY2Mjk1NjcwMg&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1900https://images.unsplash.com/photo-1597431824273-fd1ec691a0e7?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=800&ixid=MnwxfDB8MXxyYW5kb218MHx8fHx8fHx8MTY2Mjk1NjcwMg&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=1900I";
        ////images.add(R.drawable.test_pic1);
        ////images.add(R.drawable.test_pic2);
        ////images.add(R.drawable.test_pic3);
        ////images.add(R.drawable.test_pic4);
        ////images.add(R.drawable.test5);
        ////images.add(R.drawable.test6);
        ////images.add(R.drawable.test7);
        ////images.add(R.drawable.test11);
        ////images.add(R.drawable.test9);
        ////images.add(R.drawable.test10);
        ////images.add(R.drawable.test11);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);
        //images.add(testImage);

        adapter = new ProfileGridViewAdapter(getContext(), titles, images);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                3,
                GridLayoutManager.VERTICAL,
                false
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // ******* API request for retrieving user uploaded video *******
        ServiceCall.retrieveUserLikedVideos(getContext(), recyclerView);
        //recyclerView.setAdapter(adapter);
    }
}