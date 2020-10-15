/*
 * ModeSelectionView.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.android.lock.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.auth0.android.lock.R;
import com.auth0.android.lock.internal.configuration.AuthMode;
import com.google.android.material.tabs.TabLayout;

@SuppressLint("ViewConstructor")
public class ModeSelectionView extends LinearLayout implements TabLayout.OnTabSelectedListener {

    private final ModeSelectedListener callback;
    private TabLayout tabLayout;

    public ModeSelectionView(@NonNull Context context, @NonNull ModeSelectedListener listener) {
        super(context);
        this.callback = listener;
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.com_auth0_lock_tab_layout, this);
        tabLayout = findViewById(R.id.com_auth0_lock_tab_layout);

        final TabLayout.Tab firstTab = tabLayout.newTab()
                .setText(R.string.com_auth0_lock_mode_log_in);
        firstTab.setTag(AuthMode.LOG_IN);
        final TabLayout.Tab secondTab = tabLayout.newTab()
                .setText(R.string.com_auth0_lock_mode_sign_up);
        secondTab.setTag(AuthMode.SIGN_UP);

        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedMode((int) tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void setSelectedMode(@AuthMode int mode) {
        toggleBoldText(mode);
        callback.onModeSelected(mode);
    }

    private void toggleBoldText(@AuthMode int mode) {
        final int count = tabLayout.getTabCount();
        for (int i = 0; i < count; i++) {
            final TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                boolean selected = (int) tab.getTag() == mode;
                final TextView text = (TextView) tab.view.getChildAt(1);
                text.setTypeface(Typeface.defaultFromStyle(selected ? Typeface.BOLD : Typeface.NORMAL));
            }
        }
    }

    @Override
    @Deprecated
    public void onTabSelected(@NonNull TabLayout.Tab tab) {
        //No-Op
    }

    @Override
    @Deprecated
    public void onTabUnselected(@NonNull TabLayout.Tab tab) {
        //No-Op
    }

    @Override
    @Deprecated
    public void onTabReselected(@NonNull TabLayout.Tab tab) {
        //No-Op
    }

    public interface ModeSelectedListener {
        void onModeSelected(@AuthMode int mode);

        @AuthMode
        int getSelectedMode();
    }
}
