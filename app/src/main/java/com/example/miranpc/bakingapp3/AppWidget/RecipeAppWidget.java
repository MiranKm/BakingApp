package com.example.miranpc.bakingapp3.AppWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.miranpc.bakingapp3.R;

import com.example.miranpc.bakingapp3.ActivitiesAndFragments.MainActivity;
import com.example.miranpc.bakingapp3.SharedUtilis.SharedPrefUtil;
import com.example.miranpc.bakingapp3.WidgetViewModel;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppWidget extends AppWidgetProvider {

    static void updateAppWidget(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);


        WidgetViewModel widgetViewModel = SharedPrefUtil.getWidgetViewModel(context);
        views.setTextViewText(R.id.recipe_name_widget, widgetViewModel.getRecipeName());

        SharedPrefUtil.ingredientToJson(context, widgetViewModel.getIngredients());


        Intent intent = new Intent(context, RecipeRemoteViewFactory.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.ingredients_list_widget, intent);


        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0,
                new Intent(context, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.ingredients_list_widget, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "Widget Added :D", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "Bye :)", Toast.LENGTH_SHORT).show();
    }
}

