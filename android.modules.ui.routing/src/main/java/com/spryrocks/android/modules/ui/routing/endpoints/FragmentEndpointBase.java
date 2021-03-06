package com.spryrocks.android.modules.ui.routing.endpoints;

import com.spryrocks.android.modules.ui.routing.context.IFrameTarget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

class FragmentEndpointBase<TFragment extends Fragment> extends EndpointBase<IFrameTarget, TFragment, TFragment, FragmentEndpointBase.Wrapper<TFragment>, FrameEndpointSettings> {
    FragmentEndpointBase(IFrameTarget target, Class<TFragment> tFragmentClass) {
        super(target, tFragmentClass);
    }

    @NonNull
    @Override
    protected Wrapper<TFragment> createWrapper(IFrameTarget target, Class<TFragment> tFragmentClass) {
        return new Wrapper<>(target, tFragmentClass);
    }

    @NonNull
    @Override
    protected FrameEndpointSettings createSettings() {
        return new FrameEndpointSettings();
    }

    static class Wrapper<TFragment extends Fragment> extends EndpointBase.Wrapper<IFrameTarget, TFragment, FrameEndpointSettings> {
        boolean clearBackStack;
        boolean hideKeyboard;

        Wrapper(IFrameTarget target, Class<TFragment> tFragmentClass) {
            super(target, createFragment(tFragmentClass));
        }

        @Override
        void setup(@Nullable FrameEndpointSettings settings) {
            if (settings == null)
                return;

            clearBackStack = settings.clearBackStack;
            hideKeyboard = settings.hideKeyboard;
        }

        @Override
        void start() {
            target.replaceFragment(wrapped, clearBackStack, hideKeyboard);
        }

        private static <T extends Fragment> T createFragment(Class<T> tFragmentClass) {
            try {
                return tFragmentClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}