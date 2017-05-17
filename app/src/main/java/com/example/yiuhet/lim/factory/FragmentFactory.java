package com.example.yiuhet.lim.factory;

import com.example.yiuhet.lim.BaseFragment;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.ui.fragment.ContactFragment;
import com.example.yiuhet.lim.ui.fragment.ConversationFragment;
import com.example.yiuhet.lim.ui.fragment.SettingFragment;

/**
 * Created by yiuhet on 2017/5/7.
 */

public class FragmentFactory {
    private static FragmentFactory sFragmentFactory;

    private BaseFragment mContactFragment;
    private BaseFragment mConversationFragment;
    private BaseFragment mSettingFragment;

    public static FragmentFactory getInstance() {
        if (sFragmentFactory == null) {
            synchronized (FragmentFactory.class) {
                if (sFragmentFactory == null) {
                    sFragmentFactory = new FragmentFactory();
                }
            }
        }
        return sFragmentFactory;
    }
    public BaseFragment getFragment(int id) {
        switch (id) {
            case R.id.tab_contact:
                return getContactFragment();
            case R.id.tab_conversation:
                return getConversationFragment();
            case R.id.tab_setting:
                return getSettingFragment();
        }
        return null;
    }

    private BaseFragment getSettingFragment() {
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
        }
        return mSettingFragment;
    }

    private BaseFragment getConversationFragment() {
        if (mConversationFragment == null) {
            mConversationFragment = new ConversationFragment();
        }
        return mConversationFragment;
    }

    private BaseFragment getContactFragment() {
        if (mContactFragment == null) {
            mContactFragment = new ContactFragment();
        }
        return mContactFragment;
    }

}
