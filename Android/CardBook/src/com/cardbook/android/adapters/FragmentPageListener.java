package com.cardbook.android.adapters;

import com.cardbook.android.fragments.BaseFragment;

public interface FragmentPageListener {
    void onSwitchToNextFragment(String fragmentGroup, BaseFragment nextFragment, BaseFragment oldFragment);
    void  onSwitchBeforeFragment(String fragmentGroup);
}
