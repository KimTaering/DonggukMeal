package com.example.donggukmeal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<Meal> meals;
    private OnItemClickListener onItemClickListener;

    public MealAdapter(List<Meal> meals, OnItemClickListener onItemClickListener) {
        this.meals = meals;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Meal meal);
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal currentMeal = meals.get(position);

        holder.mealName.setText(currentMeal.getMenu());
        holder.mealDate.setText(currentMeal.getDate());
        holder.mealPlace.setText(currentMeal.getPlace());

        // Check for null or empty image data
        byte[] imageData = currentMeal.getImage();
        if (imageData != null && imageData.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            holder.mealImage.setImageBitmap(bitmap);
        } else {
            // Set a placeholder image or hide the ImageView if there's no image data
            holder.mealImage.setImageResource(R.drawable.default_img);
            // To hide the ImageView: holder.mealImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(currentMeal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;
        TextView mealDate;
        TextView mealPlace;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.mealImage);
            mealName = itemView.findViewById(R.id.mealName);
            mealDate = itemView.findViewById(R.id.mealDate);
            mealPlace = itemView.findViewById(R.id.mealPlace);
        }
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}
