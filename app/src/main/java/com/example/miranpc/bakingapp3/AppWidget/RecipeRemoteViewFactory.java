package com.example.miranpc.bakingapp3.AppWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.miranpc.bakingapp3.R;

import java.util.List;

import com.example.miranpc.bakingapp3.Ingredients;
import com.example.miranpc.bakingapp3.SharedUtilis.SharedPrefUtil;

public class RecipeRemoteViewFactory extends RemoteViewsService {
    public static final String INGREDIENTS = "INGREDIENTS";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewProvider(this.getApplicationContext(), intent);
    }


    private class ListViewProvider implements RemoteViewsFactory {

        private Context applicationContext;
        private int appWidgetId;
        private Intent intent;
        private List<Ingredients> ingredients;

        ListViewProvider(Context applicationContext, Intent intent) {
            this.applicationContext = applicationContext;
            this.intent = intent;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 1);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredients = SharedPrefUtil.jsonToIngredient(applicationContext);

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews remoteViews = new RemoteViews(applicationContext.getPackageName(), R.layout.item_widget_ingredint);

            String ingredientName = ingredients.get(i).getIngredient();
            double quantity = ingredients.get(i).getQuantity();
            String measure = ingredients.get(i).getMeasure();

            remoteViews.setTextViewText(R.id.ingredient_name, applicationContext.getString(R.string.ingredient_quantity, quantity, measure, ingredientName));
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
