package com.example.yiuhet.lim.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yiuhet.lim.BaseFragment;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.adapter.ContactListAdapter;
import com.example.yiuhet.lim.presenter.imp1.ContactPresenterImp1;
import com.example.yiuhet.lim.ui.activity.ChatActivity;
import com.example.yiuhet.lim.view.ContactView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends BaseFragment<ContactView, ContactPresenterImp1> implements ContactView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycle_view)
    RecyclerView mRecycleView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ContactListAdapter mContactListAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void init() {
        super.init();
        initView();
        mPresenter.getContactListFromServer();
    }

    private void initView() {
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setHasFixedSize(true);
        mContactListAdapter = new ContactListAdapter(getContext(), mPresenter.getContactList());
        mContactListAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecycleView.setAdapter(mContactListAdapter);

        mRefreshLayout.setColorSchemeResources(R.color.blue,R.color.red);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    @Override
    protected ContactPresenterImp1 createPresenter() {
        return new ContactPresenterImp1(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_contact;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGetContactListSuccess() {
        mContactListAdapter.notifyDataSetChanged();
        if (mRefreshLayout != null)
            mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetContactListFailed() {
        toast("加载好友失败...");
        if (mRefreshLayout != null)
            mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onDeleteFailed() {
        mPresenter.refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private ContactListAdapter.OnItemClickListener mOnItemClickListener = new ContactListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String userName) {
            startActivity(ChatActivity.class, "user_name", userName);
        }

        @Override
        public void onItemLongClick(String userName) {
            toast("删除好友，todo..");
        }
    };
}
