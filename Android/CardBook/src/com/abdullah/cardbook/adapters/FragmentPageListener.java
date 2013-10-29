package com.abdullah.cardbook.adapters;

import com.abdullah.cardbook.fragments.BaseFragment;

public interface FragmentPageListener {
    void onSwitchToNextFragment(String fragmentGroup, BaseFragment nextFragment, BaseFragment oldFragment);
    void  onSwitchBeforeFragment(String fragmentGroup);
}
