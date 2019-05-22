package com.example.miranpc.bakingapp3;


import java.util.List;

import com.example.miranpc.bakingapp3.Ingredients;

public class WidgetViewModel {

    private String recipeName;
    private List<Ingredients> ingredients;

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipeName() {

        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
