package com.example.miranpc.bakingapp3.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miranpc.bakingapp3.R;

import java.util.ArrayList;

import com.example.miranpc.bakingapp3.Ingredients;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {


    private ArrayList<Ingredients> ingredients;
    private Context context;

    public IngredientsAdapter(ArrayList<Ingredients> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ingredint, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientViewHolder recipeViewHolder, int i) {

        recipeViewHolder.name.setText(ingredients.get(i).getIngredient());
        recipeViewHolder.quantity.setText(String.valueOf(ingredients.get(i).getQuantity()));
        recipeViewHolder.measure.setText(ingredients.get(i).getMeasure());

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView quantity;
        private TextView measure;


        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            quantity  = itemView.findViewById(R.id.quantity);
            measure  = itemView.findViewById(R.id.measure);
        }
    }

}
