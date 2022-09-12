package com.xiaomi.miyin.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.controllers.UserManager;
import com.xiaomi.miyin.utils.PermissionUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPageFragment extends Fragment {

    ImageView imageView;
    private Button pickVideo;
    public static final int REQUEST_PICK_VIDEO = 3;
    public ProgressDialog pDialog;
    private VideoView mVideoView;
    private TextView mBufferingTextView;
    private Uri video;
    private String videoPath;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("YW_TEST","upload page fragment created");


        // ======== API TEST =========
        //TestUtils.testApi();
        return inflater.inflate(R.layout.upload_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //imageView = view.findViewById(R.id.camera_image_view);

        // =========request for camera permission=============
        if(!PermissionUtils.checkPermissionForReadExternalStorage(getActivity())){
            try {
                PermissionUtils.requestPermissionForReadExternalStorage(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pickVideo = (Button) view.findViewById(R.id.pickVideo);
        context = view.getContext();
        init(view);
    }


    void init(View view){

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(view.getContext());
        controller.setMediaPlayer(mVideoView);

        pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                Log.i("YW_TEST", "button clciekd");
                pickVideoIntent.setType("video/*");
                someActivityResultLauncher.launch(pickVideoIntent);
            }
        });

        initDialog(view);
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

    protected void initDialog(View view) {
        pDialog = new ProgressDialog(view.getContext());
        pDialog.setMessage("uploading");
        pDialog.setCancelable(true);
    }

    private void uploadFile(Uri uri){
        String test = "/storage/emulated/0/DCIM/ScreenRecorder/" + getNameFromContentUri(getActivity(), uri);
        final File file = new File(test);
        MediaType MEDIA_TYPE = MediaType.parse("video/mp4");
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, file);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        Call<ResponseBody> responseBodyCall = ApiClient.getVideoTestClient().create(IMiVibeApi.class)
                .uploadVideo(UserManager.getToken(context), vFile);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("YW_TEST","S");
                ResponseBody rb = response.body();
                Log.i("YW_TEST","code" + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("YW_TEST", "failed connect to server: " + t.getMessage());
            }
        });
    }

    public static String getNameFromContentUri(Context context, Uri contentUri){
        Cursor returnCursor = context.getContentResolver().query(contentUri, null, null, null, null);
        int nameColumnIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String fileName = returnCursor.getString(nameColumnIndex);
        return fileName;
    }

}
