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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Open the about dialog.
     */
    public void onClickButtonOpenAbout(final View buttonOpenAbout) {
        startActivity(new Intent(this, AboutActivity.class));
    }

    /**
     * Add a link to the homescreen.
     */
    public void onClickButtonAddLink(final View buttonAddLink) {
        View rootView = buttonAddLink.getRootView();
        String linkAbbreviation = ((TextView) rootView.findViewById(R.id.linkAbbreviation)).getText().toString();
        String linkEmailAddress = ((TextView) rootView.findViewById(R.id.linkEmailAddress)).getText().toString();
        Intent shortcutIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", linkEmailAddress, null));

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, linkAbbreviation);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        addIntent.putExtra("duplicate", false);  //may it's already there so don't duplicate
        getApplicationContext().sendBroadcast(addIntent);
        finish();
    }

}
