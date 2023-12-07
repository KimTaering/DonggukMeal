package com.example.donggukmeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class ListFragment extends Fragment implements MealAdapter.OnItemClickListener {

    private RecyclerView listRecyclerView;
    private MealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        listRecyclerView = rootView.findViewById(R.id.mealRecyclerView);

        MealDB mealDB = Room.databaseBuilder(requireActivity().getApplicationContext(), MealDB.class, "production")
                .allowMainThreadQueries()
                .build();
        List<Meal> meals = mealDB.mealDao().getAllMeals();

        listRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MealAdapter(meals, this);
        listRecyclerView.setAdapter(adapter);


        return rootView;
    }

    @Override
    public void onItemClick(Meal meal) {
        Intent intent = new Intent(requireContext(), ShowMealDetailActivity.class);
        intent.putExtra("date", meal.getDate());
        intent.putExtra("time", meal.getTime());
        intent.putExtra("type", meal.getType());
        intent.putExtra("place", meal.getPlace());
        intent.putExtra("menu", meal.getMenu());
        intent.putExtra("cost", meal.getCost());
        intent.putExtra("review", meal.getReview());
        intent.putExtra("calorie", meal.getCalorie());
        // Assuming you have a method to convert byte[] to Bitmap
        intent.putExtra("image", meal.getImage());
        startActivity(intent);
    }


}
