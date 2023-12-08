package com.example.donggukmeal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Meal {

//    public Meal(String date, String time, String type, String place, String menu, String cost, String review, int calorie, int image) {
//        this.date = date;
//        this.time = time;
//        this.type = type;
//        this.place = place;
//        this.menu = menu;
//        this.cost = cost;
//        this.review = review;
//        this.calorie = calorie;
//        this.image = image;
//    }



    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "place")
    public String place;

    @ColumnInfo(name = "menu")
    public String menu;

    @ColumnInfo(name = "cost")
    public int cost;

    @ColumnInfo(name = "review")
    public String review;

    @ColumnInfo(name = "calorie")
    public int calorie;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] image;



    public int getID() {
        return ID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getPlace() {
        return place;
    }

    public String getMenu() {
        return menu;
    }

    public int getCost() {
        return cost;
    }

    public String getReview() {
        return review;
    }

    public int getCalorie() {
        return calorie;
    }

    public byte[] getImage() {
        return image != null ? image : new byte[0]; // 또는 null 대신 다른 기본값을 반환
    }

}