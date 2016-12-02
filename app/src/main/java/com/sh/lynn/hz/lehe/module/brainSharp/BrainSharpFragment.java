package com.sh.lynn.hz.lehe.module.brainSharp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.lynn.hz.lehe.LeHeApp;
import com.sh.lynn.hz.lehe.R;
import com.sh.lynn.hz.lehe.net.APIManager;

import java.util.List;

import javax.inject.Inject;


public class BrainSharpFragment extends Fragment implements BrainSharpView{


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
//    private OnImageClickListener mListener;
    RecyclerView recyclerView=null;
    @Inject
     BrainSharpPresenter mPresenter;
    @Inject
    APIManager mAPIManager;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BrainSharpFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BrainSharpFragment newInstance(int columnCount) {
        BrainSharpFragment fragment = new BrainSharpFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brainsharp_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
             recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }

        LeHeApp.get(getActivity()).getAppComponent().plus(new BrainSharpModule(this)).inject(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showList(List<BrainSharp.NewslistBean> list) {
        if(     recyclerView!=null        )
        recyclerView.setAdapter(new MyBrainSharpRecyclerViewAdapter(list));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closedLoading() {

    }

    @Override
    public void showResult() {

    }


}
