package com.example.miranpc.bakingapp3.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.miranpc.bakingapp3.Recipes;
import com.example.miranpc.bakingapp3.SharedUtilis.ReceipeDataService;
import com.example.miranpc.bakingapp3.SharedUtilis.RecipeData;
import com.example.miranpc.bakingapp3.R;
import com.example.miranpc.bakingapp3.Adapters.MainRecipeAdapter;
import com.example.miranpc.bakingapp3.SharedUtilis.SharedPrefUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String ID = "recipe";
    private static final String TAG = "tag";
    private CountingIdlingResource idlingResource = new CountingIdlingResource(ID);

    RecyclerView recipeRecycler;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeRecycler = findViewById(R.id.recipe_recycler);
        progress = findViewById(R.id.progress);

        RecipeData data = ReceipeDataService.getDefaultInstance().create(RecipeData.class);
        idlingResource.increment();
        data.getData().enqueue(new RecipeListCallback());

    }


    @VisibleForTesting
    public CountingIdlingResource getIdlingResources() {
        return idlingResource;
    }

    private class RecipeListCallback implements Callback<List<Recipes>> {
        @Override
        public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {

            List<Recipes> recipe = response.body();
            Log.d(TAG, "onResponse: size" + recipe.size());

            if (recipe != null) {
                Log.d(TAG, "onResponse: size" + recipe.size());
                idlingResource.decrement();
                SharedPrefUtil.saveAllDataToPreferences(MainActivity.this, new Gson().toJson(response.body()));
                MainRecipeAdapter adapter = new MainRecipeAdapter(recipe, MainActivity.this);
                setUpRecycler(adapter);
            }

            progress.setVisibility(View.GONE);
        }

        @Override
        public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {
            Log.e("something went wrong!!", t.getMessage());
            progress.setVisibility(View.GONE);
        }
    }

    private void setUpRecycler(MainRecipeAdapter adapter) {
        recipeRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recipeRecycler.setHasFixedSize(true);
        recipeRecycler.setAdapter(adapter);

    }
}
