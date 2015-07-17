package me.danielpan.youtubelike.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;

/**
 * Created by it-od-m-2572 on 15/6/11.
 */
public final class Utils {

    public static final int CODE_TAKE_PHOTO = 0x1001;
    public static final int CODE_PICK_PICTURE = 0x1002;
    public static final int CODE_CROP_IMAGE = 0x1003;

    private Utils() {
    }

    public static void takeAPhoto(Activity activity, int action_code, File result) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(result));
        intent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, action_code);
        } else {
            Toast.makeText(activity, "No Camera App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public static void selectPicture(Activity activity, int action_code) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        if (chooserIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(chooserIntent, action_code);
        } else {
            Toast.makeText(activity, "No Gallery App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public static void cropImage(Uri uri, Activity activity, int action_code) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, action_code);
        } else {
            Toast.makeText(activity, "No Crop App Available", Toast.LENGTH_SHORT).show();
        }
    }

    public static void cropImage(String imagePath, Activity activity, int action_code) {
        cropImage(new File(imagePath), activity, action_code);
    }

    public static void cropImage(File imageFile, Activity activity, int action_code) {
        cropImage(Uri.fromFile(imageFile), activity, action_code);
    }

    public static String getSelectedImagePathFromUri(Context context, Uri selectedImageUri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImageUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    public static void pickAndCropImageFromGallery(Activity activity, int action_code, Uri outUri) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        pickImageIntent.putExtra("crop", "true");
        pickImageIntent.putExtra("outputX", 200);
        pickImageIntent.putExtra("outputY", 200);
        pickImageIntent.putExtra("aspectX", 1);
        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        pickImageIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(pickImageIntent, action_code);
    }

    public static void takePhotoAndCrop(Activity activity, int action_code, Uri outUri) {
        Intent takePicIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        takePicIntent.putExtra("crop", "true");
        takePicIntent.putExtra("outputX", 200);
        takePicIntent.putExtra("outputY", 200);
        takePicIntent.putExtra("aspectX", 1);
        takePicIntent.putExtra("aspectY", 1);
        takePicIntent.putExtra("scale", true);
        takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        takePicIntent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        activity.startActivityForResult(takePicIntent, action_code);
    }
}
