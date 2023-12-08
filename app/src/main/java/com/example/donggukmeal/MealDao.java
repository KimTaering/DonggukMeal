package com.example.donggukmeal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealDao {
    @Query("select * from Meal")
    List<Meal> getAllMeals();

    @Query("select * from Meal where ID=:id")
    Meal getMealByID(int id);


    @Insert
    void insertAll(Meal... foodList);

    @Delete
    void deleteAllMeals(Meal... foodList);

    @Query("SELECT SUM(calorie) FROM Meal WHERE SUBSTR(date, 5, 2) = :selectedMonth")
    int getCalorieSumInSpecificMonth(String selectedMonth);

    @Query("SELECT SUM(cost) FROM Meal WHERE SUBSTR(date, 5, 2) = :selectedMonth and type = :selectedType")
    int getCostSumInMonthType(String selectedMonth, String selectedType);

    @Query("SELECT SUM(cost) FROM Meal WHERE SUBSTR(date, 5, 2) = :selectedMonth")
    int getCostSumInSpecificMonth(String selectedMonth);


    @Query("SELECT * FROM Meal ORDER BY date DESC LIMIT 1 OFFSET :n-1")
    Meal getNthLatestData(int n);

}
