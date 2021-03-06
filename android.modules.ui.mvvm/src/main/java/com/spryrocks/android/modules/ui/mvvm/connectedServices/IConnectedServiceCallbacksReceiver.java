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

package com.spryrocks.android.modules.ui.mvvm.connectedServices;

import com.spryrocks.android.modules.utils.Actions;

import java.util.Set;

import androidx.annotation.NonNull;

public interface IConnectedServiceCallbacksReceiver {
    @NonNull <TCallbacks extends IConnectedServiceCallbacks>
    Set<TCallbacks> getCallbacks(@NonNull Class<TCallbacks> callbacksClass);

    @SuppressWarnings("unused")
    default <TCallbacks extends IConnectedServiceCallbacks>
    void useCallbacks(@NonNull Class<TCallbacks> tCallbacksClass, @NonNull Actions.Action1<TCallbacks> action) {
        @NonNull Set<TCallbacks> callbacks = getCallbacks(tCallbacksClass);
        for (TCallbacks callback : callbacks) {
            action.run(callback);
        }
    }
}