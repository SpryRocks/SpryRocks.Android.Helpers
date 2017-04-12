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

package com.spryrocks.android.modules.data.loaders;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class EmptyDataSource<T> implements IDataSource<T> {
    @Override
    public ILoaderAlgorithm<T> createAlgorithm(Context context) {
        return new Algorithm<>();
    }

    private static class Algorithm<T> implements ILoaderAlgorithm<T> {
        @Override
        public List<T> loadItems(int offset, int count) throws Exception {
            return new ArrayList<>();
        }

        @Override
        public boolean isPaginationSupported() {
            return false;
        }
    }
}
