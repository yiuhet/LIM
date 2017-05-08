package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.ContactPresenter;
import com.example.yiuhet.lim.view.ContactView;
import com.example.yiuhet.lim.view.ConversationView;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class ContactPresenterImp1 extends BasePresenter<ContactView> implements ContactPresenter{
    private ContactView mContactView;

    public ContactPresenterImp1(ContactView mContactView) {
        this.mContactView = mContactView;
    }
}
