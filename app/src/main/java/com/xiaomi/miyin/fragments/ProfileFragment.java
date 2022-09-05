package com.xiaomi.miyin.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.xiaomi.miyin.activities.LoginActivity;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.adapters.ProfileFragmentAdapter;

public class ProfileFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    // user avatar
    ImageView avatar;

    ProfileFragmentAdapter profileFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.profile_tab_layout);
        viewPager2 = view.findViewById(R.id.profile_viewpager);
        avatar = view.findViewById(R.id.profile_avatar);

        profileFragmentAdapter = new ProfileFragmentAdapter(this);
        viewPager2.setAdapter(profileFragmentAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // change the highlight when we swiped the viewpager
                tabLayout.getTabAt(position).select();
            }
        });

        // ======== test opening log in============
        testLoginPage(avatar);
    }

    void testLoginPage(ImageView avatar){
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("YW_TEST", "avatar clicked");
                // open a new activity
                openLoginActivity();
            }
        });
    }
    void openLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
