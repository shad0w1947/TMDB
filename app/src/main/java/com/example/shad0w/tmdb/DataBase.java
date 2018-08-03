package com.example.shad0w.tmdb;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DatabaseTable.class},version = 1)
public abstract class DataBase extends RoomDatabase{

abstract DaoClass getDaoClass();

}
