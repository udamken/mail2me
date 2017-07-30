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
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Show about dialog to display e.g. the license.
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        WebView webViewAboutApacheLicenseText = (WebView) findViewById(R.id.aboutHtmlText);
        webViewAboutApacheLicenseText.loadUrl(getString(R.string.aboutHtmlTextUrl));
    }
}
