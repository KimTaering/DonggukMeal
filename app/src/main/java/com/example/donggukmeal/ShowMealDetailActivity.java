package com.example.donggukmeal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.donggukmeal.databinding.ActivityShowMealBinding;

public class ShowMealDetailActivity extends AppCompatActivity {

    private ActivityShowMealBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowMealBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null) {
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");
            String type = intent.getStringExtra("type");
            String place = intent.getStringExtra("place");
            String menu = intent.getStringExtra("menu");
            String cost = intent.getStringExtra("cost");
            String review = intent.getStringExtra("review");
            int calorie = intent.getIntExtra("calorie", 0);
            byte[] imageData = intent.getByteArrayExtra("image");

            displayMealDetails(date, time, type, place, menu, cost, review, calorie, imageData);
        } else {
            Log.e("ShowMealDetailActivity", "Intent is null");
            // Handle the case where the intent is null
        }
    }

    private void displayMealDetails(String date, String time, String type, String place,
                                    String menu, String cost, String review, int calorie, byte[] imageData) {
        // Find the corresponding TextView elements in your layout
        TextView showDate = binding.showDate;
        TextView showTime = binding.showTime;
        TextView showType = binding.showType;
        TextView showPlace = binding.showPlace;
        TextView showMenu = binding.showMenu;
        TextView showCost = binding.showCost;
        TextView showCalorie = binding.showCalorie;
        TextView showReview = binding.showReview;

        // Set the text values based on the meal details
        showDate.setText(date);
        showTime.setText(time);
        showType.setText(type);
        showPlace.setText(place);
        showMenu.setText(menu);
        showCost.setText(cost);
        showCalorie.setText(String.valueOf(calorie)); // Convert int to String
        showReview.setText(review);

        ImageView showImage = binding.showImage; // Get the ImageView reference



        if (imageData != null && imageData.length > 0) {
            // 이미지 데이터가 있는 경우 처리
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            showImage.setImageBitmap(bitmap);
        } else {
            // 이미지 데이터가 없는 경우 처리
            showImage.setImageResource(R.drawable.default_img);
            // 또는 이미지를 숨길 경우: showImage.setVisibility(View.GONE);
        }

    }


}


