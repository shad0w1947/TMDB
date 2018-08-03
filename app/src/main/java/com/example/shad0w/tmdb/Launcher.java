package com.example.shad0w.tmdb;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.List;

public class Launcher extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movies");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setFragment(new MovieFragment());
    }

    @Override
    public void onBackPressed() {
        Log.i("back","bacl");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Log.i("back","bacl1");
        } else {
            Log.i("back","bacl2");
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launcher, menu);
//        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);



        final SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setQueryHint("Search Movie,Tv Show,Person...");
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search","we entered in search "+query);
                Intent intent=new Intent(Launcher.this,SearchActivity.class);
                intent.putExtra(Contact.Name,query);
                startActivity(intent);
                //finish();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            /*SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) item;
            if (manager != null) {
                search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
            }

            search.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("search","we entered in search");
                }
            });*/

            Log.i("search","we entered in search");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.movie_fragment) {
            toolbar.setTitle("Movies");
            fragment=new MovieFragment();
        } else if (id == R.id.tv_fragment) {
            toolbar.setTitle("Tv Shows");
            fragment=new TvShowFragment();
        } else if (id == R.id.favorite_fragment) {
            return false;

        } else if (id == R.id.nav_share) {
            return false;

        } else if (id == R.id.nav_send) {

            return false;
        }
        setFragment(fragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment) {
        Bundle bundle=new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.launcher_Container, fragment);
        fragmentTransaction.commit();
    }
}
