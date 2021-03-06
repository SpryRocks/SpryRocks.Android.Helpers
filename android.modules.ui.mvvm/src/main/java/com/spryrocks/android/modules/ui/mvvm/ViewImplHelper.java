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

package com.spryrocks.android.modules.ui.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spryrocks.android.modules.ui.lifecycle.LifecycleListener;
import com.spryrocks.android.modules.ui.mvvm.connectedServices.ConnectedServicesRegistration;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

class ViewImplHelper<TBinding extends ViewDataBinding, TViewModel extends ViewModel>
        extends LifecycleListener
        implements IMvvmView<TBinding, TViewModel> {
    private final IMvvmView<TBinding, TViewModel> ownerView;
    private @LayoutRes final int layoutId;
    private final Class<TViewModel> viewModelClass;
    private final int modelBindingVariableId;
    private TBinding binding;
    TViewModel viewModel;

    ViewImplHelper(@LayoutRes int layoutId, Class<TViewModel> viewModelClass, int modelBindingVariableId, IMvvmView<TBinding, TViewModel> ownerView) {
        this.layoutId = layoutId;
        this.viewModelClass = viewModelClass;
        this.modelBindingVariableId = modelBindingVariableId;
        this.ownerView = ownerView;
    }

    @Override
    public void initViewModel(TViewModel viewModel) {
        ownerView.initViewModel(viewModel);
    }

    @Override
    public void initConnectedServices(ConnectedServicesRegistration services) {
        ownerView.initConnectedServices(services);
    }

    @Override
    public void initBinding(TBinding binding) {
        ownerView.initBinding(binding);
    }

    @Override
    public TBinding getBinding() {
        return binding;
    }

    @Override
    public TViewModel getViewModel() {
        return viewModel;
    }

    void onCreate(ViewModelProvider viewModelProvider, ConnectedServicesRegistration connectedServicesRegistration) {
        this.viewModel = viewModelProvider.get(viewModelClass);

        connectedServicesRegistration.setConnectedServicesOwner(viewModel);

        initConnectedServices(connectedServicesRegistration);

        viewModel.onViewAttached();
    }

    @Override
    public void onStart() {
        viewModel.onActivated();
    }

    @Override
    public void onStop() {
        viewModel.onDeactivated();
    }

    @Override
    public void onDestroy() {
        viewModel.onViewDetached();
    }

    void inflateAndInitBinding(TBinding binding) {
        this.binding = binding;

        binding.setVariable(modelBindingVariableId, viewModel.getModel());

        initBinding(binding);
    }

    int getLayoutId() {
        return layoutId;
    }

    void initializeViewModelIfNeed(@NonNull TViewModel viewModel) {
        if (viewModel.isInitialized)
            return;

        viewModel.isInitialized = true;

        initViewModel(viewModel);

        viewModel.onInitialized();
    }

    static class FragmentActivity<TBinding extends ViewDataBinding, TViewModel extends ViewModel>
            extends ViewImplHelper<TBinding, TViewModel> {
        FragmentActivity(int layoutId, Class<TViewModel> tViewModelClass, int modelBindingVariableId, IMvvmView<TBinding, TViewModel> ownerView) {
            super(layoutId, tViewModelClass, modelBindingVariableId, ownerView);
        }

        void onCreate(androidx.fragment.app.FragmentActivity fragmentActivity, ConnectedServicesRegistration connectedServicesRegistration) {
            ViewModelProvider viewModelProvider = ViewModelProviders.of(fragmentActivity);

            super.onCreate(viewModelProvider, connectedServicesRegistration);

            TBinding binding = DataBindingUtil.setContentView(fragmentActivity, getLayoutId());
            inflateAndInitBinding(binding);

            initializeViewModelIfNeed(viewModel);
        }
    }

    static class Fragment<TBinding extends ViewDataBinding, TViewModel extends ViewModel>
            extends ViewImplHelper<TBinding, TViewModel> {
        Fragment(int layoutId, Class<TViewModel> tViewModelClass, int modelBindingVariableId, IMvvmView<TBinding, TViewModel> ownerView) {
            super(layoutId, tViewModelClass, modelBindingVariableId, ownerView);
        }

        void onCreate(androidx.fragment.app.Fragment fragment, ConnectedServicesRegistration connectedServicesRegistration) {
            ViewModelProvider viewModelProvider = ViewModelProviders.of(fragment);

            super.onCreate(viewModelProvider, connectedServicesRegistration);

            initializeViewModelIfNeed(viewModel);
        }

        View onCreateView(LayoutInflater inflater, ViewGroup container) {
            TBinding binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            inflateAndInitBinding(binding);
            return binding.getRoot();
        }
    }
}
