package com.example.miranpc.bakingapp3.SharedUtilis;


import java.util.List;

import com.example.miranpc.bakingapp3.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeData {

    @GET("2017/May/59121517_baking/baking.json")
    Call<List<Recipes>> getData();
}
