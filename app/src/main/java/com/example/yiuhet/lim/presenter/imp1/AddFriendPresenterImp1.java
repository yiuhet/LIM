package com.example.yiuhet.lim.presenter.imp1;

import android.util.Log;
import android.widget.Toast;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.model.Contact;
import com.example.yiuhet.lim.model.User;
import com.example.yiuhet.lim.presenter.AddFriendPresenter;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.AddFriendView;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by yiuhet on 2017/5/11.
 */

public class AddFriendPresenterImp1 extends BasePresenter<AddFriendView> implements AddFriendPresenter {

    private  AddFriendView mAddFriendView;
    private Contact mContact;
    private boolean isFound;
    private List<String> tmp ;

    public AddFriendPresenterImp1(AddFriendView mAddFriendView) {
        this.mAddFriendView = mAddFriendView;
        mContact = new Contact();
        tmp = new ArrayList<>();
    }

    @Override
    public void queryUserFromBomb(String userNmae) {
        mAddFriendView.onStartFind();
        tmp.clear();
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", userNmae);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    isFound = true;
                    mAddFriendView.onFoundSuccess();
                    for (int i = 0;i <list.size();i++) {
                        tmp.add(list.get(i).getUsername());
                    }
                } else {
                    isFound = false;
                    mAddFriendView.onNotFound();
                }
            }
        });
    }

    @Override
    public List<String> getQueryContact() {
        return tmp;
    }

    @Override
    public void addFriend(final String userName, final String info) {
        mAddFriendView.onStartAddFriend();
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(userName, info);
                    EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {

                        @Override
                        public void onContactAdded(String username) {
                            Log.d("ppapppa","success"+username);
                        }

                        @Override
                        public void onContactDeleted(String username) {
                            Log.d("ppapppa","success"+username);
                        }

                        @Override
                        public void onContactInvited(String username, String reason) {
                            Log.d("ppapppa","success"+username);
                        }

                        @Override
                        public void onFriendRequestAccepted(String username) {
                            Log.d("ppapppa","success"+username);
                        }

                        @Override
                        public void onFriendRequestDeclined(String username) {
                            Log.d("ppapppa","success"+username);
                        }
                    });
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAddFriendView.onAddSuccess();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAddFriendView.onAddFailed(e.toString());
                        }
                    });
                }
            }
        });

    }
}
