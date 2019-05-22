package com.example.miranpc.bakingapp3.ActivitiesAndFragments;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.miranpc.bakingapp3.R;


/**
 * An activity representing a single Receipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {


    private RecipeDetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipe_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle arguments = new Bundle();

        arguments.putParcelableArrayList(RecipeDetailFragment.STEPS,
                getIntent().getParcelableArrayListExtra(RecipeDetailFragment.STEPS));

        arguments.putInt(RecipeDetailFragment.POSITION,
                getIntent().getIntExtra(RecipeDetailFragment.POSITION, 0));


        if (savedInstanceState != null)
            fragment = (RecipeDetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "FRAG");
        else
            fragment = new RecipeDetailFragment();

        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "FRAG", fragment);
    }
}
