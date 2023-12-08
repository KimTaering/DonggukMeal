package com.example.donggukmeal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InputMealActivity extends AppCompatActivity {
    private static final String TAG = "Input Meal";
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private ImageButton chooseImageButton;
    private Button inputButton;
    private EditText etDate;
    private EditText etTime;
    private EditText etMenu;
    private EditText etCost;
    private EditText etReview;
    private String typeName;
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal);

        try {
            initializeViews();
            setupSpinners();
            setupImagePicker();
            setupInputButton();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception in onCreate: " + e.getMessage());
        }
    }

    private void initializeViews() {
        imageView = findViewById(R.id.inputImageView);
        chooseImageButton = findViewById(R.id.getImageButton);
        etDate = findViewById(R.id.input_date);
        etTime = findViewById(R.id.input_time);
        etMenu = findViewById(R.id.input_menu_value);
        etCost = findViewById(R.id.input_costs_value);
        etReview = findViewById(R.id.input_review_value);
        inputButton = findViewById(R.id.input_button);
    }

    private void setupSpinners() {
        String[] mainTypeItems = {"조식", "중식", "석식", "음료"};
        ArrayAdapter<String> mainTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mainTypeItems);
        mainTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner mainTypeSpinner = findViewById(R.id.main_type_spinner);
        mainTypeSpinner.setAdapter(mainTypeAdapter);

        List<List<String>> subPlaceItems = new ArrayList<>();
        subPlaceItems.add(Arrays.asList("기숙사 식당", "상록원 1층", "상록원 2층"));
        subPlaceItems.add(Arrays.asList("기숙사", "블루포트", "가온누리"));

        ArrayAdapter<String> initialSubPlaceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subPlaceItems.get(0));
        initialSubPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner subPlaceSpinner = findViewById(R.id.sub_place_spinner);
        subPlaceSpinner.setAdapter(initialSubPlaceAdapter);

        mainTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                typeName = parentView.getItemAtPosition(position).toString();

                ArrayAdapter<String> adapter;
                if ("음료".equals(typeName)) {
                    adapter = new ArrayAdapter<>(InputMealActivity.this, android.R.layout.simple_spinner_item, subPlaceItems.get(1));
                } else {
                    adapter = new ArrayAdapter<>(InputMealActivity.this, android.R.layout.simple_spinner_item, subPlaceItems.get(0));
                }

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subPlaceSpinner.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        subPlaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                placeName = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void setupImagePicker() {
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void setupInputButton() {
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    handleInputButtonClick();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleInputButtonClick() {
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String menu = etMenu.getText().toString();
        String cost = etCost.getText().toString();
        String review = etReview.getText().toString();

        Meal new_meal = new Meal();

        new_meal.date = date;
        new_meal.time = time;
        new_meal.type = typeName;
        new_meal.place = placeName;
        new_meal.menu = menu;
        new_meal.cost = Integer.parseInt(cost);
        new_meal.review = review;

        if (imageView.getDrawable() != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            new_meal.image = byteArray;
        }

        Random random = new Random();
        new_meal.calorie = (typeName.equals("음료")) ? random.nextInt(150 - 30 + 1) + 30 : random.nextInt(750 - 550 + 1) + 550;

        MealDB mealDB = Room.databaseBuilder(getApplicationContext(), MealDB.class, "production")
                .allowMainThreadQueries()
                .build();

        mealDB.mealDao().insertAll(new_meal);

        showAlertDialog("입력 완료", "확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // "확인" 버튼을 눌렀을 때 ListFragment로 이동
                Intent intent = new Intent(InputMealActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    private void showAlertDialog(String message, String positiveButtonText, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(InputMealActivity.this);
        builder.setMessage(message)
                .setPositiveButton(positiveButtonText, positiveClickListener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), selectedImageUri));

                // Ensure that imageView is properly initialized
                if (imageView != null) {
                    // Set the bitmap directly to the ImageView
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "ImageView is not initialized");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error decoding bitmap: " + e.getMessage());
            }
        }
    }


}
