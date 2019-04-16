package com.gadik.randomfacts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gadi
 */
public class RandomFactFragment extends Fragment {

    private static final String TAG = RandomFactFragment.class.getSimpleName();

    private static Retrofit retrofit = null;
    private static NumbersApi numbersApi;


    private TextView randomFactTV;
    private Button addToFavouritesBtn;
    private Button getNewFactBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.random_fact_fragment, container, false);
        randomFactTV = (TextView) view.findViewById(R.id.random_fact_tv);
        addToFavouritesBtn = (Button) view.findViewById(R.id.add_to_favorites_btn);
        getNewFactBtn = (Button) view.findViewById(R.id.get_new_fact_btn);

        getNewFactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "getNewFactBtn clicked.");
                getRandomFact();
            }
        });

        addToFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "addToFavouritesBtn clicked.");

                String fact = randomFactTV.getText().toString();

                if(!fact.equals("")){
                    Log.d(TAG, "Write random fact to firebase.");
                    //TODO: Write fact to firebase
                    Toast.makeText(container.getContext(), getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
                    disableAddToFavouritesBtn();
                }

            }
        });

        getRandomFact();

        return view;
    }

    private void getRandomFact() {
        Log.d(TAG, "getRandomFact() called.");

        initRetrofit();
        Call<Fact> call = numbersApi.getRandomFact();

        call.enqueue(new Callback<Fact>() {
            @Override
            public void onResponse(Call<Fact> call, Response<Fact> response) {
                Fact fact = response.body();
                if(fact != null){
                    randomFactTV.setText(fact.getText());
                    Log.i(TAG, "Fact fetched: " + fact.getText());
                    enableAddToFavouritesBtn();
                } else {
                    onFailure(call, null);
                }

            }

            @Override
            public void onFailure(Call<Fact> call, Throwable t) {
                randomFactTV.setText(getString(R.string.fetch_fact_failure_msg));
                Log.i(TAG, "Unable to fetch fact.");
                disableAddToFavouritesBtn();
            }
        });
    }

    private static void initRetrofit() {
        Log.d(TAG, "initRetrofit() called.");
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(NumbersApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        if (numbersApi == null) {
            numbersApi = retrofit.create(NumbersApi.class);
        }
    }

    private void enableAddToFavouritesBtn(){
        Log.d(TAG, "Enable AddToFavourites button.");
        addToFavouritesBtn.setEnabled(true);
        addToFavouritesBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    private void disableAddToFavouritesBtn(){
        Log.d(TAG, "Disable AddToFavourites button.");
        addToFavouritesBtn.setEnabled(false);
        addToFavouritesBtn.setBackgroundColor(getResources().getColor(R.color.lightGray));
    }
}
