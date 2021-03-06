/*
 * Copyright 2017 Spry Rocks, Inc
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.spryrocks.android.modules.ui;

import android.content.Intent;
import android.os.Bundle;

import com.spryrocks.android.modules.ui.lifecycle.ActivityLifecycleListenersCollection;
import com.spryrocks.android.modules.ui.lifecycle.ILifecycleListener;
import com.spryrocks.android.modules.ui.lifecycle.ILifecycleListenersCollection;

import androidx.appcompat.app.AppCompatActivity;

public class Activity extends AppCompatActivity implements ILifecycleListenersCollection {
    private final ActivityLifecycleListenersCollection lifecycleListenersCollection;

    public Activity() {
        lifecycleListenersCollection = new ActivityLifecycleListenersCollection();
    }


    /* lifecycle */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleListenersCollection.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        lifecycleListenersCollection.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        lifecycleListenersCollection.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        lifecycleListenersCollection.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();

        lifecycleListenersCollection.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        lifecycleListenersCollection.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        lifecycleListenersCollection.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(lifecycleListenersCollection.onActivityResult(requestCode, resultCode, data))
            return;

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        lifecycleListenersCollection.onRestoreInstanceState(savedInstanceState);
    }


    /* public methods */

    @Override
    public <T extends ILifecycleListener> T registerLifecycleListener(T lifecycleListener) {
        return lifecycleListenersCollection.registerLifecycleListener(lifecycleListener);
    }
}
