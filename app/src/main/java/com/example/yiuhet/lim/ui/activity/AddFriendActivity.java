package com.example.yiuhet.lim.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.adapter.NewFriendListAdapter;
import com.example.yiuhet.lim.presenter.imp1.AddFriendPresenterImp1;
import com.example.yiuhet.lim.utils.AddFromViewListener;
import com.example.yiuhet.lim.view.AddFriendView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFriendActivity extends MVPBaseActivity<AddFriendView, AddFriendPresenterImp1> implements AddFriendView {


    @BindView(R.id.search_et)
    EditText mSearchEt;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private NewFriendListAdapter mNewFriendListAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected AddFriendPresenterImp1 createPresenter() {
        return new AddFriendPresenterImp1(this);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        initToolBar();
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setHasFixedSize(true);
        mNewFriendListAdapter = new NewFriendListAdapter(this, mPresenter.getQueryContact(), mAddFromViewListener);
        mRecycleView.setAdapter(mNewFriendListAdapter);
    }

    @Override
    public void onStartAddFriend() {

    }

    @Override
    public void onAddSuccess() {
        toast("添加好友成功");
    }

    @Override
    public void onAddFailed(String error) {
        toast(error.toString());
    }

    @Override
    public void onStartFind() {

    }

    @Override
    public void onFoundSuccess() {
        Log.d("ccccc", "找到了" + mPresenter.getQueryContact().size());
        mNewFriendListAdapter.notifyDataSetChanged();
        mRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNotFound() {
        mRecycleView.setVisibility(View.GONE);
        toast("未找到该用户");
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText("查找好友");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        mPresenter.queryUserFromBomb(mSearchEt.getText().toString().trim());
    }

    private AddFromViewListener mAddFromViewListener = new AddFromViewListener() {
        @Override
        public void add(String userName) {
            mPresenter.addFriend(userName, "test");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
