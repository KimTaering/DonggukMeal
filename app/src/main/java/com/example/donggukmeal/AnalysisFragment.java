package com.example.donggukmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class AnalysisFragment extends Fragment {

    private String selectedMonth = "12";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_analysis, container, false);

        MealDB mealDB = Room.databaseBuilder(requireActivity().getApplicationContext(), MealDB.class, "production")
                .allowMainThreadQueries()
                .build();

        TextView totalCalorie = rootView.findViewById(R.id.monthlyCalorie);
        TextView totalCost = rootView.findViewById(R.id.monthlyCost);
        TextView breakfastCost = rootView.findViewById(R.id.breakfast);
        TextView lunchCost = rootView.findViewById(R.id.lunch);
        TextView dinnerCost = rootView.findViewById(R.id.dinner);
        TextView drinkCost = rootView.findViewById(R.id.drink);

        int monthlyCal = mealDB.mealDao().getCalorieSumInSpecificMonth(selectedMonth);
        int monthlyCost = mealDB.mealDao().getCostSumInSpecificMonth(selectedMonth);
        int breakfast = mealDB.mealDao().getCostSumInMonthType(selectedMonth, "조식");
        int lunch = mealDB.mealDao().getCostSumInMonthType(selectedMonth, "중식");
        int dinner = mealDB.mealDao().getCostSumInMonthType(selectedMonth, "석식");
        int drink = mealDB.mealDao().getCostSumInMonthType(selectedMonth, "음료");

        totalCalorie.setText(String.valueOf(monthlyCal));
        totalCost.setText(String.valueOf(monthlyCost));
        breakfastCost.setText(String.valueOf(breakfast));
        lunchCost.setText(String.valueOf(lunch));
        dinnerCost.setText(String.valueOf(dinner));
        drinkCost.setText(String.valueOf(drink));

        return rootView;
    }
}
