package com.example.baocaogiuaky;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangeAccountImageFragment extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;

    private CircleImageView avatarImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_change_account_image, container, false);

        avatarImageView = view.findViewById(R.id.img_avatar_change);

        // Handle 'Cancel' button
        Button cancelButton = view.findViewById(R.id.btn_cancel_change);
        cancelButton.setOnClickListener(v -> dismiss());

        // Handle 'Upload' button
        Button uploadButton = view.findViewById(R.id.btn_upload_change);
        uploadButton.setOnClickListener(v -> openGallery());

        // Handle 'Take Photo' button
        Button takePhotoButton = view.findViewById(R.id.btn_take_photo_change);
        takePhotoButton.setOnClickListener(v -> openCamera());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setGravity(android.view.Gravity.CENTER);
        }
    }

    // Open gallery to choose an image
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Open camera to take a photo
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_REQUEST);
    }

    // Handle the result of the gallery or camera intent
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            // If it's a camera photo, get the photo from the intent's extras
            if (requestCode == TAKE_PHOTO_REQUEST) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    imageUri = (Uri) extras.get(MediaStore.EXTRA_OUTPUT);
                }
            }

            // Set the selected or taken photo into the CircleImageView
            if (imageUri != null) {
                avatarImageView.setImageURI(imageUri);
            }
        }
    }
}
