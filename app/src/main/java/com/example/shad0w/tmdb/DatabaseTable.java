package com.example.shad0w.tmdb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DatabaseTable {

    @PrimaryKey(autoGenerate = true)
  private   Long id;

    Long typeId;

    int type;
    DatabaseTable(){

    }

    DatabaseTable(Long typeid, int type){

        this.typeId = typeid;
        this.type = type;
    }

    public Long getId() {
        return id;
    }


    public int getType() {
        return type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(int type) {
        this.type = type;
    }

}
