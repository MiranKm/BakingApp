package com.example.miranpc.bakingapp3.SharedUtilis;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.miranpc.bakingapp3.Ingredients;
import com.example.miranpc.bakingapp3.Recipes;
import com.example.miranpc.bakingapp3.AppWidget.RecipeRemoteViewFactory;
import com.example.miranpc.bakingapp3.WidgetViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class SharedPrefUtil {

    private static final String RECIPE_WIDGET_POS = "pos";
    private static final String DATA = "data";

    public static void ingredientToJson(Context context, List<Ingredients> ingredients) {
        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        Type type = new TypeToken<List<Ingredients>>() {}.getType();
        Gson gson = new Gson();

        String jsonIngredients = gson.toJson(ingredients, type);
        editor.putString(RecipeRemoteViewFactory.INGREDIENTS, jsonIngredients);
        editor.apply();
    }

    public static List<Ingredients> jsonToIngredient(Context context) {

        SharedPreferences preferences = getSharedPreferences(context);

        String jsonIngredients = preferences.getString(RecipeRemoteViewFactory.INGREDIENTS, "");

        Type type = new TypeToken<List<Ingredients>>() {}.getType();

        Gson gson = new Gson();

        List<Ingredients> ingredientsBeans = gson.fromJson(jsonIngredients, type);


        return ingredientsBeans;
    }

    public static void saveAllDataToPreferences(Context context, String dataJson) {

        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(DATA, dataJson);

        editor.apply();


    }


    public static List<Recipes> getRecipesFromPreferences(Context context) {

        SharedPreferences preferences = getSharedPreferences(context);
        String dataJson = preferences.getString(DATA, null);

        if (dataJson == null)
            return null;

        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes>>() {
        }.getType();

        List<Recipes> recipes = gson.fromJson(dataJson, type);


        return recipes;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void storeRecipePositionWidget(Context context, int position) {

        SharedPreferences preferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(RECIPE_WIDGET_POS, position);

        editor.apply();


    }

    public static WidgetViewModel getWidgetViewModel(Context context) {

        SharedPreferences preferences = getSharedPreferences(context);
        int selectedRecipeIndex = preferences.getInt(RECIPE_WIDGET_POS, 0);
        String recipeJsonData = preferences.getString(DATA, null);

        Type type = new TypeToken<List<Recipes>>() {
        }.getType();


        List<Recipes> recipes = new Gson().fromJson(recipeJsonData, type);

        WidgetViewModel viewModel = new WidgetViewModel();

        viewModel.setRecipeName(recipes.get(selectedRecipeIndex).getName());
        viewModel.setIngredients(recipes.get(selectedRecipeIndex).getIngredients());

        return viewModel;
    }
}
