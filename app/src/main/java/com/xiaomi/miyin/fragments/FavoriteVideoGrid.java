package com.xiaomi.miyin.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaomi.miyin.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteVideoGrid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteVideoGrid extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("YW_TEST", "i am inflated 2");

        return inflater.inflate(R.layout.fragment_profile_favorite, container, false);
    }
}