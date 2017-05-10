package com.example.yiuhet.lim.presenter.imp1;

import android.util.Log;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.presenter.ContactPresenter;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.ContactView;
import com.example.yiuhet.lim.view.ConversationView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class ContactPresenterImp1 extends BasePresenter<ContactView> implements ContactPresenter{
    private ContactView mContactView;
    private List<Contact> mContactList;

    public ContactPresenterImp1(ContactView mContactView) {
        this.mContactView = mContactView;
        mContactList = new ArrayList<>();
    }

    @Override
    public void getContactListFromServer() {
        if (mContactList.size() > 0) {
            mContactView.onGetContactListSuccess();
            return;
        }
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    startGetData();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onGetContactListSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onGetContactListFailed();
                        }
                    });

                }

            }
        });
    }

    private void startGetData() throws HyphenateException {
        List<String> contacts = EMClient.getInstance().contactManager().getAllContactsFromServer();
        Collections.sort(contacts, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);
            }
        });
        if (!contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
                Contact contact = new Contact();
                contact.userName = contacts.get(i);
                mContactList.add(contact);

            }
        }
    }

    @Override
    public List<Contact> getContactList() {
        return mContactList;
    }

    @Override
    public void refresh() {
        mContactList.clear();
        getContactListFromServer();
    }

    @Override
    public void delete(final String userName) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().deleteContact(userName);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onDeleteFailed();
                        }
                    });
                }
            }
        });
    }
}
