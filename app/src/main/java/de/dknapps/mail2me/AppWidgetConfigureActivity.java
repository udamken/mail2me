/*
 * mail2me - Create an email to yourself with one click
 *
 *     Copyright (C) 2017 Uwe Damken
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.dknapps.mail2me;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

/**
 * The configuration screen for the {@link AppWidget AppWidget} AppWidget.
 */
public class AppWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "de.dknapps.mail2me.AppWidget";

    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    EditText editTextAbbreviation;

    EditText editTextEmailAddress;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = AppWidgetConfigureActivity.this;

            // When the button is clicked, store the strings locally
            String abbreviation = editTextAbbreviation.getText().toString();
            String emailAddress = editTextEmailAddress.getText().toString();
            saveAppWidgetPreferences(context, appWidgetId, abbreviation, emailAddress);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            AppWidget.updateAppWidget(context, appWidgetManager, appWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public AppWidgetConfigureActivity() {
        super();
    }

    /**
     * Write new preference values into the shared preferences for the app widget with the given id.
     */
    static void saveAppWidgetPreferences(Context context, int appWidgetId, String abbreviation, String emailAddress) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(getKey(R.string.abbreviationKey, context, appWidgetId), abbreviation);
        prefs.putString(getKey(R.string.emailAddressKey, context, appWidgetId), emailAddress);
        prefs.apply();
    }

    @NonNull
    private static String getKey(int resId, Context context, int appWidgetId) {
        return context.getText(resId) + String.valueOf(appWidgetId);
    }

    /**
     * Delete all preference values from the shared preferences for the app widget with the given id.
     */
    static void deleteAppWidgetPreferences(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(getKey(R.string.abbreviationKey, context, appWidgetId));
        prefs.remove(getKey(R.string.emailAddressKey, context, appWidgetId));
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.app_widget_configure);
        editTextAbbreviation = (EditText) findViewById(R.id.abbreviation);
        editTextEmailAddress = (EditText) findViewById(R.id.emailAddress);
        findViewById(R.id.addButton).setOnClickListener(onClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        editTextAbbreviation.setText(loadAppWidgetPreference(R.string.abbreviationKey, this, appWidgetId));
        editTextEmailAddress.setText(loadAppWidgetPreference(R.string.emailAddressKey, this, appWidgetId));
    }

    /**
     * Write preference value corresponding to resId from the shared preferences for the app widget with the given id.
     */
    static String loadAppWidgetPreference(int resId, Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String value = prefs.getString(getKey(resId, context, appWidgetId), null);
        return (value != null) ? value : "";
    }

}

