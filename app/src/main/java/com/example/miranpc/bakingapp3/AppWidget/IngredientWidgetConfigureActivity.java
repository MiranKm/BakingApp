package com.example.miranpc.bakingapp3.AppWidget;


import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.miranpc.bakingapp3.ActivitiesAndFragments.MainActivity;
import com.example.miranpc.bakingapp3.Recipes;
import com.example.miranpc.bakingapp3.R;
import com.example.miranpc.bakingapp3.SharedUtilis.SharedPrefUtil;

import java.util.List;

import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

public class IngredientWidgetConfigureActivity extends AppCompatActivity {


    private int appWidgetId;
    Button choose;
    AppCompatSpinner spinner;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.choose_ingredint_widget);
        spinner = findViewById(R.id.spinner);
        choose = findViewById(R.id.choose);

        appWidgetId = getIntent().getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            appWidgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        if (appWidgetId == INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        List<Recipes> recipe = SharedPrefUtil.getRecipesFromPreferences(this);
        if (recipe == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        populateSpinner(recipe);

    }

    private void populateSpinner(List<Recipes> recipes) {

        String[] recipeName = new String[recipes.size()];

        for (int i = 0; i < recipeName.length; i++) {
            recipeName[i] = recipes.get(i).getName();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeName);

        spinner.setAdapter(adapter);

        choose.setOnClickListener(v -> {
            int selectedSpinnerIndex = spinner.getSelectedItemPosition();
            SharedPrefUtil.storeRecipePositionWidget(this, selectedSpinnerIndex);
            AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
            RecipeAppWidget.updateAppWidget(this, manager, appWidgetId);

            Intent intent = new Intent();
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, intent);
            finish();


        });

    }

}
