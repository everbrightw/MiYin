package com.xiaomi.miyin.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.xiaomi.miyin.R;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.controllers.UserManager;
import com.xiaomi.miyin.model.LoadingView;
import com.xiaomi.miyin.model.Video;
import com.xiaomi.miyin.apis.ServiceCall;
import com.xiaomi.miyin.test.PathUtils;
import com.xiaomi.miyin.utils.PermissionUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager2;
    List<Video> videos;
    View likeBtn;
    ProgressBar progressBar;

    MaterialButton button;
    LoadingView loadingView;

    ImageView uploadBtn;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
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
        uploadBtn = getView().findViewById(R.id.upload_video_btn);
        button = getView().findViewById(R.id.refresh_btn);
        loadingView =  new LoadingView(progressBar, off, hint, button);
        // =========request for camera permission=============
        if(!PermissionUtils.checkPermissionForReadExternalStorage(getActivity())){
            try {
                PermissionUtils.requestPermissionForReadExternalStorage(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                Log.i("YW_TEST", "button clciekd");
                pickVideoIntent.setType("video/*");
                someActivityResultLauncher.launch(pickVideoIntent);
            }
        });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        Log.i("YW_TEST", "okokk" + "\n=======uri" + uri + " path: " + "/storage/emulated/0/DCIM/ScreenRecorder/" + getNameFromContentUri(getActivity(), uri));
                        uploadFile(uri);

                    }
                    Log.i("YW_TEST", "okokk" + result.getResultCode());
                }
            });


    // todo refactor this =
    public void uploadFile(Uri uri){
        //String test = "/storage/emulated/0/DCIM/ScreenRecorder/" + getNameFromContentUri(getActivity(), uri);
        Log.i("PATH", PathUtils.getPath(context, uri));
        String absPath = PathUtils.getPath(context, uri);

        final File file = new File(absPath);
        MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, file);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<ResponseBody> responseBodyCall = ApiClient.getVideoTestClient().create(IMiVibeApi.class)
                .uploadVideo(UserManager.getToken(context), vFile);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody rb = response.body();
                Log.i("YW_TEST","code" + response.code());
                if(response.isSuccessful()){
                    Toast.makeText(context, "Upload Video Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("YW_TEST", "failed connect to server: " + t.getMessage());
            }
        });
    }

    public static String getNameFromContentUri(Context context, Uri contentUri) {
        Cursor returnCursor = context.getContentResolver().query(contentUri, null, null, null, null);
        int nameColumnIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameColumnIndex);
        return fileName;


    }

}
