package com.example.donggukmeal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Meal.class}, version = 1)
public abstract class MealDB extends RoomDatabase {
    private static MealDB instance;
    public abstract MealDao mealDao();

    public static synchronized MealDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MealDB.class,
                    "meal_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public static MealDB getAppDatabase(Context context){
        if (instance==null)
            instance= Room.databaseBuilder(context, MealDB.class, "myAppDB").allowMainThreadQueries().build();
        return instance;
    }

}
