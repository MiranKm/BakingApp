package com.example.miranpc.bakingapp3.SharedUtilis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceipeDataService {

    private static Retrofit retrofit = null;
    public static Retrofit getDefaultInstance() {
        if (retrofit == null)
            return retrofit = new Retrofit.Builder()
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
