package com.gadik.randomfacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Gadi
 */
public class FavouriteFactsFragment extends Fragment {

    private static final String TAG = FavouriteFactsFragment.class.getSimpleName();


    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called.");

        View view = inflater.inflate(R.layout.favourite_facts_fragment, container, false);

        // Inflate view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favourite_fact_recyclerview);

        // Set layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        ArrayList<Fact> dummyFacts = new ArrayList<>();
        dummyFacts.add(new Fact("Fact number 1"));
        dummyFacts.add(new Fact("Fact number 2"));
        dummyFacts.add(new Fact("Fact number 3"));
        dummyFacts.add(new Fact("Fact number 4"));
        dummyFacts.add(new Fact("Fact number 5"));
        dummyFacts.add(new Fact("Fact number 6"));
        dummyFacts.add(new Fact("Fact number 7"));
        dummyFacts.add(new Fact("Fact number 8"));
        dummyFacts.add(new Fact("Fact number 9"));
        dummyFacts.add(new Fact("Fact number 10"));
        dummyFacts.add(new Fact("Fact number 11"));
        dummyFacts.add(new Fact("Fact number 12"));
        dummyFacts.add(new Fact("Fact number 13"));
        dummyFacts.add(new Fact("Fact number 14"));
        dummyFacts.add(new Fact("Fact number 15"));
        dummyFacts.add(new Fact("Fact number 16"));
        dummyFacts.add(new Fact("Fact number 17"));

        FavouriteFactsAdapter adapter = new FavouriteFactsAdapter(dummyFacts);
        mRecyclerView.setAdapter(adapter);

        return view;
    }
}
