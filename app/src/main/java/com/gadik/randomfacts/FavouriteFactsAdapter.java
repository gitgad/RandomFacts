package com.gadik.randomfacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Gadi
 */
public class FavouriteFactsAdapter extends RecyclerView.Adapter<FavouriteFactsAdapter.FavouriteFactViewHolder> {

    private static final String TAG = FavouriteFactsAdapter.class.getSimpleName();

    private ArrayList<Fact> mFavFactsList;

    public FavouriteFactsAdapter(ArrayList<Fact> favFactsList){
        mFavFactsList = favFactsList;
    }

    @NonNull
    @Override
    public FavouriteFactsAdapter.FavouriteFactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context ctx = viewGroup.getContext();
        int layoutId = R.layout.favourite_facts_list_item;
        LayoutInflater inflater = LayoutInflater.from(ctx);

        View view = inflater.inflate(layoutId, viewGroup, false);
        FavouriteFactViewHolder viewHolder = new FavouriteFactViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteFactsAdapter.FavouriteFactViewHolder favouriteFactViewHolder, int i) {
        Log.d(TAG, "Binding view holder #" + i);
        favouriteFactViewHolder.bind(mFavFactsList.get(i));
    }

    @Override
    public int getItemCount() {
        if(mFavFactsList == null){
            return 0;
        } else {
            return mFavFactsList.size();
        }
    }

    /**
     * FavouriteFactViewHolder Class
     */
    class FavouriteFactViewHolder extends RecyclerView.ViewHolder {

        TextView factTV;

        public FavouriteFactViewHolder(View itemView) {
            super(itemView);
            factTV = (TextView) itemView.findViewById(R.id.favourite_fact_item_textview);
        }

        private void bind(Fact fact) {
            factTV.setText(fact.getText());
        }
    }
}