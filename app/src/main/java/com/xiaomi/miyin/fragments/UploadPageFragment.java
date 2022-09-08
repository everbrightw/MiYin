package com.xiaomi.miyin.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.xiaomi.miyin.R;
import com.xiaomi.miyin.apis.ApiClient;
import com.xiaomi.miyin.apis.IMiVibeApi;
import com.xiaomi.miyin.test.TestUtils;
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
    private Button button, uploadVideo;
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

        //// request for camera permission
        //if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
        //    ActivityCompat.requestPermissions(getActivity(), new String[]{
        //            Manifest.permission.CAMERA
        //    }, 100);
        //}
        //// open camera
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, 100);
        if(!PermissionUtils.checkPermissionForReadExternalStorage(getActivity())){
            try {
                PermissionUtils.requestPermissionForReadExternalStorage(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        button = (Button) view.findViewById(R.id.pickVideo);
        uploadVideo = (Button) view.findViewById(R.id.uploadVideo);
        context = view.getContext();
        init(view);
    }


    void init(View view){

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (video != null){
                    //uploadFile();
                }else{
                    Toast.makeText(view.getContext(), "Please select a video", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mVideoView = (VideoView) view.findViewById(R.id.test_videoview);
        mBufferingTextView = (TextView) view.findViewById(R.id.buffering_textview);

        //if (savedInstanceState != null) {
        //    mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        //}

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(view.getContext());
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);


        button.setOnClickListener(new View.OnClickListener() {
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
                .uploadVideo(TestUtils.TOKEN, vFile);
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

    @SuppressLint("ObsoleteSdkInt")
    public String getPathFromURI(Uri uri){
        String realPath="";
// SDK < API11
        if (Build.VERSION.SDK_INT < 11) {
            String[] proj = { MediaStore.Images.Media.DATA };
            @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
            int column_index = 0;
            String result="";
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                realPath=cursor.getString(column_index);
            }
        }
        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19){
            String[] proj = { MediaStore.Images.Media.DATA };
            CursorLoader cursorLoader = new CursorLoader(getActivity(), uri, proj, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if(cursor != null){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                realPath = cursor.getString(column_index);
            }
        }
        // SDK > 19 (Android 4.4)
        else{
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            String[] column = { MediaStore.Images.Media.DATA };
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{ id }, null);
            int columnIndex = 0;
            if (cursor != null) {
                columnIndex = cursor.getColumnIndex(column[0]);
                if (cursor.moveToFirst()) {
                    realPath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        }
        return realPath;
    }

}
