package com.example.miranpc.bakingapp3.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.MenuItem;

import com.example.miranpc.bakingapp3.Adapters.IngredientsAdapter;
import com.example.miranpc.bakingapp3.Adapters.StepsAdapter;
import com.example.miranpc.bakingapp3.Ingredients;
import com.example.miranpc.bakingapp3.Recipes;
import com.example.miranpc.bakingapp3.Steps;
import com.example.miranpc.bakingapp3.R;

import java.util.ArrayList;


public class RecipeListActivity extends AppCompatActivity {

    public static final String DATA = "data";
    private boolean twoPane;
    private RecyclerView ingredientsRecycler, stepsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_list);

        ingredientsRecycler = findViewById(R.id.ingredients_recycler);
        stepsRecycler = findViewById(R.id.steps_recycler);

        Recipes recipe = getIntent().getParcelableExtra(DATA);

        if (findViewById(R.id.recipe_detail_container) != null) {
            twoPane = true;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(recipe.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ArrayList<Ingredients> ingredients = (ArrayList<Ingredients>) recipe.getIngredients();

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients, this);
        setUpIngredientsRecycler(ingredientsAdapter);
        SnapHelper snapHelper= new LinearSnapHelper();
        ArrayList<Steps> steps = (ArrayList<Steps>) recipe.getSteps();
        StepsAdapter adapter = new StepsAdapter(this, steps, twoPane);
        setUpStepsRecycler(adapter);
        snapHelper.attachToRecyclerView(ingredientsRecycler);

        if (twoPane)
            adapter.ShowFragment(0);
    }


    private void setUpStepsRecycler(StepsAdapter adapter) {
        stepsRecycler.setHasFixedSize(true);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setAdapter(adapter);
    }

    private void setUpIngredientsRecycler(IngredientsAdapter adapter) {
        ingredientsRecycler.setHasFixedSize(false);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ingredientsRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
