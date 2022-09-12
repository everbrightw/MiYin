package com.xiaomi.miyin.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xiaomi.miyin.fragments.UploadedVideoGrid;
import com.xiaomi.miyin.fragments.FavoriteVideoGrid;

public class ProfileFragmentAdapter extends FragmentStateAdapter {

    public static int PROFILE_FRAGMENT_COUNT = 2;


    public ProfileFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ProfileFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ProfileFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FavoriteVideoGrid();
            default:
                return new UploadedVideoGrid();
        }
    }

    @Override
    public int getItemCount() {
        return PROFILE_FRAGMENT_COUNT;
    }
}
