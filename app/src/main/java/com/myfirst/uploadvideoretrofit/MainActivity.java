package com.myfirst.uploadvideoretrofit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private Button mBtnGallery;
    private Button mBtnUpload;
    private String videoPath;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiViews();
        mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
    }

    private void intiViews() {
        mVideoView = findViewById(R.id.videoView);
        mBtnGallery = findViewById(R.id.btnGallery);
        mBtnUpload = findViewById(R.id.btnUpload);
        mBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted())
                    openGallery();
                else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideoToApi();
            }
        });
    }

    private void uploadVideoToApi(){
        ApiService apiService = Network.getInstance().create(ApiService.class);
        File file = new File(videoPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("video/*"),file);
        MultipartBody.Part multiBody = MultipartBody.Part.createFormData("video",file.getName(),requestBody);
        apiService.uploadVideo(multiBody,"Emulator Video").enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseDTO> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions,
                                           @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openGallery();
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        launchGallery.launch(intent);
    }

    private ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Uri selectedVideoUri = result.getData().getData();
                        mVideoView.setMediaController(mediaController);
                        mVideoView.setVideoURI(selectedVideoUri);
                        getVideoPathFromUri(selectedVideoUri);
                    }
                }
            }
    );

    @NotNull
    private Cursor getVideoPathFromUri(Uri selectedVideo) {
        String[] filePath = {MediaStore.Video.Media.DATA};
        Cursor c = getContentResolver().query(selectedVideo, filePath,
                null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        videoPath = c.getString(columnIndex);
        return c;
    }
}