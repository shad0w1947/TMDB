package com.example.shad0w.tmdb;

import java.util.HashMap;

 public class GenreHas {

     HashMap<Long,String> map;
public GenreHas(){
    map=new HashMap<>();

        map.put(10759L, "Action & Adventure");
        map.put(18L, "Drama");
        map.put(10751L, "Family");
        map.put(10762L, "Kids");
        map.put(9648L, "Mystery");
        map.put(10763L, "News");
        map.put(10764L, "Reality");
        map.put(10765L, "Sci-Fi & Fantasy");
        map.put(10766L, "Soap");
        map.put(10767L, "Talk");
        map.put(10768L, "War & Politics");
        map.put(28L, "Action");
        map.put(12L," Adventure");
        map.put(16L, "Animation");
        map.put(35L, "Comedy");
        map.put(80L, "Crime");
        map.put(99L, "Documentary");
        map.put(14L, "Fantasy");
        map.put(36L, "History");
        map.put(27L, "Horror");
        map.put(10402L, "Music");
        map.put(10749L, "Romance");
        map.put(878L, "Science Fiction");
        map.put(10770L, "TV Movie");
        map.put(53L, "Thriller");
        map.put(10752L,"War");
        map.put(37L, "Western");

    }
 public String getString(Long i){
        if(map.containsKey(i)){
            return map.get(i);
        }
        else
            return "";
}

}
