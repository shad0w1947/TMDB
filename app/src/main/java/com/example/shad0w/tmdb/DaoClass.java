package com.example.shad0w.tmdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DaoClass {

    @Insert
    void addItem(DatabaseTable databaseTable);


    @Delete
    void deleteItem(DatabaseTable databaseTable);


    @Query("select * from DatabaseTable")
    List<DatabaseTable> getAll();

    @Query("select id from DatabaseTable where type=:type AND typeId=:typeId")
    Long getId(String type,String typeId);

    @Query("select typeId from DatabaseTable where id=:id1")
    Long getTypeId(Long id1);

    @Query("select type from DatabaseTable where id=:id1")
    int getType(Long id1);

}
