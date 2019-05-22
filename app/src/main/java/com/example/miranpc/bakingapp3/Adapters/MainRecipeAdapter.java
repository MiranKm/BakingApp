package com.example.miranpc.bakingapp3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miranpc.bakingapp3.R;

import java.util.List;

import com.example.miranpc.bakingapp3.Recipes;
import com.example.miranpc.bakingapp3.ActivitiesAndFragments.RecipeListActivity;

public class MainRecipeAdapter extends RecyclerView.Adapter<MainRecipeAdapter.RecipeViewHolder> {


    private List<Recipes> recipes;
    private Context context;

    public MainRecipeAdapter(List<Recipes> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recipe_name, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder recipeViewHolder, int i) {

        Recipes recipe = recipes.get(i);
        String recipeName = recipe.getName();

        recipeViewHolder.itemView.setOnClickListener (view -> {
            Intent intent = new Intent(context, RecipeListActivity.class);
            intent.putExtra(RecipeListActivity.DATA, recipes.get(recipeViewHolder.getAdapterPosition()));
            context.startActivity(intent);
        });

        recipeViewHolder.recipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }
    }

}
