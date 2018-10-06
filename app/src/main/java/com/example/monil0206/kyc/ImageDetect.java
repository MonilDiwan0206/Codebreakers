package com.example.monil0206.kyc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.IOException;
import java.util.List;

public class ImageDetect extends AppCompatActivity {

    private ImageView mImage;
    private Button snapbutton;
    private Button detectbutton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    private TextView maintext;
    private String TAG = "Image";
    private Button gallery;
    private static int GALLERY_PICK = 1;
    private Uri imageUri;
    private String name;
    private String DOB;
    private String Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detect);


        snapbutton = findViewById(R.id.snapbutton);
        detectbutton = findViewById(R.id.detectbutton);
        maintext = findViewById(R.id.maintext);
        gallery = findViewById(R.id.gallery);
        mImage = findViewById(R.id.mImage);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),GALLERY_PICK);

            }
        });

        snapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

        detectbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detecttxt();

            }
        });
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//            mImage.setImageBitmap(imageBitmap);
//        }
//    }

    private void detecttxt(){
        FirebaseVisionImage image = null;
        try {
            image = FirebaseVisionImage.fromFilePath(this,imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            image = FirebaseVisionImage.fromFilePath(this, imageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        FirebaseVisionImage image1 = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer recognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        recognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processText(firebaseVisionText);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void processText(FirebaseVisionText text){
//        List<FirebaseVisionText.TextBlock> blockList = text.getTextBlocks();
//        if(blockList.size() == 0){
//            Toast.makeText(this, "No text in the image",
//                    Toast.LENGTH_SHORT).show();
//            return;
//        }
//        for(FirebaseVisionText.TextBlock textBlock : text.getTextBlocks()){
//            String txt = textBlock.getText();
//            maintext.setText(txt);
//            Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
//        }
        String resultText = text.getText();
//        Toast.makeText(this, resultText, Toast.LENGTH_LONG).show();
        for (FirebaseVisionText.TextBlock block: text.getTextBlocks()) {
            String blockText = block.getText();
//            if(blockText.startsWith("Add")){
//                Toast.makeText(this, blockText, Toast.LENGTH_LONG).show();
//            }

            Float blockConfidence = block.getConfidence();
            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (FirebaseVisionText.Line line: block.getLines()) {
                String lineText = line.getText();
                if(lineText.startsWith("Name")){
                    name = lineText;
                    int len = lineText.length();
                    name = name.substring(4,len);
                    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                }
                if(lineText.startsWith("DOB")){
                    DOB = lineText;
                    int len = lineText.length();
                    DOB = DOB.substring(4,len-4);
                    Toast.makeText(this, DOB, Toast.LENGTH_SHORT).show();
                }
                if(lineText.startsWith("Add")){
                    Add = lineText;
                    Toast.makeText(this, Add, Toast.LENGTH_SHORT).show();
                }

                Float lineConfidence = line.getConfidence();
                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (FirebaseVisionText.Element element: line.getElements()) {
                    String elementText = element.getText();
                    Float elementConfidence = element.getConfidence();
                    List<RecognizedLanguage> elementLanguages = element.getRecognizedLanguages();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){
            RequestOptions options = new RequestOptions().error(R.drawable.defprofile).centerCrop();
            imageUri = data.getData();
            imageUri = Uri.parse(imageUri.toString().replace("/s96-c/","/s500-c/"));
            Glide.with(this).load(imageUri).apply(options).transition(DrawableTransitionOptions.withCrossFade()).into(mImage);
        }
    }
}
