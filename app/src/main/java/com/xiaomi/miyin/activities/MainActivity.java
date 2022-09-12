package com.xiaomi.miyin.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.fragments.HomeFragment;
import com.xiaomi.miyin.fragments.ProfileFragment;
import com.xiaomi.miyin.apis.ServiceCall;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        //viewPager2 = findViewById(R.id.viewpager);
        //videos = new ArrayList<>();

        //Video video1 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        //        "test1", "okokokok");
        //videos.add(video1);

        //Video video2 = new Video("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        //        "test2", "okokokok");
        //videos.add(video2);

        //viewPager2.setAdapter(new VideoAdapter(videos));

        // default fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new HomeFragment())
                .commit();
    }

    // switch bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // fragment transition
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        //case R.id.nav_upload:
                        //    selectedFragment = new UploadPageFragment();
                        //    //set up transition animation
                        //    transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                        //    transaction.addToBackStack(null);
                        //    break;
                        case R.id.nav_info:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    transaction.replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

}