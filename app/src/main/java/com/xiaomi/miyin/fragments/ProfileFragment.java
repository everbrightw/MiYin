package com.xiaomi.miyin.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.xiaomi.miyin.activities.LoginActivity;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.adapters.ProfileFragmentAdapter;
import com.xiaomi.miyin.apis.ServiceCall;
import com.xiaomi.miyin.controllers.UserManager;

public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfilePage";
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

        Log.i(" YW_TEST", "onCreated");

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
        setUpAvatarClickListeners(avatar);
        //if(!UserManager.getUserName(getContext()).equals("")){
        TextView textView = view.findViewById(R.id.profile_name);
        if(UserManager.isLoggedIn(getContext())){
            textView.setText(UserManager.getUserName(getContext()));
            Log.i(TAG, "username: " + UserManager.getUserName(getContext()));
            // disable the logged in page when signed in
            //avatar.setClickable(false);
        } else {
            textView.setText("点击头像登陆");
            //avatar.setClickable(true);
        }

        ServiceCall.fetchUserImage(getContext(), avatar);

    }

    void setUpAvatarClickListeners(ImageView avatar){
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("YW_TEST", "avatar clicked");
                // open a new activity
                if(UserManager.isLoggedIn(getContext())){
                    showSignOutConfirmDialog(getContext());
                } else {
                    openLoginActivity();
                }
            }
        });
    }

    public void refreshPage(){
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }

    public void showSignOutConfirmDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage("想要退出登录吗")
                .setCancelable(false)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserManager.signOut(context);
                        Toast.makeText(context, "user signed out", Toast.LENGTH_SHORT).show();
                        refreshPage();
                    }
                })
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    void openLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
