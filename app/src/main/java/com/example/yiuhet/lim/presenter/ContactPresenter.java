package com.example.yiuhet.lim.presenter;

import com.example.yiuhet.lim.model.Contact;

import java.util.List;

/**
 * Created by yiuhet on 2017/5/8.
 */

public interface ContactPresenter {

    void getContactListFromServer();

    List<Contact> getContactList();

    void refresh();

    void delete(String userName);
}
