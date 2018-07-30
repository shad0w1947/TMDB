package com.example.shad0w.tmdb;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPersonFragment extends Fragment {
    Clicklistener clicklistener;
    ArrayList<TvResult> personResultArrayList;
    LargeAdapter largeAdapter;
    RecyclerView recyclerView;
    String query="shiv";
    int page=1;




    public SearchPersonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        query = this.getArguments().getString(Contact.Name);
        View output = inflater.inflate(R.layout.fragment_search_person, container, false);


        recyclerView = output.findViewById(R.id.serch_person_fragment);
        personResultArrayList =new ArrayList<>();
        clicklistener = new Clicklistener() {
            @Override
            public void itemClick(View view, int position) {
                TvResult result = personResultArrayList.get(position);
                Intent intent = new Intent(getContext(), PersponDetail.class);
                intent.putExtra(Contact.ID, result.getId());
                intent.putExtra(Contact.Name, result.getName());
                startActivity(intent);

            }
        };
        largeAdapter=new LargeAdapter(getContext(),null,personResultArrayList,Contact.Smallview,Contact.Person,clicklistener);
        recyclerView.setAdapter(largeAdapter);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
        recyclerView.setLayoutManager(layoutManager);

        connect(page);
        final boolean[] isScroll = new boolean[1];
        final int[] totalItem = new int[1];
        final int[] viewItem = new int[1];
        final int[] pastVisibleItems = new int[1];
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScroll[0] = true;
                Log.d("tag1", "LOAD NEXT ITEM2");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                viewItem[0] = layoutManager.getChildCount();
                int[] firstVisibleItems = null;
                firstVisibleItems = layoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                totalItem[0] = layoutManager.getItemCount();
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems[0] = firstVisibleItems[0];
                }

                if (isScroll[0]) {
                    if ((viewItem[0] + pastVisibleItems[0]) >= totalItem[0]) {
                        isScroll[0] = false;
                        page++;
                        //  progressBar.setVisibility(View.VISIBLE);
                        connect(page);
                        Log.d("tag1", "LOAD NEXT ITEM111");
                    }
                }
            }
        });
        return output;
    }

    private void connect(final int page) {

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/search/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
        Retroservice retroservice=retrofit.create(Retroservice.class);
        Call<TvPojo> call=retroservice.getSearchTv("person",API.getApi(),Contact.Language,query,page);
        call.enqueue(new Callback<TvPojo>() {
            @Override
            public void onResponse(Call<TvPojo> call, Response<TvPojo> response) {
                TvPojo pojo=response.body();
                personResultArrayList.addAll(pojo.getResults());
                largeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPojo> call, Throwable t) {

            }
        });

    }

}
