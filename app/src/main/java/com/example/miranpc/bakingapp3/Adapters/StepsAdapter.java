package com.example.miranpc.bakingapp3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.miranpc.bakingapp3.ActivitiesAndFragments.RecipeDetailActivity;
import com.example.miranpc.bakingapp3.ActivitiesAndFragments.RecipeDetailFragment;
import com.example.miranpc.bakingapp3.ActivitiesAndFragments.RecipeListActivity;
import com.example.miranpc.bakingapp3.Steps;
import com.example.miranpc.bakingapp3.R;

import java.util.ArrayList;

public class StepsAdapter
        extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {


    private final RecipeListActivity parentActivity;
    private final boolean twoPane;
    private ArrayList<Steps> steps;
    Context context;


    public StepsAdapter(RecipeListActivity parentActivity, ArrayList<Steps> steps, boolean twoPane) {
        this.parentActivity = parentActivity;
        this.steps = steps;
        this.twoPane = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.recipeName.setText(steps.get(position).getShortDescription());
        holder.itemView.setTag(steps.get(position));
        holder.stepId.setText(String.valueOf(steps.get(position).getId() + 1));
        holder.itemView.setOnClickListener(view -> {
            if (twoPane) {
                ShowFragment(holder.getAdapterPosition());
            } else {
                context= view.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putParcelableArrayListExtra(RecipeDetailFragment.STEPS, steps);
                intent.putExtra(RecipeDetailFragment.POSITION, holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });

    }

    public void ShowFragment(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(RecipeDetailFragment.STEPS, steps);
        arguments.putInt(RecipeDetailFragment.POSITION, position);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(arguments);

        parentActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .commit();
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView stepId;

        ViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.content);
            stepId = view.findViewById(R.id.step_id);
        }
    }
}